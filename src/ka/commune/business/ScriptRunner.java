/*
 * Decompiled with CFR 0.139.
 */
package ka.commune.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptRunner {
    private static final String DEFAULT_DELIMITER = ";";
    private static final Pattern SOURCE_COMMAND = Pattern.compile("^\\s*SOURCE\\s+(.*?)\\s*$", 2);
    public static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$", 2);
    private final Connection connection;
    private final boolean stopOnError;
    private final boolean autoCommit;
    private PrintWriter logWriter = null;
    private PrintWriter errorLogWriter = null;
    private String delimiter = ";";
    private boolean fullLineDelimiter = false;
    private String userDirectory = System.getProperty("user.dir");

    public ScriptRunner(Connection connection, boolean autoCommit, boolean stopOnError) {
        this.connection = connection;
        this.autoCommit = autoCommit;
        this.stopOnError = stopOnError;
        File logFile = new File("create_db.log");
        File errorLogFile = new File("create_db_error.log");
        try {
            this.logWriter = logFile.exists() ? new PrintWriter(new FileWriter(logFile, true)) : new PrintWriter(new FileWriter(logFile, false));
        }
        catch (IOException e) {
            System.err.println("Unable to access or create the db_create log");
        }
        try {
            this.errorLogWriter = errorLogFile.exists() ? new PrintWriter(new FileWriter(errorLogFile, true)) : new PrintWriter(new FileWriter(errorLogFile, false));
        }
        catch (IOException e) {
            System.err.println("Unable to access or create the db_create error log");
        }
        String timeStamp = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss").format(new Date());
        this.println("\n-------\n" + timeStamp + "\n-------\n");
        this.printlnError("\n-------\n" + timeStamp + "\n-------\n");
    }

    public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
        this.delimiter = delimiter;
        this.fullLineDelimiter = fullLineDelimiter;
    }

    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public void setErrorLogWriter(PrintWriter errorLogWriter) {
        this.errorLogWriter = errorLogWriter;
    }

    public void setUserDirectory(String userDirectory) {
        this.userDirectory = userDirectory;
    }

    public void runScript(String filepath) throws IOException, SQLException {
        File file = new File(this.userDirectory, filepath);
        this.runScript(new BufferedReader(new FileReader(file)));
    }

    public void runScript(Reader reader) throws IOException, SQLException {
        try {
            boolean originalAutoCommit = this.connection.getAutoCommit();
            try {
                if (originalAutoCommit != this.autoCommit) {
                    this.connection.setAutoCommit(this.autoCommit);
                }
                this.runScript(this.connection, reader);
            }
            finally {
                this.connection.setAutoCommit(originalAutoCommit);
            }
        }
        catch (IOException | SQLException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Error running script.  Cause: " + e, e);
        }
    }

    private void runScript(Connection conn, Reader reader) throws IOException, SQLException {
        StringBuffer command = null;
        try {
            try {
                String line;
                LineNumberReader lineReader = new LineNumberReader(reader);
                while ((line = lineReader.readLine()) != null) {
                    if (command == null) {
                        command = new StringBuffer();
                    }
                    String trimmedLine = line.trim();
                    Matcher delimMatch = delimP.matcher(trimmedLine);
                    if (trimmedLine.length() < 1 || trimmedLine.startsWith("//")) continue;
                    if (delimMatch.matches()) {
                        this.setDelimiter(delimMatch.group(2), false);
                        continue;
                    }
                    if (trimmedLine.startsWith("--")) {
                        this.println(trimmedLine);
                        continue;
                    }
                    if (trimmedLine.length() < 1 || trimmedLine.startsWith("--")) continue;
                    if (!this.fullLineDelimiter && trimmedLine.endsWith(this.getDelimiter()) || this.fullLineDelimiter && trimmedLine.equals(this.getDelimiter())) {
                        command.append(line.substring(0, line.lastIndexOf(this.getDelimiter())));
                        command.append(" ");
                        this.execCommand(conn, command, lineReader);
                        command = null;
                        continue;
                    }
                    command.append(line);
                    command.append("\n");
                }
                if (command != null) {
                    this.execCommand(conn, command, lineReader);
                }
                if (!this.autoCommit) {
                    conn.commit();
                }
            }
            catch (IOException e) {
                throw new IOException(String.format("Error executing '%s': %s", command, e.getMessage()), e);
            }
        }
        finally {
            conn.rollback();
            this.flush();
        }
    }

    private void execCommand(Connection conn, StringBuffer command, LineNumberReader lineReader) throws IOException, SQLException {
        if (command.length() == 0) {
            return;
        }
        Matcher sourceCommandMatcher = SOURCE_COMMAND.matcher(command);
        if (sourceCommandMatcher.matches()) {
            this.runScriptFile(conn, sourceCommandMatcher.group(1));
            return;
        }
        this.execSqlCommand(conn, command, lineReader);
    }

    private void runScriptFile(Connection conn, String filepath) throws IOException, SQLException {
        File file = new File(this.userDirectory, filepath);
        this.runScript(conn, new BufferedReader(new FileReader(file)));
    }

    private void execSqlCommand(Connection conn, StringBuffer command, LineNumberReader lineReader) throws SQLException {
        boolean hasResults;
        Statement statement;
        block9 : {
            statement = conn.createStatement();
            this.println(command);
            hasResults = false;
            try {
                hasResults = statement.execute(command.toString());
            }
            catch (SQLException e) {
                String errText = String.format("Error executing '%s' (line %d): %s", command, lineReader.getLineNumber(), e.getMessage());
                this.printlnError(errText);
                System.err.println(errText);
                if (!this.stopOnError) break block9;
                throw new SQLException(errText, e);
            }
        }
        if (this.autoCommit && !conn.getAutoCommit()) {
            conn.commit();
        }
        ResultSet rs = statement.getResultSet();
        if (hasResults && rs != null) {
            int i;
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            for (i = 1; i <= cols; ++i) {
                String name = md.getColumnLabel(i);
                this.print(String.valueOf(name) + "\t");
            }
            this.println("");
            while (rs.next()) {
                for (i = 1; i <= cols; ++i) {
                    String value = rs.getString(i);
                    this.print(String.valueOf(value) + "\t");
                }
                this.println("");
            }
        }
        try {
            statement.close();
        }
        catch (Exception md) {
            // empty catch block
        }
    }

    private String getDelimiter() {
        return this.delimiter;
    }

    private void print(Object o) {
        if (this.logWriter != null) {
            this.logWriter.print(o);
        }
    }

    private void println(Object o) {
        if (this.logWriter != null) {
            this.logWriter.println(o);
        }
    }

    private void printlnError(Object o) {
        if (this.errorLogWriter != null) {
            this.errorLogWriter.println(o);
        }
    }

    private void flush() {
        if (this.logWriter != null) {
            this.logWriter.flush();
        }
        if (this.errorLogWriter != null) {
            this.errorLogWriter.flush();
        }
    }
}


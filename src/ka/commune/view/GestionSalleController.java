package ka.commune.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.sun.javafx.scene.input.ExtendedInputMethodRequests;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.PretMateriel;
import ka.commune.entity.PretSalle;
import ka.commune.entity.Salle;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class GestionSalleController implements Initializable {

    private GestionServicesSociaux gServicesSociaux;
    private PretSalle managedPretSalle=null;

    @FXML private Label labelDateDebut;
    @FXML private Label labelDateDemande;
    @FXML private Label labelDateFin;
    @FXML private JFXTextField textPhone;
    @FXML private JFXTextField textNumero;
    @FXML private JFXTextField textYear;
    @FXML private TextField textSearchPretSalle;
    @FXML private BorderPane borderPane;
    @FXML private TableView<PretSalle> tablePretSalle;
    @FXML private ScrollPane managePane;
    @FXML private JFXTextField textActivite;
    @FXML private JFXTextField textRepresentant;
    @FXML private JFXTextField textBeneficiare;
    @FXML private JFXComboBox<Salle> comboSalle;
    @FXML private JFXDatePicker dateDebut;
    @FXML private JFXDatePicker dateFin;
    @FXML private JFXDatePicker dateDemande;
    @FXML private JFXTimePicker timeBegin;
    @FXML private JFXTimePicker timeEnd;
    @FXML private TilePane buttonsPane;
    @FXML private Button buttonClose;
    @FXML private Button buttonPretSalle;
    @FXML private Button buttonUpdatePretSalle;
    @FXML private TextField textSearchSalle;
    @FXML private TableView<Salle> tableSalle;
    @FXML private JFXTextField txtSalle;

    private final TableColumn<PretSalle, String> columnActivite= new TableColumn<>("طبيعة النشاط");
    private final TableColumn<PretSalle, Integer> columnNumero= new TableColumn<>("الرقم");
    private final TableColumn<PretSalle, Integer> columnYear= new TableColumn<>("السنة");
    private final TableColumn<PretSalle, String> columnBeneficiare= new TableColumn<>("المؤسسة المستفيدة");
    private final TableColumn<PretSalle, String> columnReprersantant= new TableColumn<>("ممثل المؤسسة");
    private final TableColumn<PretSalle, String> columnPhone= new TableColumn<>("رقم الهاتف");
    private final TableColumn<PretSalle, String> columnDateDemande= new TableColumn<>("تاريخ إيداع الطلب");
    private final TableColumn<PretSalle, String> columnDateDebut= new TableColumn<>("تاريخ بداية النشاط");
    private final TableColumn<PretSalle, String> columnDateFin= new TableColumn<>("تاريخ نهاية النشاط");
    private final TableColumn<PretSalle, String> columnHeureDebut= new TableColumn<>("توقيت بداية النشاط");
    private final TableColumn<PretSalle, String> columnHeureFin= new TableColumn<>("توقيت نهاية النشاط");
    private final TableColumn<PretSalle, String> columnSalle= new TableColumn<>("القاعة");
    private final TableColumn<PretSalle, HBox> columnActionPretSalle= new TableColumn<>();

    private final TableColumn<Salle, Integer> columnNumeroSalle= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<Salle, String> columnDesignationSalle= new TableColumn<>("القاعة");
    private final TableColumn<Salle, Button> columnActionSalle= new TableColumn<>();
    @FXML private ComboBox<String> comboYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gServicesSociaux=new GestionServicesSociaux();
        textSearchPretSalle.textProperty().addListener(tapingSearchPretSAlle);
        textSearchSalle.textProperty().addListener(tapingSearchSalle);
        initializeComboYear();
        comboYear.valueProperty().addListener(selectingYear);
        dateHandler();
        remplirTablePretSalle(textSearchPretSalle.getText().toLowerCase());
        remplirTableSalle(textSearchSalle.getText().toLowerCase());
        initializeComboBox();
        manageTableSalle();
        close();
    }

    private void initializeComboYear()
    {
        comboYear.getItems().clear();
        comboYear.getItems().addAll(App.getListYears());
        for(String year: comboYear.getItems())
        {
            if(year.equals(App.getCurrentYear()+""))
                comboYear.getSelectionModel().select(year);
        }
    }

    ChangeListener<String> selectingYear= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTablePretSalle(newValue.toLowerCase());
    };

    private void showManagePane()
    {
        this.buttonsPane.getChildren().clear();
        this.buttonsPane.getChildren().add(buttonClose);
        if(this.managedPretSalle==null)
        {
            this.buttonsPane.getChildren().add(this.buttonPretSalle);
            this.borderPane.setRight(this.managePane);

        }
        else
        {
            this.borderPane.setRight(this.managePane);
            fillFields();
            this.buttonsPane.getChildren().add(this.buttonUpdatePretSalle);
        }
    }

    private void fillFields() {
        for(Salle salle : comboSalle.getItems()){
            if(salle.getIdSalle()==managedPretSalle.getSalle().getIdSalle()) {
                comboSalle.getSelectionModel().select(salle);
            }
        }
        textNumero.setText(String.valueOf(managedPretSalle.getNumero()));
        textYear.setText(String.valueOf(managedPretSalle.getYear()));
        textPhone.setText((managedPretSalle.getPhone() == null) ? "" : managedPretSalle.getPhone());
        textActivite.setText((managedPretSalle.getActivite() == null) ? "" : managedPretSalle.getActivite());
        textBeneficiare.setText((managedPretSalle.getBeneficiare() == null) ? "" : managedPretSalle.getBeneficiare());
        textRepresentant.setText((managedPretSalle.getRepresentant() == null) ? "" : managedPretSalle.getRepresentant());
        if(managedPretSalle.getDateDebut()!=null)
            dateDebut.setValue(managedPretSalle.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(managedPretSalle.getDateDemande()!=null)
            dateDemande.setValue(managedPretSalle.getDateDemande().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(managedPretSalle.getDateFin()!=null)
            dateFin.setValue(managedPretSalle.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(managedPretSalle.getHeureDebut()!=null)
            timeBegin.setValue(managedPretSalle.getHeureDebut().toLocalTime());
        if(managedPretSalle.getHeureFin()!=null)
            timeEnd.setValue(managedPretSalle.getHeureFin().toLocalTime());
    }

    ChangeListener<String> tapingSearchPretSAlle= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTablePretSalle(newValue.toLowerCase());
    };

    private void remplirTablePretSalle(String critere) {
        ObservableList<PretSalle> temp = FXCollections.observableArrayList();
        temp.clear();
        tablePretSalle.getColumns().clear();
        tablePretSalle.getItems().clear();
        columnActivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
        columnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnBeneficiare.setCellValueFactory(new PropertyValueFactory<>("beneficiare"));
        columnReprersantant.setCellValueFactory(new PropertyValueFactory<>("representant"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnSalle.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if (param.getValue().getSalle()!= null)
            {
                return new SimpleStringProperty(param.getValue().getSalle().getDesignation());
            }
            else return null;
        });
        columnHeureDebut.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if (param.getValue().getHeureDebut()!= null)
                return new SimpleStringProperty(param.getValue().getHeureDebut().toString());
            else return null;
        });
        columnHeureFin.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if (param.getValue().getHeureFin()!= null)
                return new SimpleStringProperty(param.getValue().getHeureFin().toString());
            else return null;
        });
        columnDateDemande.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateDemande()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateDemande()));
            else return null;
        });
        columnDateDebut.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateDebut()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateDebut()));
            else return null;
        });
        columnDateFin.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateFin()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateFin()));
            else return null;
        });
        columnActionPretSalle.setPrefWidth(125);
        columnActionPretSalle.setCellValueFactory(new PropertyValueFactory<>("action"));
        try {
            for(PretSalle ps : gServicesSociaux.findAllPretSalle())
            {
                if( ( ps.getSearchText().toLowerCase().contains(critere))
                && (ps.getYear()+"").contains(comboYear.getSelectionModel().getSelectedItem()))
                    temp.add(ps);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(PretSalle ps :temp)
        {
            Button delete=new Button("حذف");
            delete.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("رسالة التأكد");
                alert.setHeaderText("لإتمام الحذف إضغط على حذف");
                ButtonType buttonYes = new ButtonType("حذف");
                ButtonType buttonNo = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonYes,buttonNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && (result.get() == buttonYes)){
                    if(managedPretSalle!=null && managedPretSalle.getIdPretSalle()==ps.getIdPretSalle())
                        this.close();
                    gServicesSociaux.deletePretSalle(ps);
                    remplirTablePretSalle(textSearchPretSalle.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                //tableMembresConseil.getSelectionModel().select(mc);
                this.managedPretSalle=ps;
                showManagePane();
            });
            Button download=new Button();
            download.setOnAction(e->{
                downloadCartePretSalle(ps);
            });
            FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
            iconDown.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(download, iconDown, "#78e08f");
            ps.getAction().getChildren().clear();
            ps.getAction().getChildren().addAll(delete,update,download);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
        }
        tablePretSalle.setItems(temp);
        tablePretSalle.getColumns().addAll(columnActionPretSalle,columnNumero,columnYear,columnActivite,columnBeneficiare,columnReprersantant,columnPhone,
                columnSalle,columnDateDemande,columnDateDebut,columnDateFin,columnHeureDebut,columnHeureFin);
    }


    ChangeListener<String> tapingSearchSalle= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableSalle(newValue.toLowerCase());
    };

    private void remplirTableSalle(String critere) {
        ObservableList<Salle> temp =FXCollections.observableArrayList();
        temp.clear();
        tableSalle.getColumns().clear();
        tableSalle.getItems().clear();
        columnNumeroSalle.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesignationSalle.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnActionSalle.setCellValueFactory(new PropertyValueFactory<>("action"));
        try {
            for(Salle s : gServicesSociaux.findAllSalle())
                if( s.getDesignation()!=null && s.getDesignation().toLowerCase().contains(critere))
                    temp.add(s);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        int i=0;
        for(Salle s :temp)
        {
            s.setNumero(++i);
            Button delete=new Button("حذف");
            delete.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("رسالة التأكد");
                alert.setHeaderText("لإتمام الحذف إضغط على حذف");
                ButtonType buttonYes = new ButtonType("حذف");
                ButtonType buttonNo = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonYes,buttonNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && (result.get() == buttonYes)){
                    gServicesSociaux.deleteSalle(s);
                    remplirTableSalle(textSearchSalle.getText().toLowerCase());
                }
            });
            s.getAction().getChildren().clear();
            s.getAction().getChildren().add(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableSalle.setItems(temp);
        tableSalle.getColumns().addAll(columnNumeroSalle,columnDesignationSalle,columnActionSalle);
    }

    @FXML
    public void addPret() {
        close();
        managedPretSalle=null;
        this.showManagePane();
    }

    @FXML
    public void pretSalle() {
        if(pretSalleIsUncomplete()) return;
        PretSalle pretSalle=new PretSalle();
        fillPretSalle(pretSalle);
        String result=gServicesSociaux.addPretSalle(pretSalle);
        if(Objects.equals(result, "ok"))
        {
            close();
            textSearchPretSalle.setText("");
            remplirTablePretSalle("");
        }
        else if(Objects.equals(result, "intersection"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText(pretSalle.getSalle().getDesignation()+ " غير متاحة في هذا الوقت");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.showAndWait();
        }
    }

    @FXML
    public void updatePretSalle() {
        if(pretSalleIsUncomplete()) return;
        PretSalle pretSalle=managedPretSalle;
        fillPretSalle(pretSalle);
        String result=gServicesSociaux.updatePretSalle(pretSalle);
        if(Objects.equals(result, "ok"))
        {
            close();
            textSearchPretSalle.setText("");
            remplirTablePretSalle("");
        }
        else if(Objects.equals(result, "intersection"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText(pretSalle.getSalle().getDesignation()+ " غير متاحة في هذا الوقت");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.showAndWait();
        }
    }

    @FXML
    public void close() {
        comboSalle.getSelectionModel().clearSelection();
        textBeneficiare.setText("");
        textRepresentant.setText("");
        textActivite.setText("");
        textPhone.setText("");
        dateDemande.setValue(null);
        dateDebut.setValue(null);
        dateFin.setValue(null);
        timeBegin.setValue(null);
        timeEnd.setValue(null);
        borderPane.setRight(null);
        labelDateDebut.setVisible(false);
        labelDateDemande.setVisible(false);
        labelDateFin.setVisible(false);
        managedPretSalle=null;
    }
    @FXML
    public void addSalle() {
        if(salleIsUncomplete()) return;
        Salle salle=new Salle();
        salle.setDesignation(txtSalle.getText());
        if(gServicesSociaux.addSalle(salle))
        {
            txtSalle.setText("");
            textSearchSalle.setText("");
            remplirTableSalle(textSearchSalle.getText());
            initializeComboBox();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("هذه القاعة موجودة بالفعل");
            alert.showAndWait();
        }
    }

    private void manageTableSalle()
    {
        columnDesignationSalle.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDesignationSalle.setOnEditCommit(this::handle);
    }

    private void initializeComboBox() {
        comboSalle.getItems().clear();
        comboSalle.getItems().addAll(gServicesSociaux.findAllSalle());
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    private boolean pretSalleIsUncomplete()
    {
        if(comboSalle.getValue()==null || textActivite.getText().equals("") || textBeneficiare.getText().equals("")
                || dateDebut.getValue()==null || textRepresentant.getText().equals("") || dateDemande.getValue()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المعلومات التالية ضرورية:\n+ طبيعة النشاط\n+ المؤسسة المستفيدة\n+ممثل المؤسسة\n+القاعة\n+تاريخ البداية");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private boolean salleIsUncomplete()
    {
        if(txtSalle.getText().equals("") )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال اسم القاعة");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private void fillPretSalle(PretSalle pretSalle)
    {
        pretSalle.setNumero(Integer.parseInt(textNumero.getText()));
        pretSalle.setYear(Integer.parseInt(textYear.getText()));
        pretSalle.setSalle(comboSalle.getSelectionModel().getSelectedItem());
        pretSalle.setActivite(textActivite.getText());
        pretSalle.setBeneficiare(textBeneficiare.getText());
        pretSalle.setRepresentant(textRepresentant.getText());
        pretSalle.setPhone(textPhone.getText());
        if(dateDebut.getValue()!=null)
            pretSalle.setDateDebut(Date.from(Instant.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()))));
        if(dateDemande.getValue()!=null)
            pretSalle.setDateDemande(Date.from(Instant.from(dateDemande.getValue().atStartOfDay(ZoneId.systemDefault()))));
        if(dateFin.getValue()!=null)
            pretSalle.setDateFin(Date.from(Instant.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()))));
        if(timeBegin.getValue()!=null)
            pretSalle.setHeureDebut(Time.valueOf(timeBegin.getValue()));
        if(timeEnd.getValue()!=null)
            pretSalle.setHeureFin(Time.valueOf(timeEnd.getValue()));
    }

    private void handle(TableColumn.CellEditEvent<Salle, String> edittedCell) {
        // TODO Auto-generated method stub
        Salle salle = tableSalle.getSelectionModel().getSelectedItem();
        if (edittedCell.getNewValue().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال القاعة");
            salle.setDesignation(edittedCell.getOldValue());
            alert.showAndWait();
        } else {
            salle.setDesignation(edittedCell.getNewValue());

            if (!gServicesSociaux.updateSalle(salle)) {
                salle.setDesignation(edittedCell.getOldValue());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("خطأ");
                alert.setHeaderText("هذه القاعة موجودة بالفعل");
                alert.showAndWait();
            }
        }
        remplirTableSalle(textSearchSalle.getText().toLowerCase());
        initializeComboBox();
        remplirTablePretSalle(textSearchPretSalle.getText().toLowerCase());
    }

    private void setButtonFont(Button btn, GlyphIcon f, String Color) {
        f.setSize("16");
        btn.setText("");
        btn.setGraphic(f);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    private void downloadCartePretSalle(PretSalle p) {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadCartePretSalle(p,selectedDirectory.getAbsolutePath()))
            {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("تم بنجاح");
                dialog.setHeaderText("العملية تمت بنجاح !");
                dialog.showAndWait();
            }
            else
            {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("تخطأ");
                dialog.showAndWait();
            }
        }
        catch(NullPointerException e) {
            return;
        }
    }

    private void dateHandler()
    {
        dateDemande.valueProperty().addListener((observable, oldValue, newValue)->{
            if(newValue!= null)
            {
                labelDateDemande.setVisible(true);
                if(!newValue.equals(oldValue)) {
                    if(dateDebut.getValue()!=null)
                    {
                        if(dateDebut.getValue().isBefore(newValue))
                            dateDebut.setValue(newValue);
                    }
                    if(dateFin.getValue()!=null)
                    {
                        if(dateFin.getValue().isBefore(newValue))
                            dateFin.setValue(newValue);
                    }
                }
            }
        });
        dateDebut.valueProperty().addListener((observable, oldValue, newValue) -> {
            // TODO Auto-generated method stub
            if(newValue!= null)
            {
                labelDateDebut.setVisible(true);
                if(!newValue.equals(oldValue)) {
                    if(dateDemande.getValue()!=null)
                    {
                        if(newValue.isBefore(dateDemande.getValue()))
                        {
                            if(oldValue!=null)
                                dateDebut.setValue(oldValue);
                            else
                                dateDebut.setValue(dateDemande.getValue());
                        }
                    }
                    if(dateFin.getValue()!=null)
                    {
                        if(newValue.isAfter(dateFin.getValue()))
                        {
                            dateFin.setValue(newValue);
                        }
                    }
                }
            }
        });
        dateFin.valueProperty().addListener((observable, oldValue, newValue) -> {
            // TODO Auto-generated method stub
            if(newValue!= null)
            {
                labelDateFin.setVisible(true);
                if(dateDemande.getValue()!=null)
                {
                    if(newValue.isBefore(dateDemande.getValue()))
                    {
                        if(oldValue!=null)
                            dateFin.setValue(oldValue);
                        else
                            dateFin.setValue(dateDemande.getValue());
                    }
                }
                if(dateDebut.getValue()!=null)
                {
                    if(newValue.isBefore(dateDebut.getValue()))
                    {
                        if(oldValue!=null)
                            dateFin.setValue(oldValue);
                        else
                            dateFin.setValue(dateDebut.getValue());
                    }
                }
            }
        });
    }
}

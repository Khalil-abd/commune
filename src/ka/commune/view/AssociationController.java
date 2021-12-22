package ka.commune.view;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.Association;
import ka.commune.entity.ReunionAssociation;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class AssociationController implements Initializable {

    @FXML private TextField textSearchAssociation;
    @FXML private BorderPane borderPaneAssociation;
    @FXML private TableView<Association> tableAssociation;
    @FXML private ScrollPane managedPaneAssociation;
    @FXML private JFXTextField textNom;
    @FXML private JFXTextField textAdresse;
    @FXML private JFXTextField textRepresantant;
    @FXML private JFXTextField textPhone;
    @FXML private JFXTextField textDomaine;
    @FXML private JFXDatePicker dateFondation;
    @FXML private JFXDatePicker dateRenouvelement;
    @FXML private JFXDatePicker dateExpiration;
    @FXML private TilePane buttonsPaneAssociation;
    @FXML private Button buttonCloseAssociation;
    @FXML private Button buttonAddAssociation;
    @FXML private Button buttonUpdateAssociation;
    @FXML private Button buttonUpdateReunion;
    @FXML private Button buttonAddReunion;
    @FXML private TextField textSearchReunion;
    @FXML private BorderPane borderPaneReunion;
    @FXML private TableView<ReunionAssociation> tableReunion;
    @FXML private ScrollPane managedPaneReunion;
    @FXML private JFXTextField textSujet;
    @FXML private JFXDatePicker dateReunion;
    @FXML private JFXTimePicker timeReunion;
    @FXML private TilePane buttonsPaneReunion;
    @FXML private Button buttonCloseReunion;
    @FXML private ComboBox<String> comboYear;
    @FXML private JFXTextField textEmail;

    private GestionServicesSociaux gServicesSociaux=new GestionServicesSociaux();
    private Association managedAssociation;
    private ReunionAssociation managedReunion;

    // Association Columns

    private final TableColumn<Association, Integer> columnNumeroAssociation= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<Association, String> columnNom= new TableColumn<>("اسم الجمعية");
    private final TableColumn<Association, String> columnAdresse= new TableColumn<>("العنوان");
    private final TableColumn<Association, String> columnRepresantant= new TableColumn<>("ممثل الجمعية");
    private final TableColumn<Association, String> columnPhone= new TableColumn<>("الهاتف");
    private final TableColumn<Association, String> columnEmail= new TableColumn<>("البريد الإلكتروني");
    private final TableColumn<Association, String> columnDomaine= new TableColumn<>("المجال الرئيسي للجمعية");
    private final TableColumn<Association, String> columnDateFondation= new TableColumn<>("تاريخ التأسيس");
    private final TableColumn<Association, String> columnDateRenouvelement= new TableColumn<>("تاريخ التجديد");
    private final TableColumn<Association, String> columnDateExpiration= new TableColumn<>("تاريخ انتهاء المكتب");
    private final TableColumn<Association, FlowPane> columnActionAssociation= new TableColumn<>();

    // Reunion Columns
    private final TableColumn<ReunionAssociation, Integer> columnNumeroReunion= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<ReunionAssociation, String> columnSujet= new TableColumn<>("موضوع الاجتماع");
    private final TableColumn<ReunionAssociation, String> columnDateReunion= new TableColumn<>("تاريخ الإجتماع");
    private final TableColumn<ReunionAssociation, String> columnTimeReunion= new TableColumn<>("توقيت الإجتماع");
    private final TableColumn<ReunionAssociation, HBox> columnActionReunion= new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resizeColumns();
        closeReunion();
        closeAssociation();
        initializeComboYear();
        comboYear.valueProperty().addListener(selectingYear);
        textSearchReunion.textProperty().addListener(tapingSearchReunion);
        textSearchAssociation.textProperty().addListener(tapingSearchAssociation);
        remplirTableReunion(textSearchReunion.getText().toLowerCase());
        remplirTableAssociation(textSearchAssociation.getText().toLowerCase());
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

    private void resizeColumns()
    {
        columnActionReunion.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnActionAssociation.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnTimeReunion.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnDateReunion.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnNumeroReunion.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnSujet.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnDomaine.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnRepresantant.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnAdresse.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnNom.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnNumeroAssociation.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnDateExpiration.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnDateRenouvelement.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnDateFondation.setPrefWidth(Region.USE_COMPUTED_SIZE);
        columnPhone.setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    @FXML
    void addNewAssociation() {
        closeAssociation();
        managedAssociation=null;
        this.showManagePaneAssociation();
    }

    private void showManagePaneAssociation() {
        this.buttonsPaneAssociation.getChildren().clear();
        this.buttonsPaneAssociation.getChildren().add(buttonCloseAssociation);
        if(this.managedAssociation==null)
        {
            this.buttonsPaneAssociation.getChildren().add(this.buttonAddAssociation);
            this.borderPaneAssociation.setRight(this.managedPaneAssociation);

        }
        else
        {
            this.borderPaneAssociation.setRight(this.managedPaneAssociation);
            fillFieldsAssociation();
            this.buttonsPaneAssociation.getChildren().add(this.buttonUpdateAssociation);
        }
    }

    private boolean associationIsUncomplete(Association association) {
        if(dateFondation.getValue()==null)
            return true;
        if(dateRenouvelement.getValue()==null)
            return true;
        if(dateExpiration.getValue()==null)
            return true;
        if(textNom.getText().equals(""))
            return true;
        if(textAdresse.getText().equals(""))
            return true;
        if(textPhone.getText().equals(""))
            return true;
        if(textDomaine.getText().equals(""))
            return true;
        if(textRepresantant.getText().equals(""))
            return true;
        return false;
    }

    private void fillFieldsAssociation() {
        dateFondation.setValue(managedAssociation.getDateFondation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateRenouvelement.setValue(managedAssociation.getDateRenouvelement().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateExpiration.setValue(managedAssociation.getDateExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        textNom.setText(managedAssociation.getNom());
        textAdresse.setText(managedAssociation.getAdresse());
        textPhone.setText(managedAssociation.getPhone());
        textDomaine.setText(managedAssociation.getDomaine());
        textRepresantant.setText(managedAssociation.getRepresantant());
        if(managedAssociation.getEmail()==null || managedAssociation.getEmail().equals(""))
            textEmail.setText("");
        else
            textEmail.setText(managedAssociation.getEmail());
    }

    @FXML
    private void addNewReunion()
    {
        closeReunion();
        managedReunion=null;
        this.showManagePaneReunion();
    }

    private void showManagePaneReunion()
    {
        this.buttonsPaneReunion.getChildren().clear();
        this.buttonsPaneReunion.getChildren().add(buttonCloseReunion);
        if(this.managedReunion==null)
        {
            this.buttonsPaneReunion.getChildren().add(this.buttonAddReunion);
            this.borderPaneReunion.setRight(this.managedPaneReunion);

        }
        else
        {
            this.borderPaneReunion.setRight(this.managedPaneReunion);
            fillFieldsReunion();
            this.buttonsPaneReunion.getChildren().add(this.buttonUpdateReunion);
        }
    }

    private void fillFieldsReunion()
    {
        textSujet.setText(managedReunion.getSujet());
        timeReunion.setValue(managedReunion.getTime().toLocalTime());
        dateReunion.setValue(managedReunion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @FXML
    void closeAssociation() {
        textNom.setText("");
        textAdresse.setText("");
        textRepresantant.setText("");
        textEmail.setText("");
        textPhone.setText("");
        textDomaine.setText("");
        dateFondation.setValue(null);
        dateRenouvelement.setValue(null);
        dateExpiration.setValue(null);
        managedAssociation=null;
        borderPaneAssociation.setRight(null);
    }

    @FXML
    void closeReunion() {
        textSujet.setText("");
        dateReunion.setValue(null);
        timeReunion.setValue(null);
        managedReunion=null;
        borderPaneReunion.setRight(null);
    }

    ChangeListener<String> tapingSearchAssociation= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableAssociation(newValue.toLowerCase());
    };

    ChangeListener<String> selectingYear= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableReunion(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchReunion= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableReunion(newValue.toLowerCase());
    };

    private void remplirTableAssociation (String critere)
    {
        ObservableList<Association> temp = FXCollections.observableArrayList();
        temp.clear();
        tableAssociation.getColumns().clear();
        tableAssociation.getItems().clear();
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        columnRepresantant.setCellValueFactory(new PropertyValueFactory<>("represantant"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnDomaine.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnNumeroAssociation.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDateFondation.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateFondation()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateFondation()));
            else return null;
        });
        columnDateRenouvelement.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateRenouvelement()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateRenouvelement()));
            else return null;
        });
        columnDateExpiration.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateExpiration()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateExpiration()));
            else return null;
        });
        columnActionAssociation.setCellValueFactory(new PropertyValueFactory("action"));
        columnActionAssociation.setPrefWidth(125);
        columnActionAssociation.setResizable(false);
        try {
            int numero=1;
            for(Association a : gServicesSociaux.findAllAssociation())
            {
                if( a.getSearchText().contains(critere)) {
                    a.setNumero(numero++);
                    temp.add(a);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(Association a :temp)
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
                    if(managedAssociation!=null && managedAssociation.getIdAssociation()==a.getIdAssociation())
                        this.closeAssociation();
                    gServicesSociaux.deleteAssociation(a);
                    remplirTableAssociation(textSearchAssociation.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                //tableMembresConseil.getSelectionModel().select(mc);
                this.managedAssociation=a;
                showManagePaneAssociation();
            });
            Button download=new Button();
            download.setOnAction(e->{
                List<Association> associations=new ArrayList<>();
                associations.add(a);
                getAssociationInfo(associations);
            });
            Button print=new Button("print");
            print.setOnAction(e->{
                PdfGenerator pdf=new PdfGenerator();
                List<Association> associations=new ArrayList<>();
                associations.add(a);
                pdf.printAssociationInformation(associations);
            });
            a.getAction().getChildren().clear();
            a.getAction().getChildren().addAll(delete,update,download);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");

            FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
            iconDown.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(download, iconDown, "#78e08f");

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
            icon.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(print, icon, "#2C3A47");
        }
        tableAssociation.setItems(temp);
        tableAssociation.getColumns().addAll(columnActionAssociation,columnNumeroAssociation,columnNom,columnAdresse,columnRepresantant,
                columnPhone,columnEmail,columnDomaine,columnDateFondation,columnDateRenouvelement,columnDateExpiration);

    }

    private void remplirTableReunion (String critere)
    {
        ObservableList<ReunionAssociation> temp = FXCollections.observableArrayList();
        temp.clear();
        tableReunion.getColumns().clear();
        tableReunion.getItems().clear();
        columnSujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        columnNumeroReunion.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDateReunion.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDate()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDate()));
            else return null;
        });
        columnTimeReunion.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if (param.getValue().getTime()!= null)
                return new SimpleStringProperty(param.getValue().getTime().toString());
            else return null;
        });
        columnActionReunion.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int numero=1;
            for(ReunionAssociation a : gServicesSociaux.findAllReunionAssociation())
            {
                if( a.getSearchText().contains(critere)
                        && a.getDate().toString().contains(comboYear.getSelectionModel().getSelectedItem())) {
                    a.setNumero(numero++);
                    temp.add(a);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(ReunionAssociation a :temp)
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
                    if(managedAssociation!=null && managedAssociation.getIdAssociation()==a.getIdReunion())
                        this.closeAssociation();
                    gServicesSociaux.deleteReunionAssociation(a);
                    remplirTableReunion(textSearchReunion.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                //tableMembresConseil.getSelectionModel().select(mc);
                this.managedReunion=a;
                showManagePaneReunion();
            });
            Button invite=new Button("دعوة");
            invite.setOnAction(e->{
                inviteAssociation(a);
            });
            a.getAction().getChildren().clear();
            a.getAction().getChildren().addAll(delete,update,invite);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ENVELOPE);
            icon.setFill(Paint.valueOf("#000"));
            setButtonFont(invite, icon, "#F8EFBA");
        }
        tableReunion.setItems(temp);
        tableReunion.getColumns().addAll(columnActionReunion,columnNumeroReunion,columnSujet,columnDateReunion,columnTimeReunion);
    }

    private void inviteAssociation(ReunionAssociation ra)
    {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.inviteAssociation(tableAssociation.getItems(), ra,selectedDirectory.getAbsolutePath()))
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
                dialog.setHeaderText("لائحة الجمعيات فارغة !");
                dialog.showAndWait();
            }
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return;
        }
    }

    @FXML public void downloadListAssociation()
    {
        if(tableAssociation.getItems().size()==0)
        {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("تخطأ");
            dialog.setHeaderText("لائحة الجمعيات فارغة !");
            dialog.showAndWait();
            return;
        }
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadListAssociation(tableAssociation.getItems(),selectedDirectory.getAbsolutePath(),textSearchAssociation.getText()))
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

    private void getAssociationInfo(List<Association> associations)
    {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadAssociationInformation(associations,selectedDirectory.getAbsolutePath()))
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

    @FXML
    void addAssociation() {
        Association association=new Association();
        if(associationIsUncomplete(association)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return ;
        }
        fillAssociation(association);
        if(gServicesSociaux.addAssociation(association))
        {
            closeAssociation();
            textSearchAssociation.setText("");
            remplirTableAssociation(textSearchAssociation.getText().toLowerCase());
        }
    }



    @FXML
    void addReunion() {
        ReunionAssociation reunionAssociation=new ReunionAssociation();
        if(reunionIsUncomplete(reunionAssociation)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return;
        }
        fillReunion(reunionAssociation);
        if(gServicesSociaux.addReunionAssociation(reunionAssociation)){
            closeReunion();
            textSearchReunion.setText("");
            remplirTableReunion(textSearchReunion.getText().toLowerCase());
        }
    }

    private boolean fillAssociation(Association association)
    {
        try {
            association.setEmail((!textEmail.getText().equals(""))?textEmail.getText():"");
            association.setNom(textNom.getText());
            association.setAdresse(textAdresse.getText());
            association.setRepresantant(textRepresantant.getText());
            association.setPhone(textPhone.getText());
            association.setDateFondation(Date.from(Instant.from(dateFondation.getValue().atStartOfDay(ZoneId.systemDefault()))));
            association.setDateRenouvelement(Date.from(Instant.from(dateRenouvelement.getValue().atStartOfDay(ZoneId.systemDefault()))));
            association.setDateExpiration(Date.from(Instant.from(dateExpiration.getValue().atStartOfDay(ZoneId.systemDefault()))));
            association.setDomaine(textDomaine.getText());
            return true;
        }catch (Exception e)
        {
            return false;
        }
        
    }

    private boolean reunionIsUncomplete(ReunionAssociation reunionAssociation) {
        if(textSujet.getText().equals(""))
            return true;
        if(dateReunion.getValue()==null)
            return true;
        if(timeReunion.getValue()==null)
            return true;
        return false;
    }


    private boolean fillReunion(ReunionAssociation reunionAssociation)
    {
        try {
            reunionAssociation.setSujet(textSujet.getText());
            reunionAssociation.setDate(Date.from(Instant.from(dateReunion.getValue().atStartOfDay(ZoneId.systemDefault()))));
            reunionAssociation.setTime(Time.valueOf(timeReunion.getValue()));
            return true;
        }catch (Exception e)
        {
            return false;
        }
        
    }

    @FXML
    void updateAssociation() {
        Association association=managedAssociation;
        if(associationIsUncomplete(association)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return ;
        }
        fillAssociation(association);
        if(gServicesSociaux.updateAssociation(association))
        {
            closeAssociation();
            textSearchAssociation.setText("");
            remplirTableAssociation(textSearchAssociation.getText().toLowerCase());
        }
    }

    @FXML
    void updateReunion() {
        ReunionAssociation reunionAssociation = managedReunion;
        if(reunionIsUncomplete(reunionAssociation))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return;
        }
        fillReunion(reunionAssociation);
        if(gServicesSociaux.updateReunionAssociation(reunionAssociation)){
            closeReunion();
            textSearchReunion.setText("");
            remplirTableReunion(textSearchReunion.getText().toLowerCase());
        }
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 20, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    private void setButtonFont(Button btn, GlyphIcon f, String Color) {
        f.setSize("16");
        btn.setText("");
        btn.setGraphic(f);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }
}


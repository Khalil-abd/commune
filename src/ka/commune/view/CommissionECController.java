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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.Association;
import ka.commune.entity.CommissionEC;
import ka.commune.entity.ReunionCommissionec;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class CommissionECController implements Initializable {
    @FXML
    private TextField textSearchCommissionEC;
    @FXML
    private BorderPane borderPaneCommissionEC;
    @FXML
    private TableView<CommissionEC> tableCommissionEC;
    @FXML
    private ScrollPane managedPaneCommissionEC;
    @FXML
    private JFXTextField textNom;
    @FXML
    private JFXTextField textAdresse;
    @FXML
    private JFXTextField textRepresantant;
    @FXML
    private JFXTextField textPhone;
    @FXML
    private JFXTextField textDomaine;
    @FXML
    private JFXDatePicker dateFondation;
    @FXML
    private JFXDatePicker dateRenouvelement;
    @FXML
    private JFXDatePicker dateExpiration;
    @FXML
    private TilePane buttonsPaneCommissionEC;
    @FXML
    private Button buttonCloseCommissionEC;
    @FXML
    private Button buttonAddCommissionEC;
    @FXML
    private Button buttonUpdateCommissionEC;
    @FXML
    private Button buttonUpdateReunion;
    @FXML
    private Button buttonAddReunion;
    @FXML
    private TextField textSearchReunion;
    @FXML
    private BorderPane borderPaneReunion;
    @FXML
    private TableView<ReunionCommissionec> tableReunion;
    @FXML
    private ScrollPane managedPaneReunion;
    @FXML
    private JFXTextField textSujet;
    @FXML
    private JFXDatePicker dateReunion;
    @FXML
    private JFXTimePicker timeReunion;
    @FXML
    private TilePane buttonsPaneReunion;
    @FXML
    private Button buttonCloseReunion;
    @FXML private ComboBox<String> comboYear;

    private GestionServicesSociaux gServicesSociaux=new GestionServicesSociaux();
    private CommissionEC managedCommissionEC;
    private ReunionCommissionec managedReunion;

    // CommissionEC Columns

    private final TableColumn<CommissionEC, Integer> columnNumeroCommissionEC= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<CommissionEC, String> columnNom= new TableColumn<>("اسم المؤسسة");
    private final TableColumn<CommissionEC, String> columnAdresse= new TableColumn<>("العنوان");
    private final TableColumn<CommissionEC, String> columnRepresantant= new TableColumn<>("ممثل المؤسسة");
    private final TableColumn<CommissionEC, String> columnPhone= new TableColumn<>("الهاتف");
    private final TableColumn<CommissionEC, String> columnDomaine= new TableColumn<>("المجال الرئيسي للمؤسسة");
    private final TableColumn<CommissionEC, String> columnDateFondation= new TableColumn<>("تاريخ التأسيس");
    private final TableColumn<CommissionEC, String> columnDateRenouvelement= new TableColumn<>("تاريخ التجديد");
    private final TableColumn<CommissionEC, String> columnDateExpiration= new TableColumn<>("تاريخ انتهاء المكتب");
    private final TableColumn<CommissionEC, HBox> columnActionCommissionEC= new TableColumn<>();

    // Reunion Columns
    private final TableColumn<ReunionCommissionec, Integer> columnNumeroReunion= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<ReunionCommissionec, String> columnSujet= new TableColumn<>("موضوع الاجتماع");
    private final TableColumn<ReunionCommissionec, String> columnDateReunion= new TableColumn<>("تاريخ الإجتماع");
    private final TableColumn<ReunionCommissionec, String> columnTimeReunion= new TableColumn<>("توقيت الإجتماع");
    private final TableColumn<ReunionCommissionec, HBox> columnActionReunion= new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeCommissionEC();
        closeReunion();
        initializeComboYear();
        comboYear.valueProperty().addListener(selectingYear);
        textSearchReunion.textProperty().addListener(tapingSearchReunion);
        textSearchCommissionEC.textProperty().addListener(tapingSearchCommissionEC);
        remplirTableReunion(textSearchReunion.getText().toLowerCase());
        remplirTableCommissionEC(textSearchCommissionEC.getText().toLowerCase());
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
        remplirTableReunion(newValue.toLowerCase());
    };

    @FXML
    void addNewCommissionEC() {
        closeCommissionEC();
        managedCommissionEC=null;
        this.showManagePaneCommissionEC();
    }

    private void showManagePaneCommissionEC() {
        this.buttonsPaneCommissionEC.getChildren().clear();
        this.buttonsPaneCommissionEC.getChildren().add(buttonCloseCommissionEC);
        if(this.managedCommissionEC==null)
        {
            this.buttonsPaneCommissionEC.getChildren().add(this.buttonAddCommissionEC);
            this.borderPaneCommissionEC.setRight(this.managedPaneCommissionEC);

        }
        else
        {
            this.borderPaneCommissionEC.setRight(this.managedPaneCommissionEC);
            fillFieldsCommissionEC();
            this.buttonsPaneCommissionEC.getChildren().add(this.buttonUpdateCommissionEC);
        }
    }

    private void fillFieldsCommissionEC() {
        dateFondation.setValue(managedCommissionEC.getDateFondation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateRenouvelement.setValue(managedCommissionEC.getDateRenouvelement().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateExpiration.setValue(managedCommissionEC.getDateExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        textNom.setText(managedCommissionEC.getNom());
        textAdresse.setText(managedCommissionEC.getAdresse());
        textPhone.setText(managedCommissionEC.getPhone());
        textDomaine.setText(managedCommissionEC.getDomaine());
        textRepresantant.setText(managedCommissionEC.getRepresantant());
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
    void closeCommissionEC() {
        textNom.setText("");
        textAdresse.setText("");
        textRepresantant.setText("");
        textPhone.setText("");
        textDomaine.setText("");
        dateFondation.setValue(null);
        dateRenouvelement.setValue(null);
        dateExpiration.setValue(null);
        managedCommissionEC=null;
        borderPaneCommissionEC.setRight(null);
    }

    @FXML
    void closeReunion() {
        textSujet.setText("");
        dateReunion.setValue(null);
        timeReunion.setValue(null);
        managedReunion=null;
        borderPaneReunion.setRight(null);
    }

    ChangeListener<String> tapingSearchCommissionEC= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableCommissionEC(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchReunion= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableReunion(newValue.toLowerCase());
    };

    private void remplirTableCommissionEC (String critere)
    {
        ObservableList<CommissionEC> temp = FXCollections.observableArrayList();
        temp.clear();
        tableCommissionEC.getColumns().clear();
        tableCommissionEC.getItems().clear();
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        columnRepresantant.setCellValueFactory(new PropertyValueFactory<>("represantant"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnDomaine.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
        columnNumeroCommissionEC.setCellValueFactory(new PropertyValueFactory<>("numero"));
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
        columnActionCommissionEC.setPrefWidth(125);
        columnActionCommissionEC.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int numero=1;
            for(CommissionEC a : gServicesSociaux.findAllCommissionEC())
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
        for(CommissionEC a :temp)
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
                    if(managedCommissionEC!=null && managedCommissionEC.getIdCommissionEC()==a.getIdCommissionEC())
                        this.closeCommissionEC();
                    gServicesSociaux.deleteCommissionEC(a);
                    remplirTableCommissionEC(textSearchCommissionEC.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                //tableMembresConseil.getSelectionModel().select(mc);
                this.managedCommissionEC=a;
                showManagePaneCommissionEC();
            });
            Button download=new Button();
            download.setOnAction(e->{
                List<CommissionEC> associations=new ArrayList<>();
                associations.add(a);
                getCommissionEcInfo(associations);
            });
            a.getAction().getChildren().clear();
            a.getAction().getChildren().addAll(delete,update,download);

            FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
            iconDown.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(download, iconDown, "#78e08f");
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
        }
        tableCommissionEC.setItems(temp);
        tableCommissionEC.getColumns().addAll(columnActionCommissionEC,columnNumeroCommissionEC,columnNom,columnAdresse,columnRepresantant,
                columnPhone,columnDomaine,columnDateFondation,columnDateRenouvelement,columnDateExpiration);

    }

    private void setButtonFont(Button btn, GlyphIcon f, String Color) {
        f.setSize("16");
        btn.setText("");
        btn.setGraphic(f);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    private void remplirTableReunion (String critere)
    {
        ObservableList<ReunionCommissionec> temp = FXCollections.observableArrayList();
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
            for(ReunionCommissionec a : gServicesSociaux.findAllReunionCommissionEC())
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
        for(ReunionCommissionec a :temp)
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
                    if(managedCommissionEC!=null && managedCommissionEC.getIdCommissionEC()==a.getIdReunion())
                        this.closeCommissionEC();
                    gServicesSociaux.deleteReunionCommissionEC(a);
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
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ENVELOPE);
            icon.setFill(Paint.valueOf("#000"));
            setButtonFont(invite, icon, "#F8EFBA");
            a.getAction().getChildren().clear();
            a.getAction().getChildren().addAll(delete,update,invite);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");

        }
        tableReunion.setItems(temp);
        tableReunion.getColumns().addAll(columnActionReunion,columnNumeroReunion,columnSujet,columnDateReunion,columnTimeReunion);
    }

    private void inviteAssociation(ReunionCommissionec ra)
    {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.inviteCommissionEC(tableCommissionEC.getItems(), ra,selectedDirectory.getAbsolutePath()))
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
                dialog.setHeaderText("لائحة الأعضاء فارغة !");
                dialog.showAndWait();
            }
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return;
        }
    }

    private void getCommissionEcInfo(List<CommissionEC> associations)
    {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadCommissionECInformation(associations,selectedDirectory.getAbsolutePath()))
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
            e.printStackTrace();
            return;
        }
    }

    @FXML
    void addCommissionEC() {
        CommissionEC CommissionEC=new CommissionEC();
        if(CommissionECIsUncomplete(CommissionEC)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return ;
        }
        fillCommissionEC(CommissionEC);
        if(gServicesSociaux.addCommissionEC(CommissionEC))
        {
            closeCommissionEC();
            textSearchCommissionEC.setText("");
            remplirTableCommissionEC(textSearchCommissionEC.getText().toLowerCase());
        }
    }

    private boolean CommissionECIsUncomplete(CommissionEC CommissionEC) {
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

    @FXML
    void addReunion() {
        ReunionCommissionec ReunionCommissionec=new ReunionCommissionec();
        fillReunion(ReunionCommissionec);
        if(reunionIsUncomplete(ReunionCommissionec)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return;
        }
        if(gServicesSociaux.addReunionCommissionEC(ReunionCommissionec)){
            closeReunion();
            textSearchReunion.setText("");
            remplirTableReunion(textSearchReunion.getText().toLowerCase());
        }
    }

    private boolean reunionIsUncomplete(ReunionCommissionec ReunionCommissionec) {
        if(textSujet.getText().equals(""))
            return true;
        if(dateReunion.getValue()==null)
            return true;
        if(timeReunion.getValue()==null)
            return true;
        return false;
    }

    @FXML public void downloadListCommissionEc()
    {
        if(tableCommissionEC.getItems().size()==0)
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
            if(tp.downloadListCommissionEC(tableCommissionEC.getItems(),selectedDirectory.getAbsolutePath()))
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

    private void fillCommissionEC(CommissionEC CommissionEC)
    {
        try{
            CommissionEC.setNom(textNom.getText());
            CommissionEC.setAdresse(textAdresse.getText());
            CommissionEC.setRepresantant(textRepresantant.getText());
            CommissionEC.setPhone(textPhone.getText());
            CommissionEC.setDateFondation(Date.from(Instant.from(dateFondation.getValue().atStartOfDay(ZoneId.systemDefault()))));
            CommissionEC.setDateRenouvelement(Date.from(Instant.from(dateRenouvelement.getValue().atStartOfDay(ZoneId.systemDefault()))));
            CommissionEC.setDateExpiration(Date.from(Instant.from(dateExpiration.getValue().atStartOfDay(ZoneId.systemDefault()))));
            CommissionEC.setDomaine(textDomaine.getText());
        }catch (Exception e){}
    }

    private void fillReunion(ReunionCommissionec ReunionCommissionec)
    {
        try {
            ReunionCommissionec.setSujet(textSujet.getText());
            ReunionCommissionec.setDate(Date.from(Instant.from(dateReunion.getValue().atStartOfDay(ZoneId.systemDefault()))));
            ReunionCommissionec.setTime(Time.valueOf(timeReunion.getValue()));
        }catch (Exception e){}
    }

    @FXML
    void updateCommissionEC() {
        CommissionEC CommissionEC=managedCommissionEC;
        if(CommissionECIsUncomplete(CommissionEC)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return ;
        }
        fillCommissionEC(CommissionEC);
        if(gServicesSociaux.updateCommissionEC(CommissionEC))
        {
            closeCommissionEC();
            textSearchCommissionEC.setText("");
            remplirTableCommissionEC(textSearchCommissionEC.getText().toLowerCase());
        }
    }

    @FXML
    void updateReunion() {
        ReunionCommissionec ReunionCommissionec = managedReunion;
        fillReunion(ReunionCommissionec);
        if(reunionIsUncomplete(ReunionCommissionec)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("المرجو إدخال جميع المعلومات");
            alert.showAndWait();
            return;
        }
        if(gServicesSociaux.updateReunionCommissionEC(ReunionCommissionec)){
            closeReunion();
            textSearchReunion.setText("");
            remplirTableReunion(textSearchReunion.getText().toLowerCase());
        }
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }
}

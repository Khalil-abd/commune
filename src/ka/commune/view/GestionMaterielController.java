package ka.commune.view;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.*;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionMaterielController implements Initializable {

    @FXML private TextField textSearchPret;
    @FXML private BorderPane borderPane;
    @FXML private TableView<PretMateriel> tablePret;
    @FXML private JFXTextField textMateriel;
    @FXML private TextField textSearchMateriel;
    @FXML private JFXTextField textQuantite;
    @FXML private TableView<Materiel> tableMateriel;

    private final TableColumn<PretMateriel,String> columnActivite=new TableColumn<>("طبيعة النشاط");
    private final TableColumn<PretMateriel, Integer> columnNumero= new TableColumn<>("الرقم");
    private final TableColumn<PretMateriel, Integer> columnYear= new TableColumn<>("السنة");
    private final TableColumn<PretMateriel, String> columnBeneficiare= new TableColumn<>("المؤسسة المستفيدة");
    private final TableColumn<PretMateriel, String> columnReprersantant= new TableColumn<>("ممثل المؤسسة");
    private final TableColumn<PretMateriel, String> columnPhone= new TableColumn<>("رقم الهاتف");
    private final TableColumn<PretMateriel, String> columnDateDemande= new TableColumn<>("تاريخ الطلب");
    private final TableColumn<PretMateriel, String> columnDateDebut= new TableColumn<>("تاريخ البداية");
    private final TableColumn<PretMateriel, String> columnDateFin= new TableColumn<>("تاريخ النهاية");
    private final TableColumn<PretMateriel, HBox> columnActionPret= new TableColumn<>();

    private final TableColumn<Materiel, Integer> columnNumeroMateriel= new TableColumn<>("الرقم الترتيبي");
    private final TableColumn<Materiel, String> columnDesignation= new TableColumn<>("المعدة");
    private final TableColumn<Materiel, Double> columnQuantite= new TableColumn<>("الكمية");
    private final TableColumn<Materiel, Button> columnActionMateriel= new TableColumn<>();

    @FXML private ComboBox<String> comboYear;

    private GestionServicesSociaux gServicesSociaux=new GestionServicesSociaux();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manageMateriel();
        initializeComboYear();
        comboYear.valueProperty().addListener(selectingYear);
        remplirTableMateriel(textSearchMateriel.getText().toLowerCase());
        remplirTablePret(textSearchPret.getText().toLowerCase());
        textSearchPret.textProperty().addListener(tapingSearchPret);
        textSearchMateriel.textProperty().addListener(tapingSearchMateriel);
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
        remplirTablePret(newValue.toLowerCase());
    };

    @FXML
    void addPret() {
        ManagePretMaterielController.setManagedPretMateriel(null);
        managePretMateriel();
    }

    private void managePretMateriel()
    {
        try
        {
            Parent root ;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/ka/commune/view/ManagePretMaterielView.fxml"));
            root = loader.load();
            App.rootStage.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    ChangeListener<String> tapingSearchPret= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTablePret(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchMateriel= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableMateriel(newValue.toLowerCase());
    };

    public void remplirTablePret(String critere)
    {
        ObservableList<PretMateriel> temp = FXCollections.observableArrayList();
        temp.clear();
        tablePret.getColumns().clear();
        tablePret.getItems().clear();
        columnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnActivite.setCellValueFactory(new PropertyValueFactory<>("activite"));
        columnBeneficiare.setCellValueFactory(new PropertyValueFactory<>("beneficiare"));
        columnReprersantant.setCellValueFactory(new PropertyValueFactory<>("representant"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
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
        columnDateDemande.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateDemande()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateDemande()));
            else return null;
        });
        columnActionPret.setPrefWidth(125);
        columnActionPret.setCellValueFactory(new PropertyValueFactory<>("action"));
        try {
            for(PretMateriel p:gServicesSociaux.findAllPretMateriel())
            {
                if(p.getSearchText().contains(critere.toString())
                && (p.getYear()+"").contains(comboYear.getSelectionModel().getSelectedItem()))
                    temp.add(p);
            }
        }catch(Exception e){}
        int num=1;
        for(PretMateriel p:temp )
        {
            p.setNumero(num++);
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
                    gServicesSociaux.deletePretMateriel(p);
                    remplirTablePret(textSearchPret.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                //tableMembresConseil.getSelectionModel().select(mc);
                ManagePretMaterielController.setManagedPretMateriel(p);
                managePretMateriel();
            });
            Button download=new Button();
            download.setOnAction(e->{
                downloadCartePretMateriel(p);
            });
            FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
            iconDown.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(download, iconDown, "#78e08f");

            p.getAction().getChildren().clear();
            p.getAction().getChildren().addAll(delete,update,download);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
        }
        tablePret.setItems(temp);
        tablePret.getColumns().addAll(columnActionPret,columnNumero,columnYear,columnActivite,columnBeneficiare
        ,columnReprersantant,columnPhone,columnDateDemande,columnDateDebut,columnDateFin);
    }

    private void downloadCartePretMateriel(PretMateriel p) {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadCartePretMateriel(p,selectedDirectory.getAbsolutePath()))
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
    void addMateriel() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle("خطأ");
        if(textMateriel.getText().equals(""))
        {
            alert.setHeaderText("المرجو إدخال اسم المعدة");
            alert.showAndWait();
            return;
        }
        if(textQuantite.getText().equals(""))
        {
            alert.setHeaderText("المرجو إدخال الكمية");
            alert.showAndWait();
            return;
        }
        Materiel materiel=new Materiel();
        materiel.setDesignation(textMateriel.getText());
        materiel.setQuantite(Double.parseDouble(textQuantite.getText()));
        if(!gServicesSociaux.addMateriel(materiel))
        {
            alert.setHeaderText("هذه المعدة موجودة بالفعل");
            alert.showAndWait();
            return;
        }
        textQuantite.setText("");
        textMateriel.setText("");
        textSearchMateriel.setText("");
        remplirTableMateriel(textSearchMateriel.getText().toLowerCase());
    }

    private void remplirTableMateriel(String critere) {
        ObservableList<Materiel> temp = FXCollections.observableArrayList();
        temp.clear();
        tableMateriel.getColumns().clear();
        tableMateriel.getItems().clear();
        columnNumeroMateriel.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        columnActionMateriel.setCellValueFactory(new PropertyValueFactory<>("action"));
        try {
            for(Materiel m : gServicesSociaux.findAllMateriel())
                if( m.getSearchText().toLowerCase().contains(critere))
                    temp.add(m);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        int i=0;
        for(Materiel m :temp)
        {
            m.setNumero(++i);
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
                    if(gServicesSociaux.materielIsPreted(m))
                    {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        a.setTitle("خطأ");
                        a.setHeaderText("لا يمكن حذف هذه المعدة لأنها مستغلة!");
                        a.showAndWait();
                        return;
                    }
                    gServicesSociaux.deleteMateriel(m);
                    remplirTableMateriel(textSearchMateriel.getText().toLowerCase());
                }
            });
            m.getAction().getChildren().clear();
            m.getAction().getChildren().add(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableMateriel.setItems(temp);
        tableMateriel.getColumns().addAll(columnNumeroMateriel,columnDesignation,columnQuantite,columnActionMateriel);
    }

    private void manageMateriel() {
        tableMateriel.setEditable(true);
        columnDesignation.setEditable(true);
        columnQuantite.setEditable(true);
        columnDesignation.setCellFactory(TextFieldTableCell.forTableColumn());
        columnQuantite.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                // TODO Auto-generated method stub
                return String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                // TODO Auto-generated method stub
                return Double.parseDouble(string);
            }
        }));
        columnDesignation.setOnEditCommit(e -> {
            Materiel materiel = tableMateriel.getSelectionModel().getSelectedItem();
            if (e.getNewValue().toString().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("خطأ");
                alert.setHeaderText("يرجى إدخال اسم المعدة");
                materiel.setDesignation(e.getOldValue().toString());
                //alert.setContentText("Ooops, there was an error!");
                alert.showAndWait();
            } else {
                materiel.setDesignation(e.getNewValue().toString());

                if (!gServicesSociaux.updateMateriel(materiel)) {
                    materiel.setDesignation(e.getOldValue().toString());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    alert.setTitle("خطأ");
                    alert.setHeaderText("هذه المعدة موجودة بالفعل");
                    //alert.setContentText("Ooops, there was an error!");
                    alert.showAndWait();
                }
            }
            textSearchMateriel.setText("");
            remplirTableMateriel(textSearchMateriel.getText().toLowerCase());
        });
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    private void showErrorMessage()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle("خطأ");
        alert.setHeaderText("لا يمكن حذف هذه المعدة لأنها مستغلة!");
        alert.showAndWait();
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

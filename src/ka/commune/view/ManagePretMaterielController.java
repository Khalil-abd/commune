package ka.commune.view;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.entity.Materiel;
import ka.commune.entity.PretMateriel;
import ka.commune.entity.PretedMateriel;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class ManagePretMaterielController implements Initializable {

    @FXML Label labelDateDemande;
    @FXML Label labelDateDebut;
    @FXML Label labelDateFin;
    @FXML private ScrollPane managePane;
    @FXML private JFXTextField textActivite;
    @FXML private JFXTextField textNumero;
    @FXML private JFXTextField textYear;
    @FXML private JFXTextField textBeneficiare;
    @FXML private JFXTextField textRepresentant;
    @FXML private JFXTextField textPhone;
    @FXML private JFXDatePicker dateDemande;
    @FXML private JFXDatePicker dateDebut;
    @FXML private JFXDatePicker dateFin;
    @FXML private ComboBox<PretedMateriel> comboMateriel;
    @FXML private Label textDisponible;
    @FXML private Spinner<Integer> textQuantite;
    @FXML private Button buttonAddMateriel;
    @FXML private TableView<PretedMateriel> tableMateriels;
    @FXML private TilePane buttonsPane;
    @FXML private Button buttonClose;
    @FXML private Button buttonPretMateriel;
    @FXML private Button buttonUpdatePretMateriel;

    private boolean answered=true;

    private GestionServicesSociaux gServicesSociaux=new GestionServicesSociaux();
    private static PretMateriel managedPretMateriel=null;


    private TableColumn<PretedMateriel,String> columnMateriel=new TableColumn<>("المعدة");
    private TableColumn<PretedMateriel,Double> columnQuantite=new TableColumn<>("الكمية");
    private TableColumn<PretedMateriel, HBox> columnAction=new TableColumn<>();
    private ObservableList<PretedMateriel> temp = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelDateDebut.setVisible(false);
        labelDateDemande.setVisible(false);
        labelDateFin.setVisible(false);
        tableMateriels.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        if(managedPretMateriel==null) {
            buttonsPane.getChildren().remove(buttonUpdatePretMateriel);
            managedPretMateriel=new PretMateriel();
        }
        else {
            fillFields();
            buttonsPane.getChildren().remove(buttonPretMateriel);
        }
        dateHandler();
        materielHandler();
        comboMateriel.valueProperty().addListener((options, oldValue, newValue) ->{
            if(newValue!=null)
            {
                textDisponible.setText(String.valueOf(newValue.getQuantite()));
                SpinnerValueFactory<Integer> valueFactory = //
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, (int)newValue.getQuantite(), 1);
                textQuantite.setValueFactory(valueFactory);
            }
        });
        remplirTableMateriel("");
    }

    private void initializeComboBox()
    {
        comboMateriel.getItems().clear();
        managedPretMateriel.setDateDebut(Date.from(Instant.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()))));
        managedPretMateriel.setDateFin(Date.from(Instant.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()))));
        List<Materiel> tmp=gServicesSociaux.findAllMateriel();
        List<PretedMateriel> list=new Vector<>();
        for (Materiel m : tmp){
            PretedMateriel p=new PretedMateriel();
            p.setPretmateriel(managedPretMateriel);
            p.setMateriel(m);
            double quantite=gServicesSociaux.getQuantiteDisponible(p);
            for(PretedMateriel pm : temp)
            {
                if(pm.getMateriel().getIdMateriel()==m.getIdMateriel())
                    quantite-=pm.getQuantite();
            }
            if(quantite>0)
            {
                p.setQuantite(quantite);
                list.add(p);
            }
        }
        comboMateriel.getItems().addAll(list);
    }

    private void fillFields()
    {
        if(managedPretMateriel!=null)
        {
            labelDateDebut.setVisible(true);
            labelDateDemande.setVisible(true);
            labelDateFin.setVisible(true);
            textNumero.setText(String.valueOf(managedPretMateriel.getNumero()));
            textYear.setText(String.valueOf(managedPretMateriel.getYear()));
            dateDemande.setValue(managedPretMateriel.getDateDemande().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            textActivite.setText(managedPretMateriel.getActivite());
            textBeneficiare.setText(managedPretMateriel.getBeneficiare());
            textRepresentant.setText(managedPretMateriel.getRepresentant());
            textPhone.setText(managedPretMateriel.getPhone());
            dateDebut.setValue(managedPretMateriel.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dateFin.setValue(managedPretMateriel.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            remplirTableMateriel("");
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
                    if (tableMateriels.getItems().size() > 0) {
                        temp.clear();
                    }
                    if(dateDemande.getValue()!=null)
                    {
                        if(newValue.isBefore(dateDemande.getValue()))
                        {
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
            materielHandler();
        });
        dateFin.valueProperty().addListener((observable, oldValue, newValue) -> {
            // TODO Auto-generated method stub
            if(newValue!= null)
            {
                labelDateFin.setVisible(true);
                if (tableMateriels.getItems().size() > 0) {
                    temp.clear();
                }
                if(dateDemande.getValue()!=null)
                {
                    if(newValue.isBefore(dateDemande.getValue()))
                    {
                        dateFin.setValue(dateDemande.getValue());
                    }
                }
                if(dateDebut.getValue()!=null)
                {
                    if(newValue.isBefore(dateDebut.getValue()))
                    {
                        dateFin.setValue(dateDebut.getValue());
                    }
                }
            }
            materielHandler();
        });
    }

    private boolean getAnswer()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.setTitle("رسالة التأكد");
        alert.setHeaderText("تغيير تاريخ البداية يترتب عنه مسح لائحة المعدات أسفله");
        alert.setContentText("للاستمرار اضغط على تغيير!");
        ButtonType buttonYes = new ButtonType("تغيير");
        ButtonType buttonNo = new ButtonType("إلغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result= alert.showAndWait();
        if (result.isPresent() && (result.get() == buttonYes)) {
            return true;
        }
        else
            return false;
    }

    @FXML
    void close() {
        managedPretMateriel=null;
        try
        {
            Parent root ;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/ka/commune/view/GestionMaterielView.fxml"));
            root = loader.load();
            App.rootStage.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void remplirTableMateriel(String critere)
    {
        temp.clear();
        columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tableMateriels.getColumns().clear();
        columnMateriel.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if (param.getValue().getMateriel()!= null)
            {
                return new SimpleStringProperty(param.getValue().getMateriel().getDesignation());
            }
            else return null;
        });
        columnAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        if(managedPretMateriel.getMateriels()!=null)
        for(PretedMateriel p: managedPretMateriel.getPretedmateriels())
        {
            temp.add(p);
        }
        for(PretedMateriel p : temp)
        {
            setPretAction(p);
        }
        tableMateriels.setItems(temp);
        tableMateriels.getColumns().addAll(columnAction,columnMateriel,columnQuantite);
    }

    private void setPretAction(PretedMateriel p) {
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
                temp.remove(p);
                try {
                    initializeComboBox();
                }catch (Exception ex){};
            }
        });
        setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        p.getAction().getChildren().clear();
        p.getAction().getChildren().add(delete);
    }

    @FXML
    void pretMateriel() {
        fillPretMateriel();
        if(!managedPretMateriel.toString().contains("null") && tableMateriels.getItems().size()>0
                && !textNumero.getText().equals("") && !textYear.getText().equals(""))
        {

            gServicesSociaux.addPretMateriel(managedPretMateriel);
            close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("يرجى إدخال جميع المعلومات وتحديد المعدات المرغوب الاستفادة منها!");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    void updatePretMateriel() {
        fillPretMateriel();
        if(!managedPretMateriel.toString().contains("null") && tableMateriels.getItems().size()>0
            && !textNumero.getText().equals("") && !textYear.getText().equals(""))
        {
            gServicesSociaux.updatePretMateriel(managedPretMateriel);
            close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("خطأ");
            alert.setHeaderText("يرجى إدخال جميع المعلومات وتحديد المعدات المرغوب الاستفادة منها!");
            alert.showAndWait();
            return;
        }
    }

    private void fillPretMateriel()
    {
        try{
            managedPretMateriel.setDateDemande(Date.from(Instant.from(dateDemande.getValue().atStartOfDay(ZoneId.systemDefault()))));
            managedPretMateriel.setDateFin(Date.from(Instant.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()))));
            managedPretMateriel.setDateDebut(Date.from(Instant.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()))));
            managedPretMateriel.setBeneficiare(!textBeneficiare.getText().equals("")?textBeneficiare.getText():null);
            managedPretMateriel.setActivite(!textActivite.getText().equals("")?textActivite.getText():null);
            managedPretMateriel.setPhone(!textPhone.getText().equals("")?textPhone.getText():null);
            managedPretMateriel.setRepresentant(!textRepresentant.getText().equals("")?textRepresentant.getText():null);
            managedPretMateriel.setPretedmateriels(tableMateriels.getItems());
            managedPretMateriel.setPretedmateriels(tableMateriels.getItems());
            if(!textNumero.getText().equals(""))
                managedPretMateriel.setNumero(Integer.parseInt(textNumero.getText()));
            if(!textYear.getText().equals(""))
                managedPretMateriel.setYear(Integer.parseInt(textYear.getText()));
        }catch(NullPointerException e){}
    }

    @FXML
    void addMateriel() {
        if(comboMateriel.getSelectionModel().getSelectedItem()!=null && textQuantite.getValue()>0)
        {
            PretedMateriel p=new PretedMateriel();
            p.setMateriel(comboMateriel.getValue().getMateriel());
            p.setPretmateriel(managedPretMateriel);
            p.setQuantite((double)textQuantite.getValueFactory().getValue());
            for(PretedMateriel pm: tableMateriels.getItems())
            {
                if(pm.getMateriel().getIdMateriel()==p.getMateriel().getIdMateriel())
                {
                    pm.setQuantite(pm.getQuantite()+p.getQuantite());
                    temp.set(temp.indexOf(pm),pm);
                    try{
                        initializeComboBox();
                    }catch(Exception e){}
                    textQuantite.getValueFactory().setValue(0);
                    textQuantite.setValueFactory(null);
                    textDisponible.setText("المتبقية");
                    return;
                }
            }
            setPretAction(p);
            temp.add(p);
            initializeComboBox();
            textQuantite.getValueFactory().setValue(0);
            textQuantite.setValueFactory(null);
            textDisponible.setText("المتبقية");
            //remplirTableMateriel("");
        }
    }


    private void materielHandler()
    {
        if(dateDebut.getValue()!=null && dateFin.getValue()!=null)
        {
            textQuantite.setDisable(false);
            comboMateriel.setDisable(false);
            buttonAddMateriel.setDisable(false);
            initializeComboBox();
        }
        else{
            textQuantite.setDisable(true);
            comboMateriel.setDisable(true);
            buttonAddMateriel.setDisable(true );
        }
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    public static PretMateriel getManagedPretMateriel() {
        return managedPretMateriel;
    }

    public static void setManagedPretMateriel(PretMateriel managedPretMateriel) {
        ManagePretMaterielController.managedPretMateriel = managedPretMateriel;
    }
}
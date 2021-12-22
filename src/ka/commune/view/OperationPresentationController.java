package ka.commune.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import ka.commune.business.App;
import ka.commune.business.GestionComptabilite;
import ka.commune.entity.*;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OperationPresentationController implements Initializable {

    @FXML private JFXTextField textYear;
    @FXML private JFXTextField textDossier;
    @FXML private JFXTextField textCategorie;
    @FXML private JFXDatePicker dateRealisation;
    @FXML private JFXTextField textMontant;
    @FXML private JFXComboBox<Resultat>  textResultat;
    @FXML private JFXComboBox<ActiviteType>  textActiviteType;
    @FXML private JFXComboBox<Realisation> textRealisation;
    @FXML private TableView<Cansistance> tableCansistance;
    @FXML private JFXTextField textCansistance;
    @FXML private TableView<PartenaireOperation> tablePartenaire;
    @FXML private JFXTextField textPartenaire;

    private GestionComptabilite gComptabilite=new GestionComptabilite();

    private final TableColumn<Cansistance, String> columnCansistance= new TableColumn<>("Cansistance");
    private final TableColumn<Cansistance, HBox> columnAC= new TableColumn<>();
    private final TableColumn<Cansistance, Integer> columnNC= new TableColumn<>("N°");
    private final TableColumn<PartenaireOperation, String> columnPartenaire= new TableColumn<>("Partenaire");
    private final TableColumn<PartenaireOperation, HBox> columnAP= new TableColumn<>();
    private final TableColumn<PartenaireOperation, Integer> columnNP= new TableColumn<>("N°");

    private static Operation operation=null;

    public static Operation getOperation() {
        return operation;
    }

    public static void setOperation(Operation operation) {
        OperationPresentationController.operation = operation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(operation!=null)
        {
            initilizeComboBoxex();
            operation=gComptabilite.findOperationByID(operation.getIdComptabilite());
            fillFields();
            remplirTableCansistance();
            remplirTablePartenaire();
        }
    }

    private void initilizeComboBoxex()
    {
        textResultat.getItems().clear();
        textResultat.getItems().addAll(gComptabilite.findAllResultat());

        textRealisation.getItems().clear();
        textRealisation.getItems().addAll(gComptabilite.findAllRealisation());

        textActiviteType.getItems().clear();
        textActiviteType.getItems().addAll(gComptabilite.findAllActiviteType());
    }

    private void fillFields()
    {
        textDossier.setText(operation.getNumeroDossier()+"");
        textYear.setText(operation.getAnnee()+"");
        if(operation.getCategorieBean()!=null)
            textCategorie.setText(operation.getCategorieBean().getDesignation());
        if(operation.getDateRealisation()!=null)
            dateRealisation.setValue(operation.getDateRealisation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        textMontant.setText(operation.getMontantRecu()+"  DH");
        if(operation.getResultatBean()!=null)
        {
            for(Resultat r: textResultat.getItems())
            {
                if(operation.getResultatBean().getDesignation().equals(r.getDesignation())) {
                    textResultat.setValue(r);
                    break;
                }
            }
        }

        if(operation.getActivitetype()!=null) {
            for(ActiviteType a: textActiviteType.getItems())
            {
                if(operation.getActivitetype().getDesignation().equals(a.getDesignation())) {
                    textActiviteType.setValue(a);
                    break;
                }
            }
        }
        if(operation.getRealisationBean()!=null)
        {
            for(Realisation r: textRealisation.getItems())
            {
                if(operation.getRealisationBean().getDesignation().equals(r.getDesignation())) {
                    textRealisation.setValue(r);
                    break;
                }
            }

        }
    }

    private void remplirTableCansistance()
    {
        ObservableList<Cansistance> temp = FXCollections.observableArrayList();
        temp.clear();
        tableCansistance.getColumns().clear();
        tableCansistance.getItems().clear();
        columnNC.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnCansistance.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnAC.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(Cansistance c : operation.getCansistances())
            {
                c.setNumero(++i);

                Button delete=new Button("حذف");
                delete.setOnAction(e->{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Pour continuer la suppression, veuillez appuyer sur Supprimer");
                    ButtonType buttonYes = new ButtonType("Supprimer");
                    ButtonType buttonNo = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonYes,buttonNo);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonYes){
                        operation.removeCansistance(c);
                        remplirTableCansistance();
                    }
                });
                c.getAction().getChildren().clear();
                c.getAction().getChildren().addAll(delete);
                setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
                temp.add(c);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        tableCansistance.setItems(temp);
        tableCansistance.getColumns().addAll(columnAC,columnNC,columnCansistance);
    }

    private void remplirTablePartenaire()
    {
        ObservableList<PartenaireOperation> temp = FXCollections.observableArrayList();
        temp.clear();
        tablePartenaire.getColumns().clear();
        tablePartenaire.getItems().clear();
        columnNP.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnPartenaire.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnAP.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(PartenaireOperation c : operation.getPartenaireoperations())
            {
                c.setNumero(++i);

                Button delete=new Button("");
                delete.setOnAction(e->{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Pour continuer la suppression, veuillez appuyer sur Supprimer");
                    ButtonType buttonYes = new ButtonType("Supprimer");
                    ButtonType buttonNo = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonYes,buttonNo);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonYes){
                        operation.removePartenaireoperation(c);
                        remplirTablePartenaire();
                    }
                });
                c.getAction().getChildren().clear();
                c.getAction().getChildren().addAll(delete);
                setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
                temp.add(c);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        tablePartenaire.setItems(temp);
        tablePartenaire.getColumns().addAll(columnAP,columnNP,columnPartenaire);
    }

    @FXML
    public void addCansistance() {
        if(textCansistance.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer la désignation de la cansistance!");
            alert.showAndWait();
            return;
        }
        Cansistance cansistance=new Cansistance();
        cansistance.setDesignation(textCansistance.getText());
        cansistance.setOperationBean(operation);
        operation.addCansistance(cansistance);
        textCansistance.setText("");
        remplirTableCansistance();
    }

    @FXML
    public void addPartenaire() {
        if(textPartenaire.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer le nom du partenaire!");
            alert.showAndWait();
            return;
        }
        PartenaireOperation p=new PartenaireOperation();
        p.setDesignation(textPartenaire.getText());
        p.setOperationBean(operation);
        operation.addPartenaireoperation(p);
        textPartenaire.setText("");
        remplirTablePartenaire();
    }

    @FXML
    public void update() {
        if(dateRealisation.getValue()!=null)
            operation.setDateRealisation(Date.from(Instant.from(dateRealisation.getValue().atStartOfDay(ZoneId.systemDefault()))));
        if(textResultat.getValue()!=null)
            operation.setResultatBean(textResultat.getValue());
        if(textActiviteType!=null)
            operation.setActivitetype(textActiviteType.getValue());
        if(textRealisation!=null)
            operation.setRealisationBean(textRealisation.getValue());
        gComptabilite.updateOperation(operation);
        operation=gComptabilite.findOperationByID(operation.getIdComptabilite());
        gComptabilite.updateOperation(operation);
        close();
    }

    @FXML
    public void close() {
        try
        {
            Parent root ;
            root= FXMLLoader.load(getClass().getResource("/ka/commune/view/ComptabiliteMenuView.fxml"));
            operation=null;
            HomeController.temphomePane.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void setButtonIco(Button btn, String imgSRC, String Color) {
        javafx.scene.image.Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setPrefWidth(18);
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }
}

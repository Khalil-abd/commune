package ka.commune.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import ka.commune.business.App;
import ka.commune.business.GestionComptabilite;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.*;
import ka.commune.view.control.NumberField;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class ComptabiliteMenuController implements Initializable {


    @FXML private JFXComboBox<Categorie> txtCategorie;
    @FXML private JFXComboBox<String> textYear;
    @FXML private JFXTextField textDossier;
    @FXML private TabPane principalPane;
    @FXML private JFXTextField textRealisation;
    @FXML private TextField textSearchRealisation;
    @FXML private TableView<Realisation> tableRealisation;
    @FXML private JFXTextField textResultat;
    @FXML private TextField textSearchResultat;
    @FXML private TableView<Resultat> tableResultat;
    @FXML private JFXTextField textActivite;
    @FXML private TextField textSearchActivite;
    @FXML private TableView<ActiviteType> tableActivite;
    @FXML private ComboBox<Categorie> comboCategorie;
    @FXML private TextField textSearchCategorie;
    @FXML private JFXTextField textCategorie;
    @FXML private JFXTextField totalMontantReserve;
    @FXML private JFXTextField totalMontantRecu;
    @FXML private JFXTextField totalMontantVerse;
    @FXML private JFXTextField totalReliquat;
    @FXML private Button buttonUpdatePartenaire;
    @FXML private Button buttonClosePartenaire;
    @FXML private Button buttonAddPartenaire;
    @FXML private TilePane buttonsPanePartenaire;
    @FXML private JFXTextField textDesignationPartenaire;
    @FXML private VBox managePanePartenaire;
    @FXML private BorderPane rootStage;
    @FXML private VBox menu;
    @FXML private ScrollPane homePane;
    @FXML private TextField textSearchOperation;
    @FXML private BorderPane borderPane;
    @FXML private TableView<Operation> tableOperation;
    @FXML private ScrollPane managedPane;
    @FXML private Label labelDate;
    @FXML private JFXDatePicker date;
    @FXML private JFXTextField textDesignation;
    @FXML private NumberField textMontantReserve;
    @FXML private JFXTextField textFournisseur;
    @FXML private NumberField textMontantRecu;
    @FXML private NumberField textMontantVerse;
    @FXML private JFXTextField textRef;
    @FXML private TilePane buttonsPane;
    @FXML private Button buttonAddOperation;
    @FXML private Button buttonUpdateOperation;
    @FXML private Button buttonClose;
    @FXML private VBox sidePane;
    @FXML private ComboBox<String> comboYear;

    @FXML private TableView<Categorie> tableCategorie;
    private final TableColumn<Categorie, String> columnDesCategorie= new TableColumn<>("Catégorie");
    private final TableColumn<Categorie, Integer> columnNumCategorie= new TableColumn<>("N°");
    private final TableColumn<Categorie, HBox> columnActionCategorie= new TableColumn<>();

    private final TableColumn<Realisation, String> columnDesRealisation= new TableColumn<>("Réalisation");
    private final TableColumn<Realisation, Integer> columnNumRealisation= new TableColumn<>("N°");
    private final TableColumn<Realisation, HBox> columnActionRealisation= new TableColumn<>();

    private final TableColumn<Resultat, String> columnDesResultat= new TableColumn<>("Résultat");
    private final TableColumn<Resultat, Integer> columnNumResultat= new TableColumn<>("N°");
    private final TableColumn<Resultat, HBox> columnActionResultat= new TableColumn<>();

    private final TableColumn<ActiviteType, String> columnDesActivite= new TableColumn<>("Activité Type");
    private final TableColumn<ActiviteType, Integer> columnNumActivite= new TableColumn<>("N°");
    private final TableColumn<ActiviteType, HBox> columnActionActivite= new TableColumn<>();

    private final TableColumn<Operation, String> columnDate= new TableColumn<>("Date d'opération");
    private final TableColumn<Operation, String> columnCategorie= new TableColumn<>("Catégorie");
    private final TableColumn<Operation, String> columnDesignation= new TableColumn<>("Désignation");
    private final TableColumn<Operation, Double> columnMontantReserve= new TableColumn<>("Montant réservé");
    private final TableColumn<Operation, String> columnFournisseur= new TableColumn<>("Fournisseur");
    private final TableColumn<Operation, Double> columnMontantRecu= new TableColumn<>("Montant reçu par le partenaire");
    private final TableColumn<Operation, Double> columnMontantVerse= new TableColumn<>("Montant versé au fournisseur");
    private final TableColumn<Operation, String> columnReliquat= new TableColumn<>("Reliquat");
    private final TableColumn<Operation, String> columnRef= new TableColumn<>("Ref");
    private final TableColumn<Operation, HBox> columnAction= new TableColumn<>();
    private final TableColumn<Operation, Integer> columnNumeroDossier= new TableColumn<>("N° Dossier");

    private static Partenaire selectedPartenaire;
    private Operation managedOperation;
    private GestionComptabilite gComptabilite=new GestionComptabilite();
    private Vector<HBox> menuButtons=new Vector<HBox>();
    private Partenaire managedPartenaire;


    ObservableList<Partenaire> partenaires = FXCollections.observableArrayList();

    public static Partenaire getSelectedPartenaire() {
        return selectedPartenaire;
    }

    public static void setSelectedPartenaire(Partenaire selectedPartenaire) {
        ComptabiliteMenuController.selectedPartenaire = selectedPartenaire;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.rootStage=this.rootStage;
        initializeComboCategorie();
        initializeComboYear();
        closePartenaire();
        close();
        textSearchOperation.textProperty().addListener(tapingSearchOperation);
        textSearchCategorie.textProperty().addListener(tapingSearchCategorie);
        textSearchRealisation.textProperty().addListener(tapingSearchRealisation);
        textSearchResultat.textProperty().addListener(tapingSearchResultat);
        textSearchActivite.textProperty().addListener(tapingSearchActivite);
        comboCategorie.valueProperty().addListener(selectingCategorie);
        comboYear.valueProperty().addListener(selectingYear);
        remplirListPartenaire();
        if(menu.getChildren().size()>0)
            selectPartenaire(partenaires.get(0));
        remplirTableCategorie(textSearchCategorie.getText().toLowerCase());
        remplirTableRealisation(textSearchRealisation.getText().toLowerCase());
        remplirTableResultat(textSearchResultat.getText().toLowerCase());
        remplirTableActivite(textSearchActivite.getText().toLowerCase());
        manageTableCategorie();
        manageTableActivite();
        manageTableRealisation();
        manageTableResultat();
    }

    ChangeListener<String> tapingSearchOperation= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableOperation(newValue.toLowerCase());
    };
    ChangeListener<String> tapingSearchCategorie= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableCategorie(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchRealisation= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableRealisation(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchResultat= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableResultat(newValue.toLowerCase());
    };

    ChangeListener<String> tapingSearchActivite= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableActivite(newValue.toLowerCase());
    };

    ChangeListener<String> selectingYear= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableOperation(textSearchOperation.getText().toLowerCase());
    };
    ChangeListener<Categorie> selectingCategorie= (observable, oldValue, newValue) -> {
        // TODO Auto-generated method stub
        remplirTableOperation(textSearchOperation.getText().toLowerCase());
    };

    private void initializeComboYear()
    {
        comboYear.getItems().clear();
        comboYear.getItems().addAll(App.getListYearsFilter());
        textYear.getItems().clear();
        textYear.getItems().addAll(App.getListYears());
        comboYear.getSelectionModel().select(0);
        for(String year: comboYear.getItems())
        {
            if(year.equals(App.getCurrentYear()+"")) {

                textYear.getSelectionModel().select(year);
            }
        }
    }
    private void initializeComboCategorie()
    {
        try{
            comboCategorie.getItems().clear();
            txtCategorie.getItems().clear();
            Categorie c=new Categorie();
            c.setDesignation("Toutes les catégories");
            comboCategorie.getItems().add(c);
            comboCategorie.getItems().addAll(gComptabilite.findAllCategorie());
            txtCategorie.getItems().addAll(gComptabilite.findAllCategorie());
            if(txtCategorie.getItems().size()>0)
                txtCategorie.getSelectionModel().select(0);
            comboCategorie.getSelectionModel().select(0);
        }catch (Exception e){
            System.out.println("erreur");
        }
    }

    private void initializeToTaux()
    {
        totalMontantRecu.setText("0  DH");
        totalMontantReserve.setText("0  DH");
        totalMontantVerse.setText("0  DH");
        totalReliquat.setText("0  DH");
    }

    private void createButton(Partenaire partenaire)
    {
        partenaire.setButton(new HBox());
        partenaire.getButton().setAlignment(Pos.CENTER_LEFT);
        Text text=new Text(partenaire.getNom());
        text.setStrokeType(StrokeType.OUTSIDE);
        text.setStrokeWidth(0);
        text.getStyleClass().add("text");
        partenaire.getButton().setSpacing(10);
        partenaire.getButton().setPrefHeight(60);
        partenaire.getButton().setPrefWidth(225);
        MaterialDesignIconView icon=new MaterialDesignIconView(MaterialDesignIcon.DELETE);
        MaterialDesignIconView iconEdit=new MaterialDesignIconView(MaterialDesignIcon.PENCIL);
        icon.setOnMouseClicked(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Pour continuer la suppression, veuillez appuyer sur Supprimer");
            ButtonType buttonYes = new ButtonType("Supprimer");
            ButtonType buttonNo = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonYes,buttonNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes){
                gComptabilite.deletePartenaire(partenaire);
                remplirListPartenaire();
                if(partenaires.size()>0)
                {
                    if(selectedPartenaire.getIdPartenaire()==partenaire.getIdPartenaire())
                        selectPartenaire(partenaires.get(0));
                    else {
                        selectPartenaire(selectedPartenaire);
                    }
                }else
                    initializeToTaux();
            }
        });
        iconEdit.setOnMouseClicked(e->{
            closePartenaire();
            managedPartenaire=partenaire;
            showManagePanePartenaire();
        });
        setButtonFont2( icon);
        setButtonFont2( iconEdit);
        partenaire.getButton().getChildren().add(icon);
        partenaire.getButton().getChildren().add(iconEdit);
        partenaire.getButton().getChildren().add(text);
        partenaire.getButton().setPadding(new Insets(0,0,0,10));
        partenaire.getButton().getStyleClass().add("cursor_hand");
        partenaire.getButton().getStyleClass().add("notActive");
        partenaire.getButton().setOnMouseClicked(e->{
            selectPartenaire(partenaire);
        });
    }

    private void selectPartenaire(Partenaire partenaire)
    {
        selectedPartenaire=partenaire;
        for(HBox b : menuButtons)
            if(((Text)b.getChildren().get(0)).getText().equals(((Text)partenaire.getButton().getChildren().get(0)).getText()))
            {
                styleButton(partenaire);
                textSearchOperation.setText("");
                remplirTableOperation(textSearchOperation.getText().toLowerCase());
                break;
            }
    }

    private void remplirListPartenaire()
    {
        menu.getChildren().clear();
        partenaires.clear();

        for(Partenaire partenaire : gComptabilite.findAllPartenaire())
        {
            partenaires.add(partenaire);
            createButton(partenaire);
            menu.getChildren().add(partenaire.getButton());
            menuButtons.add(partenaire.getButton());
        }
    }

    private void styleButton(Partenaire btn) {
        for(Partenaire p : partenaires)
        {
            if(p.getIdPartenaire()==btn.getIdPartenaire())
            {
                p.getButton().getStyleClass().clear();
                p.getButton().getStyleClass().add("active");
            }
            else
            {
                p.getButton().getStyleClass().clear();
                p.getButton().getStyleClass().add("notActive");
            }
        }
    }

    private void showManagePane()
    {
        this.buttonsPane.getChildren().clear();
        if(this.managedOperation==null)
        {
            this.buttonsPane.getChildren().add(this.buttonAddOperation);
            this.borderPane.setRight(this.managedPane);
        }
        else
        {
            this.borderPane.setRight(this.managedPane);
            fillFields();
            this.buttonsPane.getChildren().add(this.buttonUpdateOperation);
        }
        this.buttonsPane.getChildren().add(buttonClose);
    }

    private void fillFields() {
        textDesignation.setText(managedOperation.getDesignation());
        textFournisseur.setText(managedOperation.getFournisseur());
        textMontantReserve.setText(""+managedOperation.getMontant());
        textMontantRecu.setText(""+managedOperation.getMontantRecu());
        textMontantVerse.setText(""+managedOperation.getMontantVerse());
        textRef.setText(managedOperation.getRef());
        textDossier.setText(managedOperation.getNumeroDossier()+"");
        for(Categorie c:txtCategorie.getItems())
        {
            if(managedOperation.getCategorieBean().getDesignation().equals(c.getDesignation()))
                txtCategorie.getSelectionModel().select(c);
        }
        date.setValue(managedOperation.getDateOperation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private void remplirTableCategorie(String critere)
    {
        ObservableList<Categorie> temp = FXCollections.observableArrayList();
        temp.clear();
        tableCategorie.getColumns().clear();
        tableCategorie.getItems().clear();
        columnNumCategorie.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesCategorie.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnActionCategorie.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(Categorie c : gComptabilite.findAllCategorie())
            {
                if( c.getDesignation().toLowerCase().contains(critere.toLowerCase()))
                {
                    c.setNumero(++i);
                    temp.add(c);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(Categorie o :temp)
        {
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
                    gComptabilite.deleteCategorie(o);
                    remplirTableCategorie(textSearchCategorie.getText().toLowerCase());
                }
            });
            o.getAction().getChildren().clear();
            o.getAction().getChildren().addAll(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableCategorie.setItems(temp);
        tableCategorie.getColumns().addAll(columnActionCategorie,columnNumCategorie,columnDesCategorie);
    }

    private void remplirTableRealisation(String critere)
    {
        ObservableList<Realisation> temp = FXCollections.observableArrayList();
        temp.clear();
        tableRealisation.getColumns().clear();
        tableRealisation.getItems().clear();
        columnNumRealisation.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesRealisation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnActionRealisation.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(Realisation c : gComptabilite.findAllRealisation())
            {
                if( c.getDesignation().toLowerCase().contains(critere.toLowerCase()))
                {
                    c.setNumero(++i);
                    temp.add(c);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(Realisation o :temp)
        {
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
                    gComptabilite.deleteRealisation(o);
                    remplirTableRealisation(textSearchRealisation.getText().toLowerCase());
                }
            });
            o.getAction().getChildren().clear();
            o.getAction().getChildren().addAll(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableRealisation.setItems(temp);
        tableRealisation.getColumns().addAll(columnActionRealisation,columnNumRealisation,columnDesRealisation);
    }

    private void remplirTableResultat(String critere)
    {
        ObservableList<Resultat> temp = FXCollections.observableArrayList();
        temp.clear();
        tableResultat.getColumns().clear();
        tableResultat.getItems().clear();
        columnNumResultat.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesResultat.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnActionResultat.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(Resultat c : gComptabilite.findAllResultat())
            {
                if( c.getDesignation().toLowerCase().contains(critere.toLowerCase()))
                {
                    c.setNumero(++i);
                    temp.add(c);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(Resultat o :temp)
        {
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
                    gComptabilite.deleteResultat(o);
                    remplirTableResultat(textSearchResultat.getText().toLowerCase());
                }
            });
            o.getAction().getChildren().clear();
            o.getAction().getChildren().addAll(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableResultat.setItems(temp);
        tableResultat.getColumns().addAll(columnActionResultat,columnNumResultat,columnDesResultat);
    }

    private void remplirTableActivite(String critere)
    {
        ObservableList<ActiviteType> temp = FXCollections.observableArrayList();
        temp.clear();
        tableActivite.getColumns().clear();
        tableActivite.getItems().clear();
        columnNumActivite.setCellValueFactory(new PropertyValueFactory<>("numero"));
        columnDesActivite.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnActionActivite.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            int i=0;
            for(ActiviteType c : gComptabilite.findAllActiviteType())
            {
                if( c.getDesignation().toLowerCase().contains(critere.toLowerCase()))
                {
                    c.setNumero(++i);
                    temp.add(c);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(ActiviteType o :temp)
        {
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
                    gComptabilite.deleteActiviteType(o);
                    remplirTableCategorie(textSearchCategorie.getText().toLowerCase());
                }
            });
            o.getAction().getChildren().clear();
            o.getAction().getChildren().addAll(delete);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
        }
        tableActivite.setItems(temp);
        tableActivite.getColumns().addAll(columnActionActivite,columnNumActivite,columnDesActivite);
    }

    private String getYear(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR)+"";
    }

    private void remplirTableOperation(String critere)
    {
        ObservableList<Operation> temp = FXCollections.observableArrayList();
        temp.clear();
        tableOperation.getColumns().clear();
        tableOperation.getItems().clear();
        columnNumeroDossier.setCellValueFactory(new PropertyValueFactory<>("numeroDossier"));
        columnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnMontantReserve.setCellValueFactory(new PropertyValueFactory<>("montant"));
        columnMontantRecu.setCellValueFactory(new PropertyValueFactory<>("montantRecu"));
        columnFournisseur.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        columnMontantVerse.setCellValueFactory(new PropertyValueFactory<>("montantVerse"));
        columnRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
        columnCategorie.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            if(param.getValue().getCategorieBean()!=null)
                return new SimpleStringProperty(param.getValue().getCategorieBean().getDesignation());
            return null;
        });
        columnDate.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            if (param.getValue().getDateOperation()!= null)
                return new SimpleStringProperty(format1.format(param.getValue().getDateOperation()));
            else return null;
        });
        columnReliquat.setCellValueFactory(param -> {
            // TODO Auto-generated method stub
            return new SimpleStringProperty(""+(param.getValue().getMontantRecu()-param.getValue().getMontantVerse()));
        });
        columnAction.setPrefWidth(156);
        columnAction.setCellValueFactory(new PropertyValueFactory("action"));
        try {
            for(Operation o : gComptabilite.findPartenaireOperations(selectedPartenaire))
            {
                if(comboYear.getSelectionModel().getSelectedIndex()==0 ||
                        comboYear.getSelectionModel().getSelectedItem().contains(getYear(o.getDateOperation())))
                {
                    if(comboCategorie.getSelectionModel().getSelectedIndex()==0 ||
                            comboCategorie.getSelectionModel().getSelectedItem().getDesignation().equals(o.getCategorieBean().getDesignation()))
                    {
                        if( o.getSearchText().toLowerCase().contains(critere.toLowerCase()))
                        {
                            temp.add(o);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        double reliquat,reserve,recu,verse;
        reliquat=reserve=recu=verse=0;
        for(Operation o :temp)
        {
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
                    gComptabilite.deleteOperation(o);
                    remplirTableOperation(textSearchOperation.getText().toLowerCase());
                }
            });
            Button update=new Button("تعديل");
            update.setOnAction(e->{
                close();
                managedOperation=o;
                showManagePane();
            });
            Button download=new Button();
            download.setOnAction(e->{
                downloadPresentationActivity(o);
            });
            Button consult=new Button();
            consult.setOnAction(e->{
                OperationPresentationController.setOperation(o);
                consultOperation();
            });
            o.getAction().getChildren().clear();
            o.getAction().getChildren().addAll(delete,update,consult,download);
            setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
            setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
            setButtonIco(consult,"/ka/commune/view/resources/img/watch.png","black");
            FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
            iconDown.setFill(Paint.valueOf("#FFFFFF"));
            setButtonFont(download, iconDown, "#78e08f");
            reserve+=o.getMontant();
            recu+=o.getMontantRecu();
            verse+=o.getMontantVerse();
        }
        reliquat=recu-verse;
        totalMontantRecu.setText(String.format("%,.2f", recu)+"  DH");
        totalMontantReserve.setText(String.format("%,.2f", reserve)+"  DH");
        totalMontantVerse.setText(String.format("%,.2f", verse)+"  DH");
        totalReliquat.setText(String.format("%,.2f", reliquat)+"  DH");
        tableOperation.setItems(temp);
        tableOperation.getColumns().addAll(columnAction,columnNumeroDossier,columnCategorie,columnDate,columnDesignation,columnMontantReserve,columnFournisseur,columnMontantRecu
                ,columnMontantVerse,columnReliquat,columnRef);
    }

    private void downloadPresentationActivity(Operation o) {
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadActivityPresentation(o,selectedDirectory.getAbsolutePath()))
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

    private void consultOperation()
    {
        try
        {
            Parent root ;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/ka/commune/view/OperationPresentationView.fxml"));
            root = loader.load();
            HomeController.temphomePane.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void addNewOperation() {
        if(selectedPartenaire!=null)
        {
            close();
            managedOperation=null;
            showManagePane();
        }
    }

    @FXML
    void close() {
        textDossier.setText("");
        textDesignation.setText("");
        textFournisseur.setText("");
        textMontantReserve.setText("");
        textMontantRecu.setText("");
        textMontantVerse.setText("");
        textRef.setText("");
        date.setValue(null);
        borderPane.setRight(null);
    }

    private boolean isFilled()
    {
        if(textMontantVerse.getText().equals("") || textMontantRecu.getText().equals("")
            || textMontantReserve.getText().equals("") || textFournisseur.getText().equals("")
            || textDesignation.getText().equals("") || textRef.getText().equals("") || date.getValue()==null
        || txtCategorie.getSelectionModel().getSelectedItem()==null|| textDossier.getText().equals(""))
            return false;
        return true;
    }

    private void initializeOperation(Operation o)
    {
        o.setPartenaireBean(selectedPartenaire);
        o.setDateOperation(Date.from(Instant.from(date.getValue().atStartOfDay(ZoneId.systemDefault()))));
        o.setDesignation(textDesignation.getText());
        o.setFournisseur(textFournisseur.getText());
        o.setRef(textRef.getText());
        o.setMontant(Double.valueOf(textMontantReserve.getText()));
        o.setMontantVerse(Double.valueOf(textMontantVerse.getText()));
        o.setMontantRecu(Double.valueOf(textMontantRecu.getText()));
        o.setCategorieBean(txtCategorie.getSelectionModel().getSelectedItem());
        o.setAnnee(Integer.parseInt(textYear.getSelectionModel().getSelectedItem()));
        o.setNumeroDossier(Integer.parseInt(textDossier.getText()));
    }

    @FXML
    void addOperation() {
        if(isFilled())
        {
            Operation o=new Operation();
            initializeOperation(o);
            if(gComptabilite.addOperation(o))
            {
                close();
                textSearchOperation.setText("");
                remplirTableOperation(textSearchOperation.getText().toLowerCase());
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atention");
            alert.setHeaderText("Veuillez entrer toutes les informations!");
            alert.showAndWait();
        }
    }

    @FXML
    void downloadListOperations() {

    }

    @FXML
    void updateOperation( ) {
        if(isFilled())
        {
            initializeOperation(managedOperation);
            if(gComptabilite.updateOperation(managedOperation))
            {
                close();
                textSearchOperation.setText("");
                remplirTableOperation(textSearchOperation.getText().toLowerCase());
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atention");
            alert.setHeaderText("Veuillez entrer toutes les informations!");
            alert.showAndWait();
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

    private void showManagePanePartenaire()
    {
        this.buttonsPanePartenaire.getChildren().clear();

        if(this.managedPartenaire==null)
        {
            this.buttonsPanePartenaire.getChildren().add(this.buttonAddPartenaire);
        }
        else
        {
            fillPartenaireFields();
            this.buttonsPanePartenaire.getChildren().add(this.buttonUpdatePartenaire);
        }
        this.buttonsPanePartenaire.getChildren().add(buttonClosePartenaire);
        this.sidePane.getChildren().add(2,this.managePanePartenaire);
    }

    private void fillPartenaireFields() {
        textDesignationPartenaire.setText(managedPartenaire.getNom());
    }

    @FXML
    public void addNewPartenaire() {
        managedPartenaire=null;
        showManagePanePartenaire();
    }

    @FXML
    public void addPartenaire() {
        if(!textDesignationPartenaire.getText().equals(""))
        {
            managedPartenaire=new Partenaire();
            managedPartenaire.setNom(textDesignationPartenaire.getText());
            if(gComptabilite.addPartenaire(managedPartenaire))
            {
                closePartenaire();
                remplirListPartenaire();
                if(selectedPartenaire!=null)
                    selectPartenaire(selectedPartenaire);
                else
                    selectPartenaire(partenaires.get(0));
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrez le nom du partenaire");
            alert.showAndWait();
        }
    }

    @FXML
    public void updatePartenaire() {
        if(!textDesignationPartenaire.getText().equals(""))
        {
            managedPartenaire.setNom(textDesignationPartenaire.getText());
            if(gComptabilite.updatePartenaire(managedPartenaire))
            {
                closePartenaire();
                remplirListPartenaire();
                styleButton(selectedPartenaire);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrez le nom du partenaire");
            alert.showAndWait();
        }
    }

    @FXML
    public void closePartenaire() {
        managedPartenaire=null;
        textDesignationPartenaire.setText("");
        this.sidePane.getChildren().remove(this.managePanePartenaire);
    }

    private void setButtonFont2(GlyphIcon f) {
        f.setFill(javafx.scene.paint.Color.WHITE);
        f.setSize("18");
    }

    public void addCategorie() {
        if(textCategorie.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer la désignation de la catégorie!");
            alert.showAndWait();
            return;
        }
        Categorie categorie=new Categorie();
        categorie.setDesignation(textCategorie.getText());
        try {
            if(gComptabilite.addCategorie(categorie))
            {
                textCategorie.setText("");
                textSearchCategorie.setText("");
                remplirTableCategorie(textSearchCategorie.getText().toLowerCase());
                initializeComboCategorie();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("Erreur");
                alert.setHeaderText("Cette catégorie existe déjà!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    public void addRealisation() {
        if(textRealisation.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer la désignation de la Realisation!");
            alert.showAndWait();
            return;
        }
        Realisation categorie=new Realisation();
        categorie.setDesignation(textRealisation.getText());
        try {
            if(gComptabilite.addRealisation(categorie))
            {
                textRealisation.setText("");
                textSearchRealisation.setText("");
                remplirTableRealisation(textSearchRealisation.getText().toLowerCase());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("Erreur");
                alert.setHeaderText("Cette réalisation existe déjà!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    public void addResultat() {
        if(textResultat.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer la désignation du Resultat!");
            alert.showAndWait();
            return;
        }
        Resultat categorie=new Resultat();
        categorie.setDesignation(textResultat.getText());
        try {
            if(gComptabilite.addResultat(categorie))
            {
                textResultat.setText("");
                textSearchResultat.setText("");
                remplirTableResultat(textSearchResultat.getText().toLowerCase());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("Erreur");
                alert.setHeaderText("Ce resultat existe déjà!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    public void addActivite() {
        if(textActivite.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez entrer la désignation de l'activité type!");
            alert.showAndWait();
            return;
        }
        ActiviteType categorie=new ActiviteType();
        categorie.setDesignation(textActivite.getText());
        try {
            if(gComptabilite.addActiviteType(categorie))
            {
                textActivite.setText("");
                textSearchActivite.setText("");
                remplirTableActivite(textSearchActivite.getText().toLowerCase());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("Erreur");
                alert.setHeaderText("Cette activité type existe déjà!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void manageTableCategorie()
    {
        columnDesCategorie.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDesCategorie.setOnEditCommit(edittedCell -> {
            // TODO Auto-generated method stub
            Categorie categorie=tableCategorie.getSelectionModel().getSelectedItem();
            if(edittedCell.getNewValue().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Veuiller entrer la désignation de la catégorie");
                categorie.setDesignation(edittedCell.getOldValue());
                alert.showAndWait();
            }
            else
            {
                categorie.setDesignation(edittedCell.getNewValue());

                if(!gComptabilite.updateCategorie(categorie))
                {
                    categorie.setDesignation(edittedCell.getOldValue());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Cette catégorie existe déjà!");
                    alert.showAndWait();
                }
            }
            remplirTableCategorie(textSearchCategorie.getText());
            initializeComboCategorie();
        });
    }

    private void manageTableRealisation()
    {
        columnDesRealisation.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDesRealisation.setOnEditCommit(edittedCell -> {
            // TODO Auto-generated method stub
            Realisation categorie=tableRealisation.getSelectionModel().getSelectedItem();
            if(edittedCell.getNewValue().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Veuiller entrer la désignation de la réalisation");
                categorie.setDesignation(edittedCell.getOldValue());
                alert.showAndWait();
            }
            else
            {
                categorie.setDesignation(edittedCell.getNewValue());

                if(!gComptabilite.updateRealisation(categorie))
                {
                    categorie.setDesignation(edittedCell.getOldValue());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Cette réalisation existe déjà!");
                    alert.showAndWait();
                }
            }
            remplirTableRealisation(textSearchRealisation.getText());
        });
    }

    private void manageTableResultat()
    {
        columnDesResultat.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDesResultat.setOnEditCommit(edittedCell -> {
            // TODO Auto-generated method stub
            Resultat categorie=tableResultat.getSelectionModel().getSelectedItem();
            if(edittedCell.getNewValue().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Veuiller entrer la désignation du résultat");
                categorie.setDesignation(edittedCell.getOldValue());
                alert.showAndWait();
            }
            else
            {
                categorie.setDesignation(edittedCell.getNewValue());

                if(!gComptabilite.updateResultat(categorie))
                {
                    categorie.setDesignation(edittedCell.getOldValue());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Ce résultat existe déjà!");
                    alert.showAndWait();
                }
            }
            remplirTableResultat(textSearchResultat.getText());
        });
    }

    private void manageTableActivite()
    {
        columnDesActivite.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDesActivite.setOnEditCommit(edittedCell -> {
            // TODO Auto-generated method stub
            ActiviteType categorie=tableActivite.getSelectionModel().getSelectedItem();
            if(edittedCell.getNewValue().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Veuiller entrer la désignation de l'activité type");
                categorie.setDesignation(edittedCell.getOldValue());
                alert.showAndWait();
            }
            else
            {
                categorie.setDesignation(edittedCell.getNewValue());

                if(!gComptabilite.updateActiviteType(categorie))
                {
                    categorie.setDesignation(edittedCell.getOldValue());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Cette activité type existe déjà!");
                    alert.showAndWait();
                }
            }
            remplirTableActivite(textSearchActivite.getText());
        });
    }

    private void setButtonFont(Button btn, GlyphIcon f, String Color) {
        f.setSize("16");
        btn.setText("");
        btn.setGraphic(f);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

    public void downloadEtatActivites() {
        if(tableOperation.getItems().size()==0)
        {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Erreur");
            dialog.setHeaderText("La liste des opérations est vide!");
            dialog.showAndWait();
            return;
        }
        PdfGenerator tp=new PdfGenerator();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        try {
            if(tp.downloadEtatActivites(tableOperation.getItems(),selectedDirectory.getAbsolutePath(),comboCategorie.getValue().getDesignation()))
            {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("Succées");
                dialog.setHeaderText("L'opération est terminée avec succées");
                dialog.showAndWait();
            }
            else
            {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Erreur");
                dialog.showAndWait();
            }
        }
        catch(NullPointerException e) {
            return;
        }
    }

}

package it.polito.tdp.baseball;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


import it.polito.tdp.baseball.model.Model;
import it.polito.tdp.baseball.model.People;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConnesse;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDreamTeam;

    @FXML
    private Button btnGradoMassimo;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtYear;

    
    
    @FXML
    void doCalcolaConnesse(ActionEvent event) {
    	
    	txtResult.appendText("\n");
    	int connesse = this.model.componentiConnesse();
    	txtResult.appendText("Ci sono " + connesse + " componenti connesse");
    	
    }

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	int anno;
    	int salario;
    	try {
    		anno = Integer.parseInt(txtYear.getText());
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un anno");
    		return;
    	}
    	
    	if(Integer.parseInt(txtYear.getText())> 2019 || Integer.parseInt(txtYear.getText())< 1871) {
    		txtResult.setText("Inserisci un anno valido");
    		return;
    	}
    	
    	try {
    		salario = Integer.parseInt(txtSalary.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un salario valido");
    		return;
    	}
    	
    	this.model.creaGrafo(anno,salario);
    	
    	txtResult.appendText("GRAFO CREATO!\n");
    	txtResult.appendText("# VERTICI: " + model.getNVertici() + "\n");
    	txtResult.appendText("# ARCHI: " + model.getNArchi() + "\n");
    	
    	btnGradoMassimo.setDisable(false);
    	btnConnesse.setDisable(false);
    	btnDreamTeam.setDisable(false);
    	
    	
    }

    
    @FXML
    void doDreamTeam(ActionEvent event) {

    }

    
    @FXML
    void doGradoMassimo(ActionEvent event) {
    	
    	String risultato = model.gradoMax();
    	txtResult.appendText("\n");
    	txtResult.appendText(risultato);

    }

    
    @FXML
    void initialize() {
        assert btnConnesse != null : "fx:id=\"btnConnesse\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGradoMassimo != null : "fx:id=\"btnGradoMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYear != null : "fx:id=\"txtYear\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}

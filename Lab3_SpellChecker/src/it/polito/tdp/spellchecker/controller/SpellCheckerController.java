package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class SpellCheckerController {

// 		MODEL DICTIONARY
	private Dictionary dictionary = new Dictionary ();	
	
// SELEZIONARE IL TIPO DI RICERCA CON IL FLAG
	private final boolean dichotomicSearch = false;
	private final boolean linearSearch = false;

	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxLingua"
    private ComboBox<String> boxLingua; // Value injected by FXMLLoader

    @FXML // fx:id="vBoxCenter"
    private VBox vBoxCenter; // Value injected by FXMLLoader

    @FXML // fx:id="txtArea1"
    private TextArea txtArea1; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtArea2"
    private TextArea txtArea2; // Value injected by FXMLLoader

    @FXML // fx:id="vBoxBottom"
    private VBox vBoxBottom; // Value injected by FXMLLoader

    @FXML // fx:id="lblErrori"
    private Label lblErrori; // Value injected by FXMLLoader

    @FXML // fx:id="btnClearText"
    private Button btnClearText; // Value injected by FXMLLoader

    @FXML // fx:id="lblSeconds"
    private Label lblSeconds; // Value injected by FXMLLoader

    @FXML
    void doActivation(ActionEvent event) {
    	
    	if(this.boxLingua.getValue() != null) {
    	
    		this.vBoxCenter.setDisable(false);
    		this.vBoxBottom.setDisable(false);
    		
    		this.txtArea1.clear();
    		this.txtArea2.clear();
    		
        	this.lblErrori.setText("");
        	this.lblSeconds.setText("");
        	
    	}
    	else {
    		this.vBoxBottom.setDisable(true);
    		this.vBoxCenter.setDisable(true);
    		this.txtArea1.setText("Seleziona una lingua!");
    	}
    }

    @FXML
    void doClearText(ActionEvent event) {
    	
    	this.txtArea1.clear();
    	this.txtArea2.clear();
    	
    	this.lblErrori.setText("");
    	this.lblSeconds.setText("");
    	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {

    	this.txtArea2.clear();
    	
    	this.lblErrori.setText("");
    	this.lblSeconds.setText("");
    	    	
    	String language = this.boxLingua.getValue();
    	if (language == null) {
    		this.txtArea1.setText("Seleziona una lingua!");
    		return ;
    	}
    	
    	if (!this.dictionary.loadDictionary(language)) {
    		this.txtArea1.setText("Errore nel caricamento del dizionario!");
    		return;
    	}

    	String text = this.txtArea1.getText();
    	if (text.isEmpty()) {
    		this.txtArea1.setText("Inserire un testo da correggere!");
    		return;
    	}
    	
    	
// PER CALCOLARE IL TEMPO IMPIEGATO PER IL CONTROLLO ORTOGRAFICO    
    	List <String> inputText = this.dictionary.inputText(text);
    	long l1 = System.nanoTime();
    	List <RichWord> lista ;
    	
// IN BASE ALLA TIPOLOGIA DI RICERCA SELEZIONATA CON I FLAG, SI EFFETTUA UNA RICERCA SPECIFICA    	
    	if (this.dichotomicSearch)
    		lista = this.dictionary.spellCheckTextDichotomic(inputText);
    	else if (this.linearSearch)
    		lista = this.dictionary.spellCheckTextLinear(inputText);
    	else
    		lista = this.dictionary.spellCheckText(inputText);
    	
    	long l2 = System.nanoTime();

    	
    	this.txtArea2.setText(dictionary.wrongWords(lista));
    	this.lblErrori.setText("The text contains " + dictionary.errors(lista) + " errors");
    	this.lblSeconds.setText("Spell check completed in " + (l2 - l1) / 1E9 + " seconds");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert vBoxCenter != null : "fx:id=\"vBoxCenter\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtArea1 != null : "fx:id=\"txtArea1\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtArea2 != null : "fx:id=\"txtArea2\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert vBoxBottom != null : "fx:id=\"vBoxBottom\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblSeconds != null : "fx:id=\"lblSeconds\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        
        
    }

	public void setModel(Dictionary model) {
		this.dictionary = model;

		this.txtArea1.setText("Selezionare una lingua!");
		
		this.boxLingua.getItems().addAll("English", "Italian");
	}
    
    
}

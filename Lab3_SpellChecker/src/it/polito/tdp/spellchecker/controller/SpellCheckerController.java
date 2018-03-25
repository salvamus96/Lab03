package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.LinkedList;
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

	private Dictionary dictionary = new Dictionary ();

	private List <String> inputText = new LinkedList <String> ();
	
	
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
    		
    		this.inputText.clear();
        	this.lblErrori.setText("");
        	this.lblSeconds.setText("");
        	
    	}
    }

    @FXML
    void doClearText(ActionEvent event) {
    	
    	this.txtArea1.clear();
    	this.txtArea2.clear();
    	
    	this.inputText.clear();
    	this.lblErrori.setText("");
    	this.lblSeconds.setText("");
    	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {

    	this.txtArea2.clear();
    	this.inputText.clear();
    	this.lblErrori.setText("");
    	this.lblSeconds.setText("");
    	
    	
    	String language = this.boxLingua.getValue();
    	this.dictionary.loadDictionary(language);

    	String text = this.txtArea1.getText();
    	if (text.isEmpty())
    		return ;
    	
// PER ELIMINARE I SEGNI DI PUNTEGGIATURA    	
    	String array [] = text.split(" ");
    	for (String s : array) {
    		s = s.replaceAll("[ \\p{Punct}]", "").trim().toLowerCase();
    		this.inputText.add(s);
    	}
    	
// PER CALCOLARE IL TEMPO IMPIEGATO PER IL CONTROLLO ORTOGRAFICO    	
    	long l1 = System.nanoTime();
    	List <RichWord> lista = dictionary.spellCheckText(inputText);
    	long l2 = System.nanoTime();
    	
    	int errori = 0;
    	String rich = "";
    	for (RichWord r : lista)
    		if (!r.isCorrect()) {
    			errori ++;
    			rich += r.getWord() + "\n";
    		}
    			
    	this.txtArea2.setText(rich);
    	this.lblErrori.setText("The text contains " + errori + "errors");
    	this.lblSeconds.setText("Spell check completed in " + (l2 - l1) / 1E9 + "seconds");
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

        this.boxLingua.getItems().addAll("English", "Italian");
        
    }
    
    
}

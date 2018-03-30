package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {

	private List <String> dictionary;
	private String language;
	
	public Dictionary () {

	}
	

	public List<String> getDictionary() {
		return dictionary;
	}

	public boolean loadDictionary(String language) {

		if (this.dictionary != null && this.language.equals(language))
			return true;
		
		// INIZIALIZZO LE VARIABILI DICHIARATE SOPRA
		this.dictionary = new ArrayList <String> ();
		this.language = language;
		
			try {
				FileReader fr = new FileReader("rsc/" + language + ".txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				
				while ((word = br.readLine()) != null) 
					dictionary.add(word.toLowerCase());
			 
				Collections.sort(this.dictionary);
				br.close();

				System.out.println("Dictionary " + language + " loaded. " + 
									"Found " + this.dictionary.size() + " words.");
				
				return true;
				
			 } catch (IOException e){
				 System.out.println("Errore nella lettura del file");
				 return false;
			 }
	}

	// RICERCA CON CONTAINS
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List <RichWord> elencoParole = new ArrayList<RichWord> (); 
		RichWord r;
		for (String s : inputTextList) {
				r = new RichWord (s, false);
				
				if (this.dictionary.contains(s.toLowerCase()))
					r.setCorrect(true);
			
				elencoParole.add(r);
		}		
		return elencoParole;
	}

	
	// RICERCA LINEARE
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List <RichWord> elencoParole = new ArrayList<RichWord> (); 
		RichWord r;
		for (String s : inputTextList) {
			
			r = new RichWord (s, false);
			boolean found = false;
	
			for (String word : this.dictionary)
				if (word.toLowerCase().equals(word)) {
					found = true;
					break;
				}
			
			if (found)
				r.setCorrect(true);
			
			elencoParole.add(r);
		}
		return elencoParole;
	}

	
	// RICERCA DICOTOMICA
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		List <RichWord> elencoParole = new ArrayList<RichWord> (); 
		RichWord r;
		for (String s : inputTextList) {
				r = new RichWord (s, false);
				
				if (this.searchDichotomic(s.toLowerCase()))
					r.setCorrect(true);
			
				elencoParole.add(r);
		}
		
		return elencoParole;
	}


	public boolean searchDichotomic(String s) {
		
		int inizio = 0;
		int fine = this.dictionary.size();
		
		while (inizio != fine) {
			
			int medio = inizio + (fine - inizio)/2;
			if (s.toLowerCase().compareTo(this.dictionary.get(medio)) == 0)
				return true;
			else if (s.toLowerCase().compareTo(this.dictionary.get(medio)) > 0)
				inizio = medio + 1;
			else
				fine = medio;
			
		}
		
		return false;
	}


	public List<String> inputText(String text) {
		// PER ELIMINARE I SEGNI DI PUNTEGGIATURA E I PUNTI DI RITORNO ACCAPO	

		List <String> input = new ArrayList <> ();

		text = text.replaceAll("[\\p{Punct}]", "");
    	String array [] = text.split(" ");
    	
    	// ANCHE GLI SPAZI EXTRA VENGONO CONSIDERATI COME ERRORI
    	for (String s : array)
    		input.add(s.trim().toLowerCase());
    
    	return input; 
	}


	public String wrongWords(List<RichWord> lista) {
    	String rich = "";
    	for (RichWord r : lista)
    		if (!r.isCorrect())
    			rich += r.getWord() + "\n";

		return rich;
	}

	public int errors(List<RichWord> lista) {
		int errori = 0;
    	for (RichWord r : lista)
    		if (!r.isCorrect())
    			errori ++;
   
		return errori;
	}
}
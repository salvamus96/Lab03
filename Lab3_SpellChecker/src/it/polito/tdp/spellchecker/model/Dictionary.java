package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {

	private List <String> dictionary;
	
	
	public Dictionary () {
		this.dictionary = new LinkedList <String> ();

	}
	

	public List<String> getDictionary() {
		return dictionary;
	}

	public void loadDictionary(String language) {

			try {
				FileReader fr = new FileReader("rsc/" + language + ".txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				
				while ((word = br.readLine()) != null) 
					dictionary.add(word.toLowerCase());
			 
				br.close();

			 } catch (IOException e){
				 System.out.println("Errore nella lettura del file");
			 }

	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List <RichWord> elencoParole = new LinkedList<RichWord> (); 
		RichWord r;
		for (String s : inputTextList) {
				r = new RichWord (s, false);
				
				if (this.searchLinear(s.toLowerCase()))
					r.setCorrect(true);
			
				elencoParole.add(r);
		}
		
		return elencoParole;
	}
	
	
	public boolean searchLinear(String s) {
		if(this.dictionary.contains(s))
//		for (String st : dictionary)
//			if (st.toLowerCase().compareTo(s) == 0)
				return true;
		return false;
	}


	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		List <RichWord> elencoParole = new LinkedList<RichWord> (); 
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
		// PER ELIMINARE I SEGNI DI PUNTEGGIATURA    	
    	List <String> input = new LinkedList <> ();
		String array [] = text.split(" ");
    	for (String s : array) {
    		s = s.replaceAll("[ \\p{Punct}]", "").trim().toLowerCase();
    		input.add(s);
    	}
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
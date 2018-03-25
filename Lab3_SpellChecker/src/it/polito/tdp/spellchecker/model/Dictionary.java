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
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List <RichWord> elencoParole = new LinkedList<RichWord> (); 
		RichWord r;
		for (String s : inputTextList) {
				r = new RichWord (s, false);
				
				if (dictionary.contains(s.toLowerCase()))
					r.setCorrect(true);
			
				elencoParole.add(r);
		}
		
		return elencoParole;
	}
	

}
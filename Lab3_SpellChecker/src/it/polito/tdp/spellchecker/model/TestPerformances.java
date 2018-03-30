package it.polito.tdp.spellchecker.model;

import java.util.ArrayList;
import java.util.List;

public class TestPerformances {
	public static void main(String[] args) {

		final int NUMBER_OF_TESTS = 10;

		Dictionary model = new Dictionary();
		model.loadDictionary("Italian");

		String text = "In informatica Java è un linguaggio di programmazione ad alto livello,\n"
				+ "orientato agli oggetti e a tipizzazione statica, specificatamente progettato per essere\n"
				+ "il più possibile indipendente dalla piattaforma di esecuzione.";

		System.out.println("Testo originario: \n" + text + "\n\n");
		
		List <String> input = new ArrayList <> ();

		text = text.replaceAll("\n", " ");
		text = text.replaceAll("[\\p{Punct}]", "");
    	
		System.out.println(text);
		
		String array [] = text.split(" ");
    	// ANCHE GLI SPAZI EXTRA VENGONO CONSIDERATI COME ERRORI
    	for (String s : array)
    		input.add(s.trim().toLowerCase());
		
		List<Double> t1 = new ArrayList<>();
		List<Double> t2 = new ArrayList<>();
		List<Double> t3 = new ArrayList<>();

		for (int test = 0; test < NUMBER_OF_TESTS; test++) {

			long start = System.nanoTime();
			model.spellCheckText(input);
			long end = System.nanoTime();
			t1.add((end - start) / 10e6);

			start = System.nanoTime();
			model.spellCheckTextLinear(input);
			end = System.nanoTime();
			t2.add((end - start) / 10e6);

			start = System.nanoTime();
			model.spellCheckTextDichotomic(input);
			end = System.nanoTime();
			t3.add((end - start) / 10e6);
		}

		System.out.print("\n");
		System.out.println("debug contains: " + t1);
		System.out.println("debug linear: " + t2);
		System.out.println("debug dichotomic: " + t3);
		System.out.print("\n");
		
		System.out.println(String.format("%-30s %10f %-2s (AVG on %d runs)", "CONTAINS took: ",
				calculateAverage(t1), "ms", NUMBER_OF_TESTS));
		System.out.println(String.format("%-30s %10f %-2s (AVG on %d runs)", "LINEAR SEARCH took: ",
				calculateAverage(t2), "ms", NUMBER_OF_TESTS));
		System.out.println(String.format("%-30s %10f %-2s (AVG on %d runs)", "DICHOTOMIC SEARCH took: ",
				calculateAverage(t3), "ms", NUMBER_OF_TESTS));
	}

	private static double calculateAverage(List<Double> marks) {
		Double sum = 0.0;
		if (!marks.isEmpty()) {
			for (Double mark : marks) {
				sum += mark;
			}
			return sum.doubleValue() / marks.size();
		}
		return sum;
	}


}

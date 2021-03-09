package assignment_1_improved;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class counts the number of words that occurs in a single file.
 */

public class WordCounter{
	
	private Map<String, Integer> wordCounts;
	
	// >> Constructor <<
	public WordCounter(){
		wordCounts = new TreeMap<>();
	}
	
	/**
	 * ParseFile method takes in a file and processes it file.
	 * @param file
	 * @throws IOException
	 * @return wordCounts. A Map.
	 */
	public Map<String, Integer> parseFile(File file) throws IOException{
		Scanner scanner = new Scanner(file);
		// >> Scanning token by token <<
		while (scanner.hasNext()){
			String token = scanner.next();
			if (isValidWord(token)){
				countWord(token);
			}
		}
		scanner.close();
		
		return this.wordCounts;
	}
	
	/**
	 * Helper Function takes in a token and checks to see if it is a valid string using
	 * regular expressions.
	 * @param word
	 * @return Boolean. True if the string is valid, false otherwise.
	 */
	private boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
	
	}
	
	/**
	 * Helper Function checks to see if the token exists in the Map.
	 * @param word Token that is a valid string.
	 * @return Nothing.
	 */
	private void countWord(String word){
		if(wordCounts.containsKey(word.toLowerCase())){
			// >> If the token exists overwrite the count <<
			int previous = wordCounts.get(word.toLowerCase());
			wordCounts.put(word.toLowerCase(), previous+1);
		}else{
			// >> If the token doesn't exist put it into the map with count 1<<
			wordCounts.put(word.toLowerCase(), 1);
		}
	}
}
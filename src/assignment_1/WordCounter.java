package assignment_1;
import java.io.*;
import java.util.*;


public class WordCounter{
	
	private Map<String, Integer> wordCounts;
	
	public WordCounter(){
		wordCounts = new TreeMap<>();
	}
	
	public void parseFile(File file) throws IOException{
		System.out.println("Starting parsing the file:" + file.getAbsolutePath());

		// >> parse each file one by one inside the directory <<
		File[] content = file.listFiles();
		for(File current: content){
			Map<String, Integer> temp = new TreeMap<>();
			
			// >> Parse through a file with a empty treemap <<
			parseFile(current, temp);

			Set<String> keys = temp.keySet();
			Iterator<String> keyIterator = keys.iterator();

			// >> Add all the unique words once to the wordCounts treemap <<
			while(keyIterator.hasNext()){
				String key = keyIterator.next();
				if(wordCounts.containsKey(key)){
					int previous = wordCounts.get(key);
					wordCounts.put(key, previous+1);
				}
				else{
					wordCounts.put(key, 1);
				}
			}
		}
	}

	/**
	 * This helper functions keeps track of which unique words appear
	 * in a specific file.
	 * @param file the specific file we are parsing.
	 * @param counts an empty treemap.
	 * @throws IOException
	 */
	public void parseFile(File file, Map<String, Integer> counts) throws IOException{
		Scanner scanner = new Scanner(file);
		// scanning token by token
		while (scanner.hasNext()){
			String token = scanner.next();
			if (isValidWord(token)){
				countWord(token, counts);
			}
		}
		scanner.close();
	}
	
	/**
	 * Check to see if a token is a valid word.
	 * @param word
	 * @return Boolean.
	 */
	private boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
			
	}
	
	/**
	 * Helper Function counts a word once if it is not in a treemap.
	 * @param word token being checked.
	 * @param counts treemap
	 */
	private void countWord(String word, Map<String, Integer> counts){
		if(!(counts.containsKey(word))){
			counts.put(word, 1);
		}
	}
	
	/**
	 * Getter function
	 * @return TreeMap.
	 */
	public Map<String, Integer> getTreeMap(){
		return this.wordCounts;
	}

	//main method
	public static void main(String[] args) {
		
	}
	
}
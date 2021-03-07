package assignment_1;
import java.io.*;
import java.util.*;

public class training {
    public static void main(String[] args){
		// >> Define some variables <<
    	File dataDir1 = new File("data\\train\\ham");
        File dataDir2 = new File("data\\train\\spam");
		WordCounter wordCounter;
		Set<String> keys;
		Iterator<String> keyIterator;

		// >> trainHamFreq <<
		wordCounter = new WordCounter();
		Map<String, Integer> trainHamFreq = new TreeMap<>();
        try{
			wordCounter.parseFile(dataDir1);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDir1.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        trainHamFreq = wordCounter.getTreeMap();

		Map<String, Double> trainHamFreqProbabilities = new TreeMap<>();
		keys = trainHamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainHamFreqProbabilities.put(key, trainHamFreq.get(key)/(double)wordCounter.fileCount(dataDir1));
		}


        // >> trainSpamFreq <<
        wordCounter = new WordCounter();
        Map<String, Integer> trainSpamFreq = new TreeMap<>();

        try{
			wordCounter.parseFile(dataDir2);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDir1.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        trainSpamFreq = wordCounter.getTreeMap();

        // >> Two maps containing the probabilities for each word <<
		Map<String, Double> trainSpamFreqProbabilities = new TreeMap<>();
		keys = trainSpamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainSpamFreqProbabilities.put(key, trainSpamFreq.get(key)/(double)wordCounter.fileCount(dataDir2));
		}

		// >> Overall Probability TreeMap <<
		Map<String, Double> probabilityTreeMap = new TreeMap<>();
		keys = trainSpamFreqProbabilities.keySet();
		keyIterator = keys.iterator();
		double spam;
		double ham;

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			spam = trainSpamFreqProbabilities.get(key);
			if (trainHamFreqProbabilities.containsKey(key)){
				ham = trainHamFreqProbabilities.get(key);
			}else{
				ham = 0.0;
			}
			probabilityTreeMap.put(key, spam/spam+ham);
		}


    }
}

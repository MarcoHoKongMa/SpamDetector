package assignment_1;
import java.io.*;
import java.util.*;

public class Training {
    // >> Class Parameters <<
	private File dataDir1;
    private File dataDir2;
	private Map<String, Double> probTreeMap;

	public Training(){
		this.dataDir1 = new File("data\\train\\ham");
		this.dataDir2 = new File("data\\train\\spam");

		// >> probability that a word is considered spam <<
		Map<String, Double> probabilityTreeMap = new TreeMap<>();
		Map<String, Double> trainSpamFreqProbabilities = trainSpamFrequencyProb();
		Map<String, Double> trainHamFreqProbabilities = trainHamFrequencyProb();
		Set<String> keys = trainSpamFrequencyProb().keySet();
		Iterator<String> keyIterator = keys.iterator();
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
			probabilityTreeMap.put(key, spam/(spam+ham));
		}
		this.probTreeMap = probabilityTreeMap;
	}
	
	private Map<String, Double> trainHamFrequencyProb(){
		fileCounter fileWordCounter = new fileCounter();
		Map<String, Integer> trainHamFreq = new TreeMap<>();
		Set<String> keys;
		Iterator<String> keyIterator;

		// >> Percentage of files that contain a specific word <<
		try{
			fileWordCounter.parseFile(this.dataDir1);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + this.dataDir1.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        trainHamFreq = fileWordCounter.getTreeMap();

		// >> Probabilities that a word appears in a ham file <<
		Map<String, Double> trainHamFreqProbabilities = new TreeMap<>();
		keys = trainHamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainHamFreqProbabilities.put(key, trainHamFreq.get(key)/(double)fileWordCounter.fileCount(dataDir1));
		}
		return trainHamFreqProbabilities;
	}

	private Map<String, Double> trainSpamFrequencyProb(){
		fileCounter fileWordCounter = new fileCounter();
		Map<String, Integer> trainSpamFreq = new TreeMap<>();
		Set<String> keys;
		Iterator<String> keyIterator;
		
		// >> Percentage of files that contain a specific word <<
        try{
			fileWordCounter.parseFile(this.dataDir2);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDir2.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        trainSpamFreq = fileWordCounter.getTreeMap();

		// >> Probabilities that a word appears in a spam file <<
		Map<String, Double> trainSpamFreqProbabilities = new TreeMap<>();
		keys = trainSpamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainSpamFreqProbabilities.put(key, trainSpamFreq.get(key)/(double)fileWordCounter.fileCount(this.dataDir2));
		}
		return trainSpamFreqProbabilities;
	}

	public Map<String, Double> getProbTreeMap(){
		return this.probTreeMap;
	}
}

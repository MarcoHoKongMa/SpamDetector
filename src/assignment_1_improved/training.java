package assignment_1_improved;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class training {
    // >> Class Parameters <<
	private File dataDir1;
    private File dataDir2;
	private File dataDir3;
	private Map<String, Double> probTreeMap;

	// >> Constructor <<
	public training(String directory){
		this.dataDir1 = new File(directory + "\\train\\ham");
		this.dataDir2 = new File(directory + "\\train\\spam");
		this.dataDir3 = new File(directory + "\\train\\ham2");

		// >> probability that a word is considered spam <<
		Map<String, Double> probabilityTreeMap = new TreeMap<>();
		Map<String, Double> trainSpamFreqProb = trainSpamFreqProb();
		Map<String, Double> trainHamFreqProb = trainHamFreqProb();
		Set<String> keys2;
		Set<String> keys1;
		Set<String> keys;
		Iterator<String> keyIterator;
		String key;
		double spam;
		double ham;

		keys = trainSpamFreqProb.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			key = keyIterator.next();
			spam = trainSpamFreqProb.get(key);
			if (trainHamFreqProb.containsKey(key)){
				ham = trainHamFreqProb.get(key);
			}else{
				ham = 0.0;
			}
			probabilityTreeMap.put(key, spam/(spam+ham));
		}

		this.probTreeMap = probabilityTreeMap;
	}

	/**
	 * Helper Function used to merge 2 sets.
	 * @param set1 Set A
	 * @param set2 Set B
	 * @return set. Set = Set A + Set B
	 */
	private Set<String> mergeSet(Set<String> set1, Set<String> set2){
		Set<String> set = new TreeSet<String>();
		set.addAll(set1);
		set.addAll(set2);
		return set;
	}

	/**
	 * This helper function records the occurrences of every unique word
	 * within a directory that does not contain spam files and then calculates
	 * the percentage of files that contain that unique word and returns a Map.
	 *
	 * @return trainHamFreqProb.
	 */
	private Map<String, Double> trainHamFreqProb(){
		fileCounter fileWordCounter = new fileCounter();
		fileCounter fileWordCounter1 = new fileCounter();
		Map<String, Integer> trainHamFreq = new TreeMap<>();
		Map<String, Integer> trainHam1Freq = new TreeMap<>();
		Map<String, Integer> trainHam2Freq = new TreeMap<>();
		Set<String> keys;
		Set<String> keys1;
		Set<String> keys2;
		String key;
		Iterator<String> keyIterator;

		// >> Percentage of files that contain a specific word <<
		try{
			fileWordCounter.parseFile(this.dataDir1);
			fileWordCounter1.parseFile(this.dataDir3);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + this.dataDir1.getAbsolutePath());
			System.err.println("Invalid input dir: " + this.dataDir3.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		// >> Merging two maps <<
        trainHam1Freq = fileWordCounter.getTreeMap();
		trainHam2Freq = fileWordCounter1.getTreeMap();
		keys1 = trainHam1Freq.keySet();
		keys2 = trainHam2Freq.keySet();
		keys = mergeSet(keys1, keys2);
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()) {
			key = keyIterator.next();

			if (trainHam1Freq.containsKey(key) && trainHam2Freq.containsKey(key)){
				trainHamFreq.put(key, trainHam1Freq.get(key)+trainHam2Freq.get(key));
			}
			else if (trainHam1Freq.containsKey(key)){
				trainHamFreq.put(key, trainHam1Freq.get(key));
			}
			else{
				trainHamFreq.put(key, trainHam2Freq.get(key));
			}
		}

		// >> Probabilities that a word appears in a ham file <<
		Map<String, Double> trainHamFreqProb = new TreeMap<>();
		keys = trainHamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			key = keyIterator.next();
			trainHamFreqProb.put(key, (double)trainHamFreq.get(key)/((double)fileWordCounter.fileCount(dataDir1)+(double)fileWordCounter.fileCount(dataDir3)));
		}
		return trainHamFreqProb;
	}

	/**
	 * This helper function records the occurrences of every unique word
	 * within a directory that contains spam files and then calculates
	 * the percentage of files that contain that unique word and returns a Map.
	 *
	 * @return trainSpamFreqProb.
	 */
	private Map<String, Double> trainSpamFreqProb(){
		fileCounter fileWordCounter = new fileCounter();
		Map<String, Integer> trainSpamFreq;
		Set<String> keys;
		Iterator<String> keyIterator;
		
		// >> Percentage of files that contain a specific word <<
        try{
			fileWordCounter.parseFile(this.dataDir2);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + this.dataDir2.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        trainSpamFreq = fileWordCounter.getTreeMap();

		// >> Probabilities that a word appears in a spam file <<
		Map<String, Double> trainSpamFreqProb = new TreeMap<>();
		keys = trainSpamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainSpamFreqProb.put(key, (double)trainSpamFreq.get(key)/(double)fileWordCounter.fileCount(this.dataDir2));
		}
		return trainSpamFreqProb;
	}

	public Map<String, Double> getProbTreeMap(){
		return this.probTreeMap;
	}

	public static void main(String[] args){
	}
}

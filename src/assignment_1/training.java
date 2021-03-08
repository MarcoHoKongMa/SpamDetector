package assignment_1;
import java.io.*;
import java.util.*;

public class training {
    // >> Class Parameters <<
	private File dataDir1;
    private File dataDir2;
	private File dataDir3;
	private Map<String, Double> probTreeMap;

	public training(String directory){
		this.dataDir1 = new File(directory + "\\train\\ham");
		this.dataDir2 = new File(directory + "\\train\\spam");
		this.dataDir3 = new File(directory + "\\train\\ham2");

		// >> probability that a word is considered spam <<
		Map<String, Double> probabilityTreeMap = new TreeMap<>();
		Map<String, Double> probabilityTreeMap1 = new TreeMap<>();
		Map<String, Double> probabilityTreeMap2 = new TreeMap<>();
		Map<String, Double> trainSpamFreqProbabilities = trainSpamFrequencyProb();
		Map<String, Double> trainHamFreqProbabilities = trainHamFrequencyProb();
		double spam;
		double ham;

		Set<String> keys = trainSpamFreqProbabilities.keySet();
		Iterator<String> keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			spam = trainSpamFreqProbabilities.get(key);
			if (trainHamFreqProbabilities.containsKey(key)){
				ham = trainHamFreqProbabilities.get(key);
			}else{
				ham = 0.0;
			}
			probabilityTreeMap1.put(key, spam/(spam+ham));
		}

		keys = trainHamFreqProbabilities.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			ham = trainHamFreqProbabilities.get(key);

			if (trainSpamFreqProbabilities.containsKey(key)){
				spam = trainSpamFreqProbabilities.get(key);
			}else{
				spam = 0.0;
			}
			probabilityTreeMap2.put(key, spam/(spam+ham));
		}

		// >> Deep Copy trainSpamFreqProbabilities2 <<
		keys = probabilityTreeMap1.keySet();
		keyIterator = keys.iterator();
		while(keyIterator.hasNext()) {
			String key = keyIterator.next();
			probabilityTreeMap.put(key, probabilityTreeMap1.get(key));
		}

		// >> Merge trainSpamFreqProbabilities1 <<
		keys = probabilityTreeMap2.keySet();
		keyIterator = keys.iterator();
		while(keyIterator.hasNext()) {
			String key = keyIterator.next();
			if(probabilityTreeMap.containsKey(key)){
				double previous = probabilityTreeMap.get(key);
				probabilityTreeMap.put(key, previous+probabilityTreeMap2.get(key));
			}
			else{
				probabilityTreeMap.put(key, probabilityTreeMap2.get(key));
			}
		}
		this.probTreeMap = probabilityTreeMap;
		System.out.println(this.probTreeMap.get("ya"));
	}
	
	private Map<String, Double> trainHamFrequencyProb(){
		fileCounter fileWordCounter = new fileCounter();
		fileCounter fileWordCounter1 = new fileCounter();
		Map<String, Integer> trainHamFreq = new TreeMap<>();
		Map<String, Integer> trainHam1Freq = new TreeMap<>();
		Map<String, Integer> trainHam2Freq = new TreeMap<>();
		Set<String> keys;
		Iterator<String> keyIterator;

		// >> Percentage of files that contain a specific word <<
		try{
			fileWordCounter.parseFile(this.dataDir1);
			fileWordCounter1.parseFile(this.dataDir3);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + this.dataDir1.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		// >> Merging two maps <<
        trainHam1Freq = fileWordCounter.getTreeMap();
		trainHam2Freq = fileWordCounter1.getTreeMap();

		// >> Deep Copy trainHam1Freq <<
		keys = trainHam1Freq.keySet();
		keyIterator = keys.iterator();
		while(keyIterator.hasNext()) {
			String key = keyIterator.next();
			trainHamFreq.put(key, trainHam1Freq.get(key));
		}

		// >> Merge trainHam2Freq <<
		keys = trainHam2Freq.keySet();
		keyIterator = keys.iterator();
		while(keyIterator.hasNext()) {
			String key = keyIterator.next();
			if(trainHamFreq.containsKey(key)){
				int previous = trainHamFreq.get(key);
				trainHamFreq.put(key, previous+trainHam2Freq.get(key));
			}
			else{
				trainHamFreq.put(key, trainHam2Freq.get(key));
			}
		}

		// >> Probabilities that a word appears in a ham file <<
		Map<String, Double> trainHamFreqProbabilities = new TreeMap<>();
		keys = trainHamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainHamFreqProbabilities.put(key, (double)trainHamFreq.get(key)/((double)fileWordCounter.fileCount(dataDir1)+(double)fileWordCounter.fileCount(dataDir3)));
		}
		return trainHamFreqProbabilities;
	}

	private Map<String, Double> trainSpamFrequencyProb(){
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
		Map<String, Double> trainSpamFreqProbabilities = new TreeMap<>();
		keys = trainSpamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainSpamFreqProbabilities.put(key, (double)trainSpamFreq.get(key)/(double)fileWordCounter.fileCount(this.dataDir2));
		}
		return trainSpamFreqProbabilities;
	}

	public Map<String, Double> getProbTreeMap(){
		return this.probTreeMap;
	}
}

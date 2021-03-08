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
		Map<String, Double> trainSpamFreqProbabilities = trainSpamFrequencyProb();
		Map<String, Double> trainHamFreqProbabilities = trainHamFrequencyProb();
		Set<String> keys = trainSpamFreqProbabilities.keySet();
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
		fileCounter fileWordCounter1 = new fileCounter();
		Map<String, Integer> trainHamFreq;
		Map<String, Integer> trainHam1Freq;
		Map<String, Integer> trainHam2Freq;
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
		trainHamFreq = mergeMaps(trainHam1Freq, trainHam2Freq);

		// >> Probabilities that a word appears in a ham file <<
		Map<String, Double> trainHamFreqProbabilities = new TreeMap<>();
		keys = trainHamFreq.keySet();
		keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			trainHamFreqProbabilities.put(key, trainHamFreq.get(key)/(double)fileWordCounter.fileCount(dataDir1)+(double)fileWordCounter.fileCount(dataDir3));
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
			trainSpamFreqProbabilities.put(key, trainSpamFreq.get(key)/(double)fileWordCounter.fileCount(this.dataDir2));
		}
		return trainSpamFreqProbabilities;
	}

	private Map<String, Integer> mergeMaps(Map<String, Integer> map1, Map<String, Integer> map2){
		Set<String> keys = map2.keySet();
		Iterator<String> keyIterator = keys.iterator();

		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			if (map1.containsKey(key)){
				int previous = map1.get(key);
				map1.put(key, previous+map2.get(key));
			}
			else {
				map1.put(key, map2.get(key));
			}
		}
		return map1;
	}

	public Map<String, Double> getProbTreeMap(){
		return this.probTreeMap;
	}
}

package assignment_1;

import java.io.*;
import java.util.*;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class testing {
    private File dataDir1;
    private File dataDir2;
    private ObservableList<TestFile> testFiles = FXCollections.observableArrayList();
    private training object;

    public testing(String directory){
        this.dataDir1 = new File(directory + "\\test\\ham");
        this.dataDir2 = new File(directory + "\\test\\spam");
        object = new training(directory);

        File[] content = dataDir1.listFiles();
        System.out.println("Starting parsing the file:" + dataDir1.getAbsolutePath());
		for(File current: content){
            this.testFiles.add(new TestFile(current.getName(), fileSpamProb(current), "Ham"));
        }

        content = dataDir2.listFiles();
        System.out.println("Starting parsing the file:" + dataDir2.getAbsolutePath());
        for(File current: content){
            this.testFiles.add(new TestFile(current.getName(), fileSpamProb(current), "Spam"));
        }
    }

    private double fileSpamProb(File file){
        double spamProbability = 0.0;
        double n = 0.0;
        double prob = 0.0;
        Map<String, Integer> words = new TreeMap<>();
        WordCounter wordCounter = new WordCounter();
        Set<String> keys;
        Iterator<String> keyIterator;
        
        try{
            words = wordCounter.parseFile(file);
        }catch(IOException e){
            
        }finally{
            keys = words.keySet();
		    keyIterator = keys.iterator();
        }

        while(keyIterator.hasNext()){
            String key = keyIterator.next();

            if (this.object.getProbTreeMap().containsKey(key)){
                prob = this.object.getProbTreeMap().get(key);
                n += (double)(words.get(key)*(Math.log(1.0 - prob) - Math.log(prob)));
            }
        }

        spamProbability = 1.0 / (1.0 + Math.exp(n));
//        System.out.printf("%.5f\n", spamProbability);
        return spamProbability;
    }

    public ObservableList<TestFile> getTestFiles(){
        return this.testFiles;
    }
}

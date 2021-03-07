package assignment_1;
import java.io.*;
import java.util.*;

import java.io.File;

public class Testing {
    private File dataDir1;
    private File dataDir2;
    private ArrayList<TestFile> testFiles;
    private Training object = new Training();

    public Testing(){
        this.dataDir1 = new File("data\\test\\ham");
        this.dataDir2 = new File("data\\test\\spam");

        File[] content = dataDir1.listFiles();
		for(File current: content){
            this.testFiles.add(new TestFile(current.getName(), fileSpamProb(current), "Ham"));
        }

        content = dataDir2.listFiles();
        for(File current: content){
            this.testFiles.add(new TestFile(current.getName(), fileSpamProb(current), "Spam"));
        }
    }

    private double fileSpamProb(File file){
        double spamProbability = 0;
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
                spamProbability += words.get(key) * this.object.getProbTreeMap().get(key);
            }
        }
        return spamProbability;
    }

    public ArrayList<TestFile> getTestFiles(){
        return this.testFiles;
    }
}

package assignment_1;
import java.io.*;
import java.util.*;

public class training {
    public static void main(String[] args){
		File dataDir1 = new File("\\train\\ham");
        File dataDir2 = new File("\\train\\spam");
		
		WordCounter wordCounter = new WordCounter();
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
    }
}

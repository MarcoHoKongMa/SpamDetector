package assignment_1;
import java.io.*;
import java.util.*;

public class training {
    public static void main(String[] args){
		File dataDir1 = new File("\\train\\ham");
        File dataDir2 = new File("\\train\\spam");
		
		WordCounter trainHamFreq = new WordCounter();
        WordCounter trainSpamFreq = new WordCounter();
		
        trainHamFreq.parseFile(dataDir1);
        trainSpamFreq.parseFile(dataDir2);

    }
}

package assignment_1;
import java.io.*;
import java.util.*;

import java.io.File;

public class testing {
    public static void main(String[] args){
        // >> Define some variables <<
        File dataDir1 = new File("data\\train\\ham");
        File dataDir2 = new File("data\\train\\spam");
        WordCounter wordCounter = new WordCounter();
        TestFile[] testList = new TestFile[wordCounter.fileCount(dataDir1) + wordCounter.fileCount(dataDir2)];


    }

    // >> Get array of Strings from file <<
    public String[] fileWord(){

    }
}

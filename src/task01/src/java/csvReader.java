package src.java;

import java.io.FileReader;
import java.io.BufferedReader;


public class csvReader{

    private final int NAME_COL = 0;
    private final int CATEGORY_COL = 1;
    private final int RATING_COL = 2;

    public void readCSV (String args) throws Exception{
        try(FileReader fr = new FileReader(args)){
            BufferedReader br = new BufferedReader(fr);

        }

    }
    
}
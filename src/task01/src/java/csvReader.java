package src.java;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;


public class csvReader{

    private Map<String,List<Application>> categorised = new HashMap<String, List<Application>>();
    private final int NAME_COL = 0;
    private final int CATEGORY_COL = 1;
    private final int RATING_COL = 2;

    public void readCSV (String args) throws Exception{
        try(FileReader fr = new FileReader(args)){
            BufferedReader br = new BufferedReader(fr);
            //read the csv file in buffered reader
            //turn into a stream
            //return a map<string,list> called categorised
            categorised = br.lines()
                .skip(1)
                .filter(line -> !line.isEmpty())
                //split according to commas
                .map(line -> line.split(","))
                //create new app object
                .map(line -> new Application(line[NAME_COL],line[CATEGORY_COL],line[RATING_COL]))
                //terminate the stream and return a map<String,list>
                .collect(Collectors.groupingBy(app -> app.getCategory()))
            ;
        }
        int lineCount = 0;
        for(String key:categorised.keySet()){
            lineCount += (categorised.get(key)).size();
            List<Application> discarded = new ArrayList<Application>();
            List<Application> approved = new ArrayList<Application>();
            Float highest = 0.0f;
            String highestName="";
            Float lowest = 5.0f;
            String lowestName="";
            Float total = 0.0f;
            for(Application app:categorised.get(key)){
                Float rating = app.getRating();
                if(rating.isNaN()){
                    discarded.add(app);
                    continue;
                }
                else{
                    approved.add(app);
                }
                if(rating>highest){
                    highest = rating;
                    highestName = app.getName();
                }
                if(rating<lowest){
                    lowest = rating;
                    lowestName = app.getName();
                }
                total += rating;
            }
            Float average = total/approved.size();
            System.out.println("CATEGORY: "+key.toUpperCase());
            System.out.printf("     Highest: %s,%.2f\n",highestName,highest);
            System.out.printf("     Lowest: %s,%.2f\n",lowestName,lowest);
            System.out.printf("     Average: %.2f\n",average);
            System.out.printf("     Count: %d\n",(categorised.get(key)).size());
            System.out.printf("     Discarded: %d\n",discarded.size());
        }
        System.out.printf("Total lines read: %d",lineCount);
    }
}
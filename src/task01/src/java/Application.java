package src.java;


public class Application {

    private final String name;
    private final String category;
    private final Float rating;

    public Application(String name, String category, String ratingString){
        this.name = name;
        this.category = category.toLowerCase();
        this.rating = Float.parseFloat(ratingString);
    }

    public String getName(){return name;}
    public String getCategory(){return category;}
    public Float getRating(){return rating;}
    
}

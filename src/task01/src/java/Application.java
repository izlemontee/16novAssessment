package src.java;


public class Application {

    private String name;
    private String category;
    private Float rating;

    public Application(String name, String category, String ratingString){
        this.name = name;
        this.category = category.toLowerCase();
        this.rating = Float.parseFloat(ratingString);
    }

    public String getName(){return name;}
    public String getCategory(){return category;}
    public Float getRating(){return rating;}
    
}

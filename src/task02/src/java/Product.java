package src.java;

public class Product {
    private int prod_id;
    private String title;
    private float price;
    private float rating;

    public Product(){
        
    }

    public void setID(String prod_id_string){
        prod_id = Integer.parseInt(prod_id_string);
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setPrice(String priceString){
        price = Float.parseFloat(priceString);
    }
    public void setRating(String ratingString){
        rating = Float.parseFloat(ratingString);
    }
    public int getID(){
        return prod_id;
    }

    public String getTitle(){
        return title;
    }
    public float getPrice(){return price;}
    public float getRating(){return rating;}
    
    
}

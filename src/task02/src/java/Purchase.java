package src.java;

import java.util.*;
import java.util.ArrayList;

public class Purchase {

    private String request_id;
    private int itemCount;
    private float budget;
    private List<Product> productList = new ArrayList<Product>();
    private Map<String, Product> productMap = new HashMap<String,Product>();

    public Purchase(String request_id){
        this.request_id = request_id;
    }
    

    public String getRequestID(){return request_id;}
    public void setItemCount(String itemCountString){
        itemCount = Integer.parseInt(itemCountString);
    }

    public void setBudget(String budgetString){
        budget = Float.parseFloat(budgetString);
    }

    public Float getBudget(){return budget;}

    public void addProduct(Product p){
        //int id = p.getID();
        //String IDString = Integer.toString(id);
        productMap.put(Integer.toString(p.getID()),p);
        productList.add(p);
    }
    public int getItemCount(){return itemCount;}
    
    public Map<String,Product> getProductMap(){return productMap;}
    
    public List<Product> getProductList(){return productList;}
    
    public void printProducts(){
        for(String s:productMap.keySet()){
            Product p = productMap.get(s);
            System.out.println(p.getTitle());
            System.out.println(p.getID());
            System.out.println(p.getPrice());
            System.out.println(p.getRating());

        }
        System.out.println("Products printed.");
    }
    
}

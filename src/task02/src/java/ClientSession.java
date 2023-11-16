package src.java;

import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;
import java.util.stream.Collectors;

public class ClientSession {

    private Socket socket;
    private final String END = "client_end\n";

    public ClientSession(Socket socket) {
		this.socket = socket;
	}

    public void start() throws Exception{
        //get the inputstream from the socket, but the data that comes out of here is in bytes
		InputStream is = socket.getInputStream();
		//an inputstreamreader is then needed to convert the bytes from inputstream to  characters in streams
		InputStreamReader isr = new InputStreamReader(is);
		//use a buffered reader for more efficient handling of data
		BufferedReader br = new BufferedReader(isr);
		//do the same for outputstream to write back to the socket
		OutputStream os = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);

        String line = "";
        boolean stop = false;
        Purchase purchase = null;
        Product product = null;
        int item_count = 0;
        int productCounter = 0;

        while(!stop){
            //reads the info from server
            line = br.readLine().trim();
            //will keep repeating this, taking in messages from server.
            //server will stop sending messages when it reaches "prod_end" but client will still keep polling

            String[] lineSplit = line.split(":");
            //switch cases to check through the lines and get the info
            switch(lineSplit[0].toLowerCase().trim()){
                case "request_id":{
                    purchase = new Purchase(lineSplit[1].trim());
                    break;
                }

                case "item_count":{
                    purchase.setItemCount(lineSplit[1].trim());
                    item_count = purchase.getItemCount();
                    break;
                }

                case "budget":{
                    purchase.setBudget(lineSplit[1].trim());
                    break;
                }

                case "prod_start":{
                    product = new Product();
                    break;
                }
                case "prod_id":{
                    product.setID(lineSplit[1].trim());
                    break;
                }
                case "title":{
                    product.setTitle(lineSplit[1].trim());
                    break;
                }
                case "price":{
                    product.setPrice(lineSplit[1].trim());
                    break;
                }
                case "rating":{
                    product.setRating(lineSplit[1].trim());
                    break;
                }
                case "prod_end":{
                    purchase.addProduct(product);
                    productCounter++;
                    product = null;
                    break;
                }
            }
            //sort the products according to rating and price

            //once it has collected everything, denoted by the product counter equal to the item count,
            //write to the server
            if((productCounter == item_count)&&(productCounter>0)){
                //method writeToServer found after this method
                writeToServer(purchase, bw);
                System.out.println(br.readLine());
                //end the session
                stop = true;
            }
        }
    }
   
    public void writeToServer(Purchase purchase, BufferedWriter bw) throws Exception{
        List<Product> pl = purchase.getProductList();
                //declare the comparators
                Comparator<Product> comparator = Comparator.comparing(productcompare -> productcompare.getRating());
                comparator = comparator.thenComparing(Comparator.comparing(price -> price.getPrice())).reversed();
                //returns a comparator in descending order
                

                //iterate through the product list in a stream and sort it out
                pl = pl.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList())
                    ;
                Float budget = purchase.getBudget();
                Float totalPrice = 0.00f;
                List<Product> purchaseList = new ArrayList<Product>();
                //iterate through the sorted product list, pl
                for(Product p:pl){
                    float productprice = p.getPrice();
                    //check if the price, added to total, exceeds budget
                    if((totalPrice+productprice)>budget){
                        //System.out.printf("Product %d exceeds budget.\n",productid);
                        continue;
                    }
                    else{
                        totalPrice += productprice;
                        purchaseList.add(p);
                    }
                    
                }
                System.out.printf("Budget: %.2f. Price incurred: %.2f\n", budget, totalPrice);
                //purchase.printProducts();
                
                //write
                String request_id = purchase.getRequestID() + "\n";
                String name = "Isaac Tee Yuan Jie\n";
                String email = "iztyj.work@gmail.com\n";
                float remainder = budget - totalPrice;
                bw.write("request_id: "+request_id);
                bw.flush();
                bw.write("name: "+name);
                bw.flush();
                bw.write("email: "+email);
                bw.flush();
                StringBuilder sb = new StringBuilder();
                sb.append("items: ");
                //iterate a for loop for all the items purchased
                for (int i = 0; i<purchaseList.size();i++){
                    Product p = purchaseList.get(i);
                    //get the ID of the item purchased
                    int itemID = p.getID();
                    //bw.write(purchaseName);
                    //append to the stringbuilder
                    sb.append(itemID);
                    if(i<(purchaseList.size()-1)){
                        bw.write(",");
                    }
                }
                sb.append("\n");
                String bwItems = sb.toString();
                bw.write(bwItems);
                bw.flush();

                sb = new StringBuilder();
                sb.append("spent: ");
                sb.append(totalPrice);
                sb.append("\n");
                String spentString = sb.toString();
                bw.write(spentString);
                bw.flush();

                sb = new StringBuilder();
                sb.append("remaining: ");
                sb.append(remainder);
                sb.append("\n");
                String remainderString = sb.toString();
                bw.write(remainderString);
                bw.flush();

                bw.write(END);
                bw.flush();
    }
}

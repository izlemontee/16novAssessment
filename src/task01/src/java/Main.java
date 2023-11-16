package src.java;

public class Main {

    public static void main(String[] args) throws Exception{
        csvReader cr = new csvReader();
        if(args.length<=0){
            System.out.println("CSV name not entered.");
            System.exit(0);
        }
        cr.readCSV(args[0]);
    }
    
}

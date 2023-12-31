package src.java;

import java.net.Socket;

public class Client{

    public static void main (String[] args)throws Exception{
        final int DEFAULT_PORT = 3000;
        final String DEFAULT_SERVER = "localhost";
        int port=0;
        String serverName="";
        //check the args length for the different use cases
        switch(args.length){
            case 0:{
                port = DEFAULT_PORT;
                serverName = DEFAULT_SERVER;
                break;
            }

            case 1:{
                port = Integer.parseInt(args[0]);
                serverName = DEFAULT_SERVER;
                break;
            }

            case 2:{
                serverName = args[0];
                port = Integer.parseInt(args[1]);
                break;
            }
            default:
                System.out.println("Invalid args length.");
                System.exit(1);
                break;
        }

        Socket socket = new Socket(serverName,port);
        ClientSession cs = new ClientSession(socket);
        cs.start();
    }

}
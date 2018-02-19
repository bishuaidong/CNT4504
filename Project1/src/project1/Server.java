package project1;

import java.net.*;

public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            /**server is server socket object and 8080 that is passed through
            is the port number that identifies the application on the network.
            * When a connection is made, server.accept() will return a socket
            * object which Socket socket will capture.
            */
            System.out.println("Waiting for connection");
            ServerSocket server = new ServerSocket(8080);
            Socket connection = server.accept();
            System.out.println("Connection successful");
        } catch (Exception e) {
            /**
             * Try catch used in case connection was not found. Will display
             * error to user.
             */
            System.out.println("Connection unsuccessful");
            
            //for diagnosis, use
            e.printStackTrace();
            
        }
    }
    
}

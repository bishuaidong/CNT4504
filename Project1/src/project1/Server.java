package project1;

import java.io*;
import java.net.*;
import java.text*;
import java.util.*;
import java.lang.management.*;

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
            
        } catch (Exception e) {
            /**
             * Try catch used in case connection was not found. Will display
             * error to user.
             */
            
            //for diagnosis, use
            e.printStackTrace();
            
        }
        
        String command;
        
        try {
            Socket connection = server.accept();
            System.out.println("Connection successful");
            
            boolean running = true;
            
            while(running) {
                //Starts the I/O stream
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                bufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                // waits for a command from the client
                while(!connection.isClosed() && ((command = in.readLine()) != null)) {
                    //prints linux command
                    System.out.println(command);
                    
                    //exits the server when given the exit command
                    if (command.equals("exit")) {
                        System.out.println("Exiting the Server");
                        running = false;
                        break;
                    }
                  
                    //runs the given command and sends the output back to the client
                    else {
                        try {
                            //creates a runtime that allows the command to execute as a linux command
                            Runtime runtime = Runtime.getRuntime();
                            Process process = runtime.exec(command);
                            
                            //creates an input stream that saves the results as a string
                            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String results = input.readLine();
                            
                            //prints the results on the server and on the client
                            while (results != null) {
                                System.out.println(results);
                                out.println(results);
                            }
                            
                            out.close(); //close output stream
                            System.out.println("Complete");
                            
                            process.waitFor();
                        }
                        
                        catch(Exception e) {
                            System.out.println("An Error has occured");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        catch {
            System.out.println("Connection unsuccessful");
            e.printStackTrace();
        }
    }
}

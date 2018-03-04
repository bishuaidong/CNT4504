package project1;

import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    	ServerSocket server = null;
    	
    	try {
            /**server is server socket object and 8080 that is passed through
            is the port number that identifies the application on the network.
            * When a connection is made, server.accept() will return a socket
            * object which Socket socket will capture.
            */
            System.out.println("Waiting for connection");
            server = new ServerSocket(10095);
            
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
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
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
                            String results = null;
                            
                            //prints the results on the server and on the client
                            while ((results = input.readLine()) != null) {
                                //System.out.println(results);
                                //try without this ^ first
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
        
        catch(Exception e) {
            System.out.println("Connection unsuccessful");
            e.printStackTrace();
        }
    }
}


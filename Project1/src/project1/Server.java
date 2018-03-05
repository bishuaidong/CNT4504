import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
* This is the main class for the Server. This class creates a socket for the Client to connect to. When a connection is accepted, the
* server takes in the requested linux command from the client, runs the command on the server, and displays the output on both machines.
*/
public class Server {

    public static void main(String[] args) {
        
    	ServerSocket server = null;
    	Socket connection = null;
    	
    	try {
            //creates a server socket object with the correct port number
            //this must be accepted for the connection to be established
            System.out.println("Waiting for connection");
            server = new ServerSocket(8090);
            
        } 
        catch (Exception e) {
            //for diagnosis, use
            e.printStackTrace();
        }
        
        String command;
        
        try { 
            boolean running = true;
            
            //runs until the exit command is given
            while(running) {
                //client to server connection is accepted
            	connection = server.accept();
            	System.out.println("Connection successful");
            	
                //Starts the I/O stream
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                // waits for a command from the client
                while(!connection.isClosed() && ((command = in.readLine()) != null)) {
                    //prints linux command
                    System.out.println(command);
                    
                    //exits the server when given the exit command
                    if (command.equals("exit")) {
                        System.out.println("Exiting the Server\n");
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
                                System.out.println(results);
                                out.println(results);
                            }
                            
                            //close output stream
                            out.close(); 
                            System.out.println("\nComplete\n");
                            
                            process.waitFor();
                        }
                        catch(Exception e) {
                            System.out.println("An Error has occured\n");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("Connection unsuccessful\n");
            e.printStackTrace();
        }
    }
}

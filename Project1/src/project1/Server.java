import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
* This is the main class for the Server. This class creates a socket for the Client to connect to. When a connection is accepted, the
* server takes in the requested linux command from the client, runs the command on the server, and displays the output on both machines.
*/
public class Server {

    public static void main(String[] args) {
        
    	ArrayList<ServerThread> serverThreads = new ArrayList<ServerThread>();
    	ServerSocket server = null;
    	
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
        
        
        try {
        	ServerThread temp;
            boolean running = true;
            
            //runs until the exit command is given
            while(running) {
            	temp = new ServerThread(server.accept());
            	temp.start();
            	serverThreads.add(temp);
            	
            	for (int x = 0; x < serverThreads.size(); x++) {
            		if (!(serverThreads.get(x).isAlive())) {
            			if (temp.exit)
            				running = false;
            			serverThreads.remove(x);
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

class ServerThread extends Thread {
	private Socket connection;
	public boolean exit;
	
	public ServerThread(Socket socket) {
		connection = socket;
		exit = false;
	}
	
	public void run() {
		try {
			System.out.println("Connection successful");
			String command;
			
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
	                exit = true;
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
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

import java.io.*;
import java.net.*;
import java.util.*;
/**
 * This is the main class for the Client. This class must take in at least 1 command line argument (host name), but an addition argument
 * can be added to change the number of clients. A menu is then presented to the user with the options to display system time, the uptime, 
 * the memory use, netstat, current users, and the running processes from the server. The Cient then connects to the Server and sends the 
 * command for every client. After the command results are received for every client, the latency and average latency are displayed to 
 * the user. The menu will loop until the exit command is chosen, and the Client and Server programs will end.
 *
 * @author Bishuai
 * @author Varsha
 * @author Rishav
 * @author Clay
 * @author Daniel
 */
public class Client {
    
    public static int port = 8090;
    public static ClientThread[] threads;
    public static int clientCount;
    public static String host;
    public static String Incommand;
    public static int x;
	
    public static void main(String[] args) {
        
    	
    	
        double totalLatency = 0;
        double avgLatency = 0;
    	
    	
    	
        //No command line arguments
        if (args.length < 1) {
            System.out.println("Did not enter a host name. Goodbye.\n");
        }
        //at least 1 command line argument
        else {
            //hostname
            host = args[0];
            
            //user entered number of clients
            if (args.length == 2) {
                clientCount = Integer.parseInt(args[1]);
            }
            //default number of clients
            else {
                clientCount = 1;
            }
            
            Scanner in = new Scanner ( System.in );
            Client client = new Client();
            Menu menu = new Menu();
            
            boolean running = true;
            
            //while the user has not exited, display the menu and run the chosen command
            while(running) {
		//displays menu can gets the linux command
                menu.display_menu();
                Incommand = menu.getCommand(in.next());
                
                //exit menu
                if(Incommand.equals("exit")) {
                    //tells the server to exit
                    try {
                    	Socket connection = new Socket(host, port);
                        PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
                        output.println("exit");
                        connection.close();
                	}
                    catch (Exception e) {
                    	e.printStackTrace();
                    }
                    
                    //exits the client
                    System.out.println("Goodbye\n");
                    running = false;
                }
		//invalid option
                else if (Incommand.equals("invalid")) {
                	System.out.println("Invalid option\n");
                }
                //valid option - run the chosen command for all clients and prints the average latency
                else {
                    System.out.println("running " + Incommand + "\n\n");
                    
		    //runs the command for every client
                    threads = new ClientThread[clientCount];
                    
                    for (x = 0; x < clientCount; x++)
                    	threads[x] = new ClientThread(Incommand, host);
                    
                    for (x = 0; x < threads.length; x++)
                    	threads[x].start();
                    
                    for (x = 0; x < threads.length; x++) {
                    	try {
                    		threads[x].join();
                    	}
                    	catch (Exception e){
                    		e.printStackTrace();
                    	}
                    }
                    	
                    
                    for (x = 0; x < threads.length; x++)
                    	totalLatency += threads[x].latency;
                    
                    
                    
	 	    //calculates and prints average latency
                    avgLatency = totalLatency / clientCount;
                    System.out.printf("Average latency for %d clients in milliseconds: %.0f\n\n", clientCount, avgLatency);
                    
		    //resets latencies
                    totalLatency = 0;
                    avgLatency = 0;
                }
         
            }
            in.close();
        }
    }
}    

class ClientThread extends Thread {
	public double latency, end, start;
	public String command, host;
	public int port = 8090;
	
	public ClientThread(String com, String hos) {
		command = com;
		host = hos;
		latency = 0;
	}
	
	public void run() {
		try {
    	    String line = null;
            
            //connects to the server
            Socket connection = new Socket(host, port);
            connection.setSoTimeout(30000);
            System.out.println("Connection Successful\n");
            
            //opens I/O stream
            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            //gives command to server
            output.println(command);
            
            //start timer
            start = System.currentTimeMillis();
            	
            //print output
            while((line = input.readLine()) != null) 
            	System.out.println(line);
            
            //end timer
            end = System.currentTimeMillis();
            
            System.out.println("");
		
            //calculate and display latency
            latency = end - start;
            System.out.printf("Latency in milliseconds: %.0f\n\n", latency);
            
            output.close();
            input.close();
            connection.close();
    	}
    	catch (Exception e) {
            System.out.println("Could not connect to the server\n");
    		e.printStackTrace();
    	}
	}
	
}


/**
* This class displays a menu to the user and determines which linux command to send to the server. The class also checks for invalid
* choices.
*/
class Menu {
    
    public Menu() {
    
    }
    public String getCommand(String input) {
        
        String command = " ";
        
        //determines which linux command to run based on the user's input
        switch (input) {
        case "1":
            System.out.println ( "current Date and Time\n" );
            command = "date";
            break;
        case "2":
            System.out.println ( "uptime\n" );
            command = "uptime";
            break;
        case "3":
            System.out.println ( "memory use\n" );
            command = "free";
            break;
        case "4":
            System.out.println ( "Netstat\n" );
            command = "netstat";
            break;
        case "5":
            System.out.println ( "current users\n" );
            command = "who";
            break;
        case "6":
            System.out.println ( "running processes\n" );
            command = "ps aux";
            break;
        case "7":
            System.out.println("Quitting\n");
            command = "exit";
            break;
        default:
            command = "invalid";
            break;
        }
      return command;
    }
    
    public void display_menu() {
        //displays the menu
        System.out.println("The menu provides the following choices to the user: ");
                        System.out.println("1. Host current Date and Time \n2. Host uptime\n"
                                        + "3. Host memory use \n4. Host Netstat \n5. Host current users "
                                        + "\n6. Host running processes \n7. Quit ");
                        System.out.print("Please select an action: ");
 }
}

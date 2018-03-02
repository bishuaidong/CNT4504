/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Bishuai
 * @author Varsha
 * @author Rishav
 * @author Clay
 * @author Danniel
 */
public class Client {
    
    
    public static void main(String[] args) {
        
        
        int threadCount;
        ClientThread[] threads;
        String host;
        int portNumber = 8080;
        
        if (args.length < 1) {
            System.out.println("Did not enter a host name. Goodbye.");
        }
        else {
            host = args[0];
            
            if (args.length == 2) {
                threadCount = Integer.parseInt(args[1]);
            }
            else {
                threadCount = 1;
            }
            
            
            
            Scanner in = new Scanner ( System.in );
            Menu menu = new Menu();
            String command;
            boolean running = true;
        
            while(running) {
                menu.display_menu();
                command = menu.getCommand(in.next());
            
                if(command.equals("exit")) {
                    System.out.println("Goodbye");
                    running = false;
                }
                else {
                    System.out.println("running " + command + "\n");
               
                }
         
            }
        
            in.close();
        }
        
    }
}

class ClientThread extends Thread {
    public double start, end, total;
    public String command, host;
    public int port = 8080;
    
    public ClientThread(String com, String hos) {
        command = com;
        host = hos;
        total = 0;
        end = 0;
        start = 0;
    }
    
    public void execute() {
        
    	try {
    		String line = null;
            
            Socket connection = new Socket("localhost", port);
            PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
        
    }
}

class Menu {
    
  public Menu() {
    
  }
    public String getCommand(String input) {
        
        String command = " ";
        
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
            command = "users";
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
            System.err.println ( "Invalid option\n" );
            break;
    }
      return command;
    }
    
    public void display_menu() {
        System.out.println("The menu provides the following choices to the user: ");
                        System.out.println("1. Host current Date and Time \n2. Host uptime\n"
                                        + "3. Host memory use \n4. Host Netstat \n5. Host current users "
                                        + "\n6. Host running processes \n7. Quit ");
                        System.out.print("Please select an action: ");
 }
}

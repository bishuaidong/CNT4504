/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.net.*;
import java.util.*;
/**
 *
 * @author Bishuai
 * @author Varsha
 * @author Rishav
 *
 */
public class Client {
    public static void main(String[] args) {
        /*
        Create socket object. Two parameters are passed, the first is the IP 
        of the server and the second is the port number
        */
        try {
            System.out.println("Attempting to connect Client");
            Socket connection = new Socket("localhost",8080);
        }
        catch (Exception e) {
            System.out.println("Connection unsuccessful");
        }
        Scanner in = new Scanner ( System.in );
        Menu menu = new Menu();
        String command;
        boolean running = true;
        
        while(running) {
            menu.display_menu();
            command = menu.getCommand(in.nextInt());
            
            if(command.equals("exit")) {
                System.out.println("Goodbye");
                running = false;
            }
            
            else {
                System.out.println("running ", command);
            }
        }
        
        
    }
}

class Menu {
    
  public Menu() {
    
  }
    public String getCommand(String input) {
        
        String command;
        
        switch (input) {
        case 1:
            System.out.println ( "current Date and Time" );
            command = "date";
            break;
        case 2:
            System.out.println ( "uptime" );
            command = "uptime";
            break;
        case 3:
            System.out.println ( "memory use" );
            command = "free";
            break;
        case 4:
            System.out.println ( "Netstat" );
            command = "netstat";
            break;
        case 5:
            System.out.println ( "current users" );
            command = "users";
            break;
        case 6:
            System.out.println ( "running processes" );
            command = "ps aux";
            break;
        case 7:
            System.out.println("Quitting.");
            command = "exit";
            break;
        default:
            System.err.println ( "Invalid option." );
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

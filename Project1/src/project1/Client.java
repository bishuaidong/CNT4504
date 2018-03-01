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
    }
}

class Menu {
  public void display_menu() {
 System.out.println("The menu provides the following choices to the user: ");
                        System.out.println("1. Host current Date and Time \n2. Host uptime\n"
                                        + "3. Host memory use \n4. Host Netstat \n5. Host current users "
                                        + "\n6. Host running processes \n7. Quit ");
                        System.out.print("Please select an action: ");
 }

  public Menu() {
    Scanner in = new Scanner ( System.in );

    display_menu();
    switch ( in.nextInt() ) {
      case 1:
        System.out.println ( "current Date and Time" );
        break;
      case 2:
        System.out.println ( "uptime" );
        break;
      case 3:
        System.out.println ( "memory use" );
        break;

      case 4:
        System.out.println ( "Netstat" );
        break;
      case 5:
        System.out.println ( "current users" );
        break;
      case 6:
        System.out.println ( "running processes" );
        break;
      case 7:
        System.out.println("Quitting.");
        System.exit(0);

      default:
        System.err.println ( "Invalid option." );
        break;
    }
  }

  public static void main ( String[] args ) {
    new Menu();
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.net.*;

/**
 *
 * @author Bishuai
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

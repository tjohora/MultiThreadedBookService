/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author D00186562
 */
public class Client {

    public static void displayMenu() {
        System.out.println("-----------------------------------------------------");
        System.out.println("Please choose one of the following options: ");
        System.out.println("1) Find book by Title");
        System.out.println("2) Find book by Author");
        System.out.println("3) Find book by first line");
        System.out.println("4) Add entry");
        System.out.println("5) Remove Entry");
        System.out.println("Enter 9 if you wish to end the program.");
        System.out.println("-----------------------------------------------------");
    }

    public static int getChoice(Scanner input) {
        int choice = 0;
        boolean validNumber = false;
        while (!validNumber) {
            try {
                choice = input.nextInt();
                validNumber = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number to choose a menu option!");
                System.out.println();
                input.nextLine();
                displayMenu();
            }
        }
        return choice;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final int SERVER_PORT = 23456;
        try {
            Socket mySocket = new Socket("localhost", SERVER_PORT);
            Scanner networkIn = new Scanner(mySocket.getInputStream());
            PrintWriter networkOut = new PrintWriter(mySocket.getOutputStream(), true);

            String message = "";

            int choice = -1;
            while (choice != 9) {
                displayMenu();
                choice = getChoice(input);
                switch (choice) {
                    case 1:
                        input.nextLine();
                        System.out.println("Please enter the title of the book: ");
                        message = "SEARCH%%BYTITLE%%" + input.nextLine();
                        break;
                        
                    case 2:
                        input.nextLine();
                        System.out.println("Please enter the author of the book: ");
                        message = "SEARCHAUTHOR%%" + input.nextLine();
                        break;
                        
                    case 3:
                        input.nextLine();
                        System.out.println("Please enter the first line of the book: ");
                        message = "SEARCH%%BYFIRSTLINE%%" + input.nextLine();
                        break;
                        
                    case 4:
                        input.nextLine();
                        System.out.println("New book entry.");
                        System.out.println("Please enter book name: ");
                        String title = input.nextLine();
                        System.out.println("Please enter book author: ");
                        String author = input.nextLine();
                        System.out.println("Please enter first line of book:");
                        String firstLine = input.nextLine();
                        message = "ADD%%" + title + "%%" + author + "%%" +firstLine;
                        break;
                        
                    case 5:
                        input.nextLine();
                        System.out.println("Delete book entry.");
                        System.out.println("Please enter book title:");
                        title = input.nextLine();
                        System.out.println("Please enter book title:");
                        author = input.nextLine();
                        System.out.println("Please enter book title:");
                        firstLine = input.nextLine();
                        message = "REMOVE%%" + title + "%%" + author + "%%" + firstLine;
                        break;
                        
                    case 9:
                        message = "EXIT";
                        System.out.println("Thanks for using book club service");
                        break;    
                }
                
                if (choice != -1){
                    networkOut.println(message);
                    String response = networkIn.nextLine();
                    String[] components = response.split("%%");
                    switch (choice){
                        case 1:
                            if (components.length == 3){
                                System.out.println("Book found! Title: " + components[0] + ", Author: " + components[1] +", First line: " + components[2] +"." );
                            }else if(response.equals("NO_BOOK_FOUND")){
                                System.out.println("No books with that title have been found.");
                            }else{
                                System.out.println("There was a problem with this action, please try again later");
                            }
                            break;
                            
                        case 2:
                            String[] components2 = message.split("__");
                            if (components.length >= 3){
//                                System.out.println("Book(s) found!");
//                                TODO
//                                for(int i=0;i< ;i++){
//                                    System.out.println("Book found! Title: " + components[0] + ", Author: " + components[1] +", First line: " + components[2] +"." );
//                                }
                            }else if(response.equals("NO_BOOKS_FOUND")){
                                System.out.println("No books with that author have been found.");
                            }else{
                                System.out.println("There was a problem with this action, please try again later");
                            }
                            
                            break;
                            
                        case 3:
                            if (components.length == 3){
                                System.out.println("Book found! Title: " + components[0] + ", Author: " + components[1] +", First line: " + components[2] +"." );
                            }else if(response.equals("NO_BOOK_FOUND")){
                                System.out.println("No books with that first line have been found.");
                            }else{
                                System.out.println("There was a problem with this action, please try again later");
                            }
                            break;
                            
                        case 4:
                            if (response.equals("ADDED")){
                                System.out.println("Book successfully added.");
                            }else if(response.equals("BOOK_ALREADY_EXISTS")){
                                System.out.println("Book with those details is already present. Action cancelled.");
                            }else{
                                System.out.println("There was a problem with this action, please try again later");
                            }
                            break;
                            
                        case 5:
                            if (response.equals("REMOVED")){
                                System.out.println("Book successfully removed.");
                            }else if(response.equals("NO_BOOKS_FOUND")){
                                System.out.println("No book with those details were found");
                            }else{
                                System.out.println("There was a problem with this action, please try again later");
                            }
                            break;
                            
                        case 9:
                            if (response.equals("GOODBYE")){
                                System.out.println("Server shutting down. See you later!");
                            }else{
                                System.out.println("Problem with server shut down.");
                            }
                            break;                    
                    }                    
                }
            }
            System.out.println("Communication with the server will now cease.");
            mySocket.close();
        } catch (UnknownHostException ex) {
            System.out.println("A problem occurred when attempting to look up host.");
            System.out.println(ex.getMessage());
        } catch (SocketException ex) {
            System.out.println("A problem occurred when connecting to the server on port " + SERVER_PORT);
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("A problem occurred when sending a packet.");
            System.out.println(ex.getMessage());
        }
    }

}

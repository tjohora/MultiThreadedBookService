/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import business.Book;
import business.BookList;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author D00186562
 */
public class bookClubRunnable implements Runnable{
    private final Socket clientLink;
    private final BookList bookList;
    
    public bookClubRunnable(Socket client, BookList bl){
        this.clientLink = client;
        this.bookList = bl;
    }

    @Override
    public void run() {
        try{
            Scanner input = new Scanner(clientLink.getInputStream());
            PrintWriter output = new PrintWriter(clientLink.getOutputStream());
            
            InetAddress response = clientLink.getInetAddress();
            int responsePort = clientLink.getPort();
            String message = "";
            while (!message.equals("EXIT")) {
                message = input.nextLine();
                
                String responseMsg = null;
                
                System.out.println("Message received: " + message + ", from " + response + " on port " + responsePort);
                
                String[] components = message.split("%%");
                switch (components[0]) {
                    case "SEARCH":
                        Book book = null;
                        if(components.length == 3){
                            if(components[1].equalsIgnoreCase("BYTITLE")){
                                book = bookList.getBookByTitle(components[2]);
                            }else if(components[1].equalsIgnoreCase("BYFIRSTLINE")){
                                book = bookList.getBookByFirstLine(components[2]);
                            }else{
                                responseMsg = "ERROR";
                            }                            
                            if(book!=null){
                                responseMsg = book.getTitle() + "%%" + book.getAuthor() + "%%" + book.getFirstLine();
                            }else{
                                responseMsg = "NO_BOOK_FOUND";
                            }
                        }else{
                            responseMsg = "INVALID";
                        }
                        break;
                        
                    case "SEARCHAUTHOR":
                        if(components.length == 2){
                            ArrayList<Book> books = bookList.getBooksByAuthor(components[1]);
                            if(books.isEmpty()){
                                responseMsg = "NO_BOOKS_FOUND";
                            }else{
                                for (Book book1 : books) {
                                    responseMsg += book1.getTitle() + "%%" + book1.getAuthor() + "%%" + book1.getFirstLine() + "%%";
                                }
                            }
                        }else{
                            responseMsg = "INVALID";
                        }
                        break;  
                        
                    case "ADD":
                        
                        if(components.length == 4){
                            boolean added = bookList.addBook(components[1], components[2], components[3]);
                            if(added){
                                responseMsg = "ADDED";
                            }else{
                                responseMsg = "BOOK_ALREADY_EXISTS";
                            }
                        }else{
                            responseMsg = "INVALID";
                        }
                        break;    
                        
                    case "REMOVE":
                        if(components.length == 4){
                            boolean removed = bookList.removeBook(components[1], components[2], components[3]);
                            if(removed){
                                responseMsg = "REMOVED";
                            }else{
                                responseMsg = "NO_BOOKS_FOUND";
                            }
                        }else{
                            responseMsg = "INVALID";
                        }
                        break;
                    
                    case "EXIT":
                        responseMsg = "GOODBYE";
                        break;     
                    
                    default:
                        responseMsg = "INVALID";
                        break;    
                }
                System.out.println("Sent to client: " + responseMsg);
                output.println(responseMsg);
                output.flush();
            }
            clientLink.close();
        }catch (IOException ex) {
            Logger.getLogger(bookClubRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

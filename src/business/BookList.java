/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.ArrayList;

/**
 *
 * @author D00186562
 */
public class BookList {
    private ArrayList<Book> bookList = new ArrayList();
    
    //bootstrap info
    
    public Book getBookByTitle(String title){
        for (Book book : bookList) {
            if(book.getTitle().equalsIgnoreCase(title)){
                return book;
            }
        }
        return null;
    }
    
    public ArrayList<Book> getBooksByAuthor(String author){
        ArrayList<Book> matches = new ArrayList();
        for(Book book : bookList){
            if(book.getAuthor().equalsIgnoreCase(author)){
                matches.add(book);
            }
        }
        return matches;
    }
    
    public Book getBookByFirstLine(String firstLine){
        for (Book book : bookList) {
            if(book.getFirstLine().equalsIgnoreCase(firstLine)){
                return book;
            }
        }
        return null;
    }
    
    public boolean addBook(String title, String author, String firstLine){
        Book book = new Book(title, author, firstLine);
        
        if(bookList.contains(book)){
            return false;
        }else{
            bookList.add(book);
            return true;
        }
    }
    
    public boolean removeBook(String title, String author, String firstLine){
        Book book = new Book(title, author, firstLine);
        
        if(bookList.contains(book)){
            bookList.remove(book);
            return true;
        }else{
            return false;
        }
    }
}

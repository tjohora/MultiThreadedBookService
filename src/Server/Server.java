package Server;

import business.BookList;
import business.Book;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author D00186562
 */
public class Server {
    public static void main(String[] args) {
        final int MY_PORT = 23456;
        boolean continueRunning = true;
        
        BookList bookList = new BookList();
        
        try{
            ServerSocket connectionSocket = new ServerSocket(MY_PORT);
            System.out.println("Now ready to accept requests.");
            
            while (continueRunning == true){
                Socket clientLink = connectionSocket.accept();
                bookClubRunnable newClient = new bookClubRunnable(clientLink, bookList);
                Thread clientWrapper = new Thread(newClient);
                clientWrapper.start();
            }
            connectionSocket.close();
        }catch (SocketException ex){        
            System.out.println("A problem occurred when creating the socket on port " + MY_PORT);
            System.out.println(ex.getMessage());
        }catch (IOException ex){        
            System.out.println("A problem occurred when reading in a message from the stream.");
            System.out.println(ex.getMessage());
        }
    }
}

import java.io.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {

  ServerSocket server;
  Socket socket;
  BufferedReader br; // for reading streams/Strings
  PrintWriter out; // for writing streams/Strings

  //Constructor....
  public Server() {
    try {
      server = new ServerSocket(7777);
      System.out.println("Server is ready to accept connections...");
      System.out.println("Waiting for connection...");
      socket = server.accept(); // it will accept the object from the client side .i.e socket

      br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // it establishes the connection between the server and the client to read the sms from client
      out = new PrintWriter(socket.getOutputStream()); // it establishes the connection to send the sms to the client through the server output stream funct:- getoutputstream...

      startReading();
      startWriting();
    } catch (Exception e) { //end of try_block()
      e.printStackTrace();
    } //end of catch_block()
  } // end of constructor_ #Server

  // funtion to read
  public void startReading() {
    // thread-read karke  deta rhega
    // runnable its a thread it reads the sms..
    Runnable r1 = () -> {
      System.out.println("Reader Started...");
      while (true) {
        try {
          String msg = br.readLine();
          if (msg.equals("exit")) {
            System.out.println("Client disconnected the Chat !!");

            break;
          } //if_block
          System.out.println("Client: " + msg);
        } catch (Exception e) {
          e.printStackTrace();
        } //end of catch_block()
      } //while_block
    }; // thread _ implementation
    // starting thread
    new Thread(r1).start();
  } //end of reading_method

  // function to write
  public void startWriting() {
    // thread - data user se lega and send karega client takk
    Runnable r2 = () -> {
      System.out.println("Writer Started...");
      while (true) {
        try {
          BufferedReader br1 = new BufferedReader(
            new InputStreamReader(System.in)
          );

          String content = br1.readLine();
          out.println(content);
          out.flush();
        } catch (Exception e) {
          e.printStackTrace();
        } //end of catch_block()
      } //end of while_block
    }; // thread_implementation
    // starting thread
    new Thread(r2).start();
  } //end of writing_method()

  public static void main(String[] args) {
    System.out.println("This is Server.Going to start the server....");
    new Server();
  } // end of main method
} // end of class Server

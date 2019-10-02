package dk.kea.stud.chris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatAppServer {

  public static void main(String[] args) {
    boolean reInit = true;
    Scanner scn = new Scanner(System.in);

    while (reInit) {
      Socket connection = initialize(args);
      if (connection != null) {
        System.out.println("Client connected.");
        try {
          PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          Protocol protocol = new Protocol();
          while (protocol.getState() != ProtocolState.GOODBYE) {
            out.println(protocol.parse(in.readLine()));
          }
          out.close();
          in.close();
          connection.close();
        } catch (IOException e) {
          System.out.println("Error initializing IO streams.");
        }
      }

      System.out.print("Connection closed. Listen for another connection? (y for yes, anything else to quit): ");
      reInit = scn.nextLine().toLowerCase().startsWith("y");
    }
  }

  private static Socket initialize(String[] args) {
    Socket result = null;
    int port;
    if (args[0] == null) {
      System.out.println("Usage:");
      System.out.println("ChatAppServer <port> - Start the application in server mode");
    } else {
      try {
        port = Integer.parseInt(args[0]);
        if (port < 0 || port > 65535) {
          throw new NumberFormatException();
        }
        try {
          ServerSocket server = new ServerSocket(port);
          System.out.println("Waiting for a client...");
          result = server.accept();
        } catch (IOException e) {
          System.out.println("Could not bind to port " + port + ". It may be in use by another app.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid port number.");
      }
    }
    return result;
  }
}
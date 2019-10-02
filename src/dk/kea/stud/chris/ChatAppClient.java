package dk.kea.stud.chris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatAppClient {

  public static void main(String[] args) {
    Socket connection = initialize(args);
    if (connection != null) {
      System.out.println("Successfully connected.");
      try {
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Scanner scn = new Scanner(System.in);
        String reply = null;
        System.out.println("Type your messages at the prompt below.");
        do {
          System.out.print(": ");
          out.println(scn.nextLine());
          reply = in.readLine();
          System.out.println("Reply: " + reply);
        } while (!reply.equals("GOODBYE"));
      } catch (IOException e) {
        System.out.println("Error initializing IO streams.");
      }
    }
  }

  private static Socket initialize(String[] args) {
    Socket result = null;
    int port;
    if (args[0] == null || args[1] == null) {
      System.out.println("Usage:");
      System.out.println("ChatAppClient <host> <port> - Start the application in client mode");
    } else {
      try {
        port = Integer.parseInt(args[1]);
        if (port < 0 || port > 65535) {
          throw new NumberFormatException();
        }
        try {
          result = new Socket(args[0], port);
        } catch (UnknownHostException e) {
          System.out.println("Could not find host.");
        } catch (IOException e) {
          System.out.println("Error establishing connection.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid port number.");
      }
    }
    return result;
  }
}
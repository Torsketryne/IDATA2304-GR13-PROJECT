package idata2304.group13.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    System.out.println("a new thread was ran with ClientHandler");
  }
}

package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable {

  public ServerSocket serverSocket;
  private ConnectionServer connectionServer;
  private Vector<Thread> threads = new Vector<Thread>();

  public static void main(String args[]) {
    Seeds.seed();
    SocketCommunication client = new SocketCommunication();
    Server server = new Server(3000, client);
    server.start();
  }

  public Server(int _port, ConnectionServer connectionServer) {
    this.connectionServer = connectionServer;
    try {
      serverSocket = new ServerSocket(_port);
    } catch (IOException e) {
      System.out.println("Could not listen to port: " + _port);
      System.exit(-1);
    }
  }

  public void start() {
    new Thread(this).start();
    System.out.println("Server started on port 3000\n");
  }

  public void run() {
    while (true) {
      try {
        Socket clientSocket = serverSocket.accept();
        serveConnection(clientSocket);
      } catch (IOException e) {
        System.out.println("Could not connect to server.");
        e.printStackTrace();
      }
    }
  }

  private void serveConnection(Socket clientSocket) {
    Thread thread = startThread(clientSocket);
    System.out.println(threads.size() + ": Client connected to server\n");
    killThread(thread);
  }

  private synchronized Thread startThread(Socket clientSocket) {
    Thread thread = new Thread(new ConnectionServerDriver(clientSocket));
    threads.add(thread);
    thread.start();
    return thread;
  }

  private synchronized void killThread(Thread thread) {
    threads.remove(thread);
    thread.interrupt();
    thread = null;
  }

  private synchronized void killLastThread() {
    System.out.println(threads.size() + " threads");
//    threads.remove().interrupt();
    System.out.println(threads.size() + " threads");
  }

  private class ConnectionServerDriver implements Runnable {
    private Socket clientSocket;

    public ConnectionServerDriver(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    public void run() {
      serve();
      close();
    }

    private synchronized void serve() {
      try {
        connectionServer.serve(clientSocket);
      } catch(IOException ioe) {
        System.out.println("IOException when writing/reading client connection");
      }
    }

    private synchronized void close() {
      try {
        connectionServer.close(clientSocket);
      } catch (IOException ioe) {
        System.out.println("IOException when closing client connection");
      }
    }
  }
}

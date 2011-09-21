package HTTPServer;

import HTTPServer.Logger.Logger;
import HTTPServer.Logger.SOLogger;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Server implements Runnable {

  public ServerSocket serverSocket;
  private ConnectionServer connectionServer;
  private Logger logger;
  private Vector<Thread> threads = new Vector<Thread>();
  private boolean running = false;

  public static void main(String args[]) {
    Seeds.seed();
    SocketCommunication client = new SocketCommunication();
    Server server = new Server(3000, client);
    server.start();
  }

  public Server(int _port, ConnectionServer connectionServer) {
    this(_port, connectionServer, new SOLogger());
  }

  public Server(int _port, ConnectionServer connectionServer, Logger _logger) {
    this.connectionServer = connectionServer;
    this.logger = _logger;
    try {
      serverSocket = new ServerSocket(_port);
    } catch (IOException e) {
      this.logger.log("ERROR!! Could not listen to port: " + _port);
      System.exit(-1);
    }
  }

  public void start() {
    new Thread(this).start();
    logger.log("Server started on port " + serverSocket.getLocalPort() + "\n");
  }

  public void run() {
    running = true;
    while (running) {
      try {
        Socket clientSocket = serverSocket.accept();
        serveConnection(clientSocket);
      } catch (IOException e) {
        String stackTrace = "";
        for(StackTraceElement ste : e.getStackTrace()) {
          stackTrace += ste.toString() + "\n";
        }
        logger.log("ERROR!! Could not connect to server.\n" + stackTrace);
      }
    }
  }

  public void closeServerSocket() {
    try {
      logger.log("Stopping the server...\n");
      running = false;
      serverSocket.close();
    } catch(IOException ioe) {
      logger.log("ERROR!! Cannot close the server.");
    }
  }

  private void serveConnection(Socket clientSocket) {
    Thread thread = startThread(clientSocket);
    logger.log(threads.size() + ": Client connected to server\n");
    killThread(thread);
  }

  private Thread startThread(Socket clientSocket) {
    Thread thread = new Thread(new ConnectionServerDriver(clientSocket));
    threads.add(thread);
    thread.start();
    return thread;
  }

  private void killThread(Thread thread) {
    threads.remove(thread);
    thread.interrupt();
    thread = null;
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
        logger.log("ERROR!! IOException when writing/reading client connection");
      }
    }

    private synchronized void close() {
      try {
        connectionServer.close(clientSocket);
      } catch (IOException ioe) {
        logger.log("ERROR!! IOException when closing client connection");
      }
    }
  }
}

package specs.HTTPServer;

import HTTPServer.ConnectionServer;
import HTTPServer.Logger.MemLogger;
import HTTPServer.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
  private int serverPort = 9999;
  private TestServer testServer;
  private Server server;
  private MemLogger logger;

  @Before
  public void setUp() {
    testServer = new TestServer();
    logger = new MemLogger();
    server = new Server(serverPort, testServer, logger);
    waitForConnection();
    server.start();
  }

  @After
  public void tearDown() {
    server.closeServerSocket();
  }

  @Test
  public void testDefaultPort() {
    assertEquals(9999, server.serverSocket.getLocalPort());
  }

  @Test
  public void givenAConnectedServer_canSendString() throws Exception {
    Socket clientSocket = new Socket();
    connectClient(clientSocket);

    OutputStream outputStream = clientSocket.getOutputStream();
    PrintStream ps = new PrintStream(outputStream);
    ps.println("hello");
    assertEquals("hello", testServer.getLastMessage());
  }

  @Test
  public void givenAConnectedServer_canSendMultipleStrings() throws Exception {
    String[] messages = {"hello", "goodbye"};
    for(String message : messages){
      Socket clientSocket = new Socket();
      connectClient(clientSocket);
      OutputStream outputStream = clientSocket.getOutputStream();
      PrintStream ps = new PrintStream(outputStream);
      ps.println(message);
      assertEquals(message, testServer.getLastMessage());
    }
  }

  @Test
  public void connectingToServerLogsTheEvent() {
    assertEquals("Server started on port 9999\n", logger.getLog());
  }

  @Test
  public void logsErrorWhenClientCannotConnectToServer() {
    server.closeServerSocket();
    Socket client = new Socket();
    connectClient(client);
    assertTrue(logger.getLog().startsWith("ERROR!! Could not connect to server."));
  }

  @Test
  public void logsAmountOfClientsConnectedWhenAClientConnects() {
    Socket client = new Socket();
    connectClient(client);
    assertEquals("1: Client connected to server\n", logger.getLog());
  }


  private void connectClient(Socket clientSocket) {
    try {
      InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
      clientSocket.connect(serverAddress);
    } catch(IOException ioe) {
    }
    waitForConnection();
  }


  private void waitForConnection() {
    try {
      Thread.sleep(10);
    } catch(InterruptedException ie) {
      System.out.println("Can't sleep.");
    }
  }


  private class TestServer implements ConnectionServer {

    private String lastMessage;

    public synchronized void serve(Socket connection) {
      BufferedReader br;
      try {
        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        br = new BufferedReader(isr);
        lastMessage = br.readLine();
        close(connection);
      } catch (Exception e) {
        System.out.println("connection server error");
      }
    }

    public synchronized void close(Socket connection) {
      try{
        connection.close();
      } catch(IOException ioe) {
        System.out.println("Cannot close socket...");
      }
    }

    public synchronized String getLastMessage() throws Exception {
      return lastMessage;
    }
  }
}

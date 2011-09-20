package specs.HTTPServer;

import HTTPServer.Logger.MemLogger;
import HTTPServer.Server;
import HTTPServer.SocketCommunication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketCommunicationTest {

  SocketCommunication client;
  Socket socket;
  Server server;
  MemLogger serverLogger;
  MemLogger logger;
  PrintStream ps;
  BufferedReader br;

  @Before
  public void setUp() throws Exception {
    logger = new MemLogger();
    client = new SocketCommunication(logger);
    socket = new Socket();
    serverLogger = new MemLogger();
    server = new Server(9999, client, serverLogger);
    server.start();
    waitToSend();
    connectClient(9999);
    ps = new PrintStream(socket.getOutputStream());
    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  @After
  public void tearDown() {
    server.closeServerSocket();
  }

  @Test
  public void serveSendsHTTPResponse() throws Exception {
    printLine("GET /blah HTTP/1.0");
    assertEquals("HTTP/1.0 404 Not Found", readLine());
  }

  @Test
  public void givenRequest_loggerContainsRequestWithTypeAndResponseCode() throws Exception {
    printLine("GET /blah HTTP/1.0");
    waitToSend();
    assertEquals("GET request for \"/blah\" of type text/html\napplication responds with status 404 Not Found\n", logger.getLog());
  }


  private synchronized String readLine() throws Exception {
    return br.readLine();
  }

  private synchronized void printLine(String request) {
    ps.println(request);
  }

  private synchronized void connectClient(int serverPort) throws Exception {
    InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
    socket.connect(serverAddress);
    waitToSend();
  }

  private void waitToSend() throws Exception {
    Thread.sleep(10);
  }

}

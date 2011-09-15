package HTTPServer;

import Handlers.Handler;

import java.io.*;
import java.net.Socket;

public class SocketCommunication implements ConnectionServer {

  public final String OK = "200 OK";
  public final String NOT_FOUND = "404 Not Found";
  public final String ERROR = "500 Internal Server Error";

  public synchronized void serve(Socket connection) throws IOException {
    DataOutputStream os = new DataOutputStream(connection.getOutputStream());
    String[] request = request(connection.getInputStream());
    System.out.println("***********" + (request[0] == null));
    if(request[0] != null) {
      System.out.println(request[0] + " request for \"" + request[1] + "\" of type " + contentType(request[1]));
      String output = "HTTP/1.0 " + status(request[1]) + "\n" +
                    "Content-Type: "+ contentType(request[1]) +"\n\n";
      output += serverResponse(request);
      os.writeBytes(output);
      System.out.println("application responds with status " + status(request[1]) + "\n");
    }
  }

  public synchronized void close(Socket connection) throws IOException {
    connection.close();
  }

  private synchronized String contentType(String URI) {
    if(URI.endsWith(".css"))
      return "text/css";
    else if(URI.endsWith(".js"))
      return "text/javascript";
    else
      return "text/html";
  }

  private synchronized String status(String URI){
    try{
      BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
      String line = br.readLine();
      while(line != null){
        String route = line.split("\\s*->\\s*")[0];
        if(!route.isEmpty() && !route.startsWith("///") && URI.matches(route))
          return OK;
        line = br.readLine();
      }
      return NOT_FOUND;
    } catch(IOException ioe) {
      return ERROR;
    }
  }

  private synchronized String[] request(InputStream inputStream) {
    String[] requestElements = new String[3];
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      String line = br.readLine();
      if(line != null)
        requestElements = line.split(" ");
    } catch (IOException ioe) {
      System.out.println("IOException when reading the request.");
    }
    return requestElements;
  }

  private synchronized String serverResponse(String[] request) {
    BufferedReader content = Handler.execute(request[1]);
    String output = "";
    try {
      String line = content.readLine();
      while(line != null) {
        output += line + "\n";
        line = content.readLine();
      }
    } catch (IOException ioe) {
      System.out.println("IOException when reading output.");
    }
    return output;
  }

}

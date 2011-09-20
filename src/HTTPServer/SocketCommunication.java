package HTTPServer;

import HTTPServer.Logger.Logger;
import HTTPServer.Logger.SOLogger;
import Handlers.Handler;

import java.io.*;
import java.net.Socket;

public class SocketCommunication implements ConnectionServer {

  public Logger logger;
  public final String OK = "200 OK";
  public final String NOT_FOUND = "404 Not Found";
  public final String ERROR = "500 Internal Server Error";

  public SocketCommunication() {
    this.logger = new SOLogger();
  }

  public SocketCommunication(Logger _logger) {
    this.logger = _logger;
  }

  public void serve(Socket connection) throws IOException {
    DataOutputStream os = new DataOutputStream(connection.getOutputStream());
    String[] request = request(connection.getInputStream());
    if(!request[0].isEmpty()) {
      logger.log(request[0] + " request for \"" + request[1] + "\" of type " + contentType(request[1]) + "\n" +
                  "application responds with status " + status(request[1]) + "\n");
      String output = "HTTP/1.0 " + status(request[1]) + "\n" +
                    "Content-Type: "+ contentType(request[1]) +"\n\n";
      output += serverResponse(request);
      os.writeBytes(output);
    }
  }

  public void close(Socket connection) throws IOException {
    connection.close();
  }

  private String contentType(String URI) {
    if(URI.endsWith(".css"))
      return "text/css";
    else if(URI.endsWith(".js"))
      return "text/javascript";
    else
      return "text/html";
  }

  private String status(String URI){
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

  private String[] request(InputStream inputStream) {
    String[] requestElements = {"", "", ""};
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      String line = br.readLine();
      if(line != null)
        requestElements = line.split(" ");
    } catch (IOException ioe) {
      logger.log("ERROR!! IOException when reading the request.");
    }
    return requestElements;
  }

  private String serverResponse(String[] request) {
    Handler handler = new Handler();
    BufferedReader content = handler.execute(request[1]);
    String output = "";
    try {
      String line = content.readLine();
      while(line != null) {
        output += line + "\n";
        line = content.readLine();
      }
    } catch (IOException ioe) {
      logger.log("ERROR!! IOException when reading output.");
    }
    return output;
  }

}

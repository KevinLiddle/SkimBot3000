package HTTPServer;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionServer {
  void serve(Socket connection) throws IOException;

  void close(Socket clientSocket) throws IOException;
}

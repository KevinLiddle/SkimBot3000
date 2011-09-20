package HTTPServer.Logger;

public class MemLogger implements Logger {

  private String log = "";

  public void log(String message) {
    log = message;
  }

  public String getLog() {
    return log;
  }
}

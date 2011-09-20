package specs.HTTPServer;

import HTTPServer.Logger.MemLogger;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LoggerTest {

  @Test
  public void logCreatesLogWithGivenMessage() {
    MemLogger logger = new MemLogger();
    String message = "What it be? What the business is?";
    logger.log(message);
    assertEquals(message, logger.getLog());
  }


}

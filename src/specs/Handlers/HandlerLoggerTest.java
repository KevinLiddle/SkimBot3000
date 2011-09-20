package specs.Handlers;

import HTTPServer.Logger.MemLogger;
import Handlers.Handler;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HandlerLoggerTest {

  MemLogger logger = new MemLogger();
  Handler handler = new Handler(logger);

  @Test
  public void pathNotFoundErrorGetsLogged() {
    handler.execute("/blah");
    assertEquals("ERROR!! No route matches \"/blah\"", logger.getLog());
  }

  @Test
  public void renderFileWithAFileThatDoesNotExistLogsError() {
    handler.renderFile("blah");
    assertEquals("ERROR!! Cannot find \"src/views/blah/\"", logger.getLog());
  }

  @Test
  public void tryingToCallHandlerThatDoesntExistLogsError() throws Exception {
    handler.callMethod("BlahHandler#what", "yay");
    assertEquals("ERROR!! Class: \"Handlers.BlahHandler\" not found.", logger.getLog());
  }

  @Test
  public void unknownMethodLogsError() throws Exception {
    handler.callMethod("HomeHandler#blah", "woohoo");
    assertEquals("ERROR!! Method: \"blah\" not found.", logger.getLog());
  }


}

package specs.Handlers;

import HTTPServer.Logger.MemLogger;
import Handlers.Handler;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static specs.Handlers.HandlerTestHelpers.page;

public class HomeHandlerSpec {

  Handler handler = new Handler(new MemLogger());

  @Test
  public void requestForHomePageReturnsBRForHomePage() throws Exception {
    assertTrue(page(handler.execute("/"), "SkimBot 3000"));
  }

}

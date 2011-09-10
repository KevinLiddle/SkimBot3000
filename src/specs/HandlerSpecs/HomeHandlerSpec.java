package specs.HandlerSpecs;

import Handlers.Handler;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static specs.HandlerSpecs.HandlerTestHelpers.page;

public class HomeHandlerSpec {

  @Test
  public void requestForHomePageReturnsBRForHomePage() throws Exception {
    assertTrue(page(Handler.execute("/"), "SkimBot 3000"));
  }

}

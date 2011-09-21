package Tests.Handlers;

import HTTPServer.Database;
import HTTPServer.Logger.MemLogger;
import HTTPServer.Seeds;
import Handlers.Handler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static Tests.Handlers.HandlerTestHelpers.*;

public class QuestionHandlerTest {

  Handler handler;

  @Before
  public void setUp() {
    Seeds.seed();
    handler = new Handler(new MemLogger());
  }

  @After
  public void tearDown() {
    Database.table().clear();
  }

  @Test
  public void requestForTheFirstQuestionCallsNextQuestion() throws Exception {
    assertTrue(mockExecute("/question/0"));
  }

  @Test
  public void requestForTheFirstQuestionRendersFirstQuestion() throws Exception {
    assertTrue(page(handler.execute("/question/0"), "question"));
  }

  @Test
  public void answeringQuestionIncorrectlyDispleasesSkimBot3000() throws Exception {
    assertTrue(page(handler.execute("/question/0/answer/1"), "sigh"));
  }

  @Test
  public void answeringCorrectlySatisfiesSkim() throws Exception {
    assertTrue(page(handler.execute("/question/0/answer/0"), "yes"));
  }

  @Test
  public void requestingNextQuestionRendersNextQuestion() throws Exception {
    assertTrue(page(handler.execute("/question/0"), "What is your name?"));
  }

  @Test
  public void requestForEndCallsEnd() throws Exception {
    assertTrue(mockExecute("/end"));
  }

  @Test
  public void requestForEndShowsOverallResults() throws Exception {
    assertTrue(page(handler.execute("/end"), "sigh"));
  }


}

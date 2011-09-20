package specs.Models;

import Models.Question;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class QuestionSpec {

  String prompt;
  String[] responses = new String[4];
  int answerIndex;
  Question question;

  @Before
  public void setUp() {
    prompt = "What is your name?";
    responses[0] = "Kevin";
    responses[1] = "Nivek";
    responses[2] = "Skim";
    responses[3] = "Whole";
    answerIndex = 0;
    question = new Question(prompt, responses, answerIndex);
  }

  @Test
  public void questionInitializesCorrectly() {
    assertEquals(prompt, question.prompt);
  }

  @Test
  public void checkAnswerReturnsTrueIfChoiceIsCorrect() {
    assertTrue(question.checkAnswer(0));
  }

  @Test
  public void checkAnswerReturnsFalseForWrongAnswer() {
    assertFalse(question.checkAnswer(2));
  }


}

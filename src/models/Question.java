package models;

public class Question {

  public String prompt;
  public String[] responses;
  private int answerIndex;

  public Question(String _prompt, String[] _responses, int _answerIndex) {
    prompt = _prompt;
    responses = _responses;
    answerIndex = _answerIndex;
  }

  public boolean checkAnswer(int answerID) {
    return answerID == answerIndex;
  }
}

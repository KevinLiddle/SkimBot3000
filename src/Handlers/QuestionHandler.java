package Handlers;

import HTTPServer.Database;
import Models.Question;
import views.QuestionHTML;
import views.Results;

import java.io.BufferedReader;
import java.util.ArrayList;

public class QuestionHandler extends Handler {

  public BufferedReader nextQuestion(String request) {
    ArrayList<Integer> ids = getIDs(request);
    Question question = (Question) Database.table().get(ids.get(0));
    return renderHTMLString(QuestionHTML.render(question, ids.get(0)));
  }

  public BufferedReader results(String request) {
    ArrayList<Integer> ids = getIDs(request);
    Question question = (Question) Database.table().get(ids.get(0));
    boolean correct = question.checkAnswer(ids.get(1));
    return renderHTMLString(Results.render(ids.get(0) + 1, correct));
  }

  public BufferedReader end(String request) {
    return renderFile("Disappointed.html");
  }
}

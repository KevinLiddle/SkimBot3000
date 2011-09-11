package Handlers;

import HTTPServer.Database;
import models.Question;
import views.QuestionHTML;
import views.Results;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class QuestionHandler extends Handler {

  public static BufferedReader nextQuestion(String request) throws Exception {
    ArrayList<Integer> ids = Handler.getIDs(request);
    Question question = (Question) Database.table().get(ids.get(0));
    return new BufferedReader(new StringReader(QuestionHTML.render(question, ids.get(0))));
  }

  public static BufferedReader results(String request) throws Exception {
    ArrayList<Integer> ids = Handler.getIDs(request);
    Question question = (Question) Database.table().get(ids.get(0));
    boolean correct = question.checkAnswer(ids.get(1));
    return new BufferedReader(new StringReader(Results.render(ids.get(0) + 1, correct)));
  }

  public static BufferedReader end(String request) throws Exception {
    return renderFile("Disappointed.html");
  }
}

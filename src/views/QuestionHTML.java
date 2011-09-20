package views;

import Models.Question;

public class QuestionHTML {

  public static String render(Question question, int questionID) {

    String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
      "\"http://www.w3.org/TR/html4/loose.dtd\">\n" +
      "<html>\n" +
      "<head>\n" +
      "<script src=\"/jquery.js\" type=\"text/javascript\"></script>\n" +
      "<script src=\"/application.js\" type=\"text/javascript\"></script>\n" +
      "<link rel=\"stylesheet\" href=\"/application.css\" type=\"text/css\" />" +
      "<title>SkimBot 3000</title>\n" +
      "</head>\n" +
      "<body>\n";
    html += "<h2 class=\"question\">" + question.prompt + "</h2>\n" +
      "<table class=\"answers\">\n";
    for(int i = 0; i < question.responses.length; i++){
      html += "<tr><td><a href=\"/question/" + questionID + "/answer/" + i + "\">" + question.responses[i] + "</a></td></tr>\n";
    }
    html += "</table>\n" +
      "</body>\n" +
      "</html>";
    return html;
  }
}

package views;

import HTTPServer.Database;

public class Results {

  public static String render(int nextQuestionID, boolean correct) {
    String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
      "        \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
      "<html>\n" +
      "<head>\n" +
      "<title>SkimBot 3000</title>\n" +
      "<script src=\"/jquery.js\" type=\"text/javascript\"></script>\n" +
      "<script src=\"/application.js\" type=\"text/javascript\"></script>\n" +
      "<link rel=\"stylesheet\" href=\"/application.css\" type=\"text/css\" />" +
      "</head>\n" +
      "<body>\n";
    if(correct){
      html += "<img src=\"http://dl.dropbox.com/u/16607608/Skim_yes.png\" alt=\"Ugh...\" />\n" +
      "<h2>Skim is satisfied.</h2>\n" +
      "<div id=\"yes\"></div>\n";
    } else {
      html += "<img src=\"http://dl.dropbox.com/u/16607608/Skim_sigh.png\" alt=\"Ugh...\" />\n" +
      "<h2>Skim is not pleased.</h2>\n" +
      "<div id=\"sigh\"></div>\n";
    }
    if(nextQuestionID < Database.table().size())
      html += "<a href=\"/question/" + nextQuestionID + "\">Next Question</a>";
    else
      html += "<a href=\"/end\">Results</a>";
    html += "</body>\n" +
      "</html>";
    return html;
  }
}

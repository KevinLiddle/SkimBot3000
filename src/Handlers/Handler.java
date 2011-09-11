package Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Handler {

  final static String viewsRoot = "src/views/";

  public static synchronized BufferedReader execute(String request) throws Exception {
    try{
      BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
      String line = br.readLine();
      while(line != null){
        String[] route = line.split("\\s*->\\s*");
        if(!line.equals("\n") && !line.startsWith("///") && request.matches(route[0])){
          return callMethod(route[1], request);
        }
        line = br.readLine();
      }
      return notFound();
    } catch(Exception e) {
      e.printStackTrace();
      return error();
    }
  }

  private static BufferedReader error() throws Exception {
    return renderFile("error.html");
  }

  public static synchronized BufferedReader notFound() throws Exception {
    return renderFile("404NotFound.html");
  }

  protected static synchronized BufferedReader renderHTMLString(String output) {
    return new BufferedReader(new StringReader(output));
  }

  protected static synchronized BufferedReader renderFile(String location) throws Exception {
    return renderFile(location, "");
  }

  protected static synchronized BufferedReader renderFile(String location, String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + location + request)));
  }

  protected static ArrayList<Integer> getIDs(String request) {
    ArrayList<Integer> IDs = new ArrayList<Integer>();
    Pattern ints = Pattern.compile("\\d+");
    Matcher match = ints.matcher(request);
    while(match.find()){
      int id = Integer.parseInt(match.group());
      IDs.add(id);
    }
    return IDs;
  }

  private static synchronized BufferedReader callMethod(String methodName, String request) throws Exception {
    String[] route = methodName.split("#");
    Class<?> handlerClass = Class.forName("Handlers." + route[0]);
    Method method = handlerClass.getMethod(route[1], String.class);
    return (BufferedReader) method.invoke(null, request);
  }
}

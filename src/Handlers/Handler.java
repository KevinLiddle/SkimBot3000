package Handlers;

import HTTPServer.Logger.Logger;
import HTTPServer.Logger.SOLogger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler {

  final static String viewsRoot = "src/views/";
  public Logger logger;

  public Handler() {
    this.logger = new SOLogger();
  }

  public Handler(Logger _logger) {
    this.logger = _logger;
  }

  public BufferedReader execute(String request) {
    try{
      BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
      String line = br.readLine();
      while(line != null){  
        String[] route = line.split("\\s*->\\s*");
        if(!line.isEmpty() && !line.startsWith("///") && request.matches(route[0])){
          return callMethod(route[1], request);
        }
        line = br.readLine();
      }
      logger.log("ERROR!! No route matches \"" + request + "\"");
      return notFound();
    } catch(Exception e) {
      e.printStackTrace();
      return error();
    }
  }

  private BufferedReader error() {
    return renderFile("error.html");
  }

  public BufferedReader notFound() {
    return renderFile("404NotFound.html");
  }

  protected BufferedReader renderHTMLString(String output) {
    return new BufferedReader(new StringReader(output));
  }

  public BufferedReader renderFile(String location) {
    try {
      return new BufferedReader(new FileReader(new File(viewsRoot + location)));
    } catch(FileNotFoundException fne) {
      logger.log("ERROR!! Cannot find \"" + viewsRoot + location + "/\"");
      return error();
    }
  }

  protected ArrayList<Integer> getIDs(String request) {
    ArrayList<Integer> IDs = new ArrayList<Integer>();
    Pattern ints = Pattern.compile("\\d+");
    Matcher match = ints.matcher(request);
    while(match.find()){
      int id = Integer.parseInt(match.group());
      IDs.add(id);
    }
    return IDs;
  }

  public BufferedReader callMethod(String methodName, String request) throws Exception {
    String[] route = methodName.split("#");
    try{
      Class<?> handlerClass = Class.forName("Handlers." + route[0]);
      Method method = handlerClass.getMethod(route[1], String.class);
      return (BufferedReader) method.invoke(handlerClass.newInstance(), request);
    } catch(ClassNotFoundException cnf) {
      logger.log("ERROR!! Class: \"Handlers." + route[0] + "\" not found.");
    } catch(NoSuchMethodException nsm) {
      logger.log("ERROR!! Method: \"" + route[1] + "\" not found.");
    } catch(InvocationTargetException ite) {
      logger.log("ERROR!! The method \"" + route[1] + "\" has thrown an exception");
    } catch(IllegalAccessException iae) {
      logger.log("ERROR!! You cannot access the method \"" + route[1] + "\" from here.");
    }
    return null;
  }
}

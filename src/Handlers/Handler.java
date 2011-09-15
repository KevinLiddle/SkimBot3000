package Handlers;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Handler {

  final static String viewsRoot = "src/views/";

  public static BufferedReader execute(String request) {
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
      System.out.println("No route matches \"" + request + "\"");
      return notFound();
    } catch(Exception e) {
      e.printStackTrace();
      return error();
    }
  }

  private static BufferedReader error() {
    return renderFile("error.html");
  }

  public static BufferedReader notFound() {
    return renderFile("404NotFound.html");
  }

  protected static BufferedReader renderHTMLString(String output) {
    return new BufferedReader(new StringReader(output));
  }

  protected static BufferedReader renderFile(String location) {
    return renderFile(location, "");
  }

  protected static BufferedReader renderFile(String location, String request) {
    try {
      return new BufferedReader(new FileReader(new File(viewsRoot + location + request)));
    } catch(FileNotFoundException fne) {
      System.out.println("Cannot find \"" + request + "\" within \"" + viewsRoot + location + "/\"");
      return error();
    }
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

  private static BufferedReader callMethod(String methodName, String request) throws Exception {
    String[] route = methodName.split("#");
    try{
      Class<?> handlerClass = Class.forName("Handlers." + route[0]);
      Method method = handlerClass.getMethod(route[1], String.class);
      return (BufferedReader) method.invoke(null, request);
    } catch(ClassNotFoundException cnf) {
      System.out.println("Class: \"Handlers." + route[0] + "\" not found.");
    } catch(NoSuchMethodException nsm) {
      System.out.println("Method: \"" + route[1] + "\" not found.");
    } catch(InvocationTargetException ite) {
      System.out.println("The method \"" + route[1] + "\" has thrown an exception");
    } catch(IllegalAccessException iae) {
      System.out.println("You cannot access the method \"" + route[1] + "\" from here.");
    }
    throw new Exception();
  }
}

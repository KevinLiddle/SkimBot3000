package Tests.Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

public class HandlerTestHelpers {

  public static boolean page(BufferedReader br, String pageElement) throws Exception {
    return readDocument(br).contains(pageElement);
  }

  public static String readDocument(BufferedReader br) throws Exception {
    String line = br.readLine();
    String document = "";
    while(line != null){
      document += line + "\n";
      line = br.readLine();
    }
    return document;
  }

  public static boolean mockExecute(String request) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
    String line = br.readLine();
    while(line != null){
      String[] route = line.split("\\s*->\\s*");
      if(request.matches(route[0]))
        return mockCallMethod(route[1], request);
      line = br.readLine();
    }
    return false;
  }

  private static boolean mockCallMethod(String methodName, String request) {
    try{
      String[] route = methodName.split("#");
      Class<?> handlerClass = Class.forName("Handlers." + route[0]);
      Method method = handlerClass.getMethod(route[1], String.class);
      return true;
    } catch(Exception e) {
      return false;
    }
  }
}

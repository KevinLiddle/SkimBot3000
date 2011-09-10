package specs.HandlerSpecs;

import java.io.BufferedReader;

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
}

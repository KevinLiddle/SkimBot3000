package Handlers;

import java.io.BufferedReader;

public class HomeHandler extends Handler {

  public static BufferedReader home(String request) throws Exception {
    return renderFile("home.html");
  }
}

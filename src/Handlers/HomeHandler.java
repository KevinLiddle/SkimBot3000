package Handlers;

import java.io.BufferedReader;

public class HomeHandler extends Handler {

  public BufferedReader home(String request) {
    return renderFile("home.html");
  }
}

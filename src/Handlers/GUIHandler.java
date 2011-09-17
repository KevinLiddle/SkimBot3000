package Handlers;

import java.io.BufferedReader;

public class GUIHandler extends Handler{

  public static synchronized BufferedReader stylesheets(String request) {
    return renderFile("stylesheets", request);
  }

  public static synchronized BufferedReader javascripts(String request) {
    return renderFile("javascripts", request);
  }

  public static synchronized BufferedReader images(String request) {
    return renderFile("images", request);
  }
}
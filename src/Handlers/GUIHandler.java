package Handlers;

import java.io.BufferedReader;

public class GUIHandler extends Handler{

  public BufferedReader stylesheets(String request) {
    return renderFile("stylesheets/" + request);
  }

  public BufferedReader javascripts(String request) {
    return renderFile("javascripts/" + request);
  }

  public BufferedReader images(String request) {
    return renderFile("images/" + request);
  }
}
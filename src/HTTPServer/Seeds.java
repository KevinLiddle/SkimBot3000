package HTTPServer;

import Models.Question;

public class Seeds {

  public static void seed() {
    Database.add(new Question("What is your name?", new String[] {"Skim", "Not Skim"}, 0));
    Database.add(new Question("Do you filter your coffee through gold?", new String[] {"Yes", "No", "I don't drink coffee"}, 0));
    Database.add(new Question("Which editor do you use?", new String[] {"TextMate", "IntelliJ", "Vim", "Emacs"}, 2));
    Database.add(new Question("What is your preferred form of travel?", new String[] {"Car", "Bike", "Public Transit", "Walking", "Flying"}, 1));
    Database.add(new Question("Can you banana kick?", new String[] {"Yes", "No"}, 0));
    Database.add(new Question("Which is your favorite musical genre?", new String[] {"Rock", "Gangsta Rap", "Indie", "Dubstep", "80s", "Classical", "Other"}, 4));
    Database.add(new Question("Do you use a mouse?", new String[] {"Yes", "No"}, 1));
    Database.add(new Question("Where do you live?", new String[] {"Chicago", "New York", "L.A.", "Dallas", "Other"}, 2));
    Database.add(new Question("Where do you get your coffee from?", new String[] {"Starbucks", "Caribou", "Intelligentsia", "Grocery Store", "I grow my own beans and make my own", "I import beans directly from Brazil", "I already told you, I don't drink coffee"}, 5));
    Database.add(new Question("Do you know Skim?", new String[] {"Yes", "No", "Like, skim milk?"}, -1));
  }
}

package specs.HTTPServer;

import HTTPServer.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class DatabaseTest {

  Object obj;

  @Before
  public void setUp(){
    Database.table().clear();
    obj = "I'ma test this sucka!";
    Database.add(obj);
  }

  @After
  public void tearDown() {
    Database.table().clear();
  }

  @Test
  public void getInstanceReturnsDatabaseInstance() {
    assertEquals(1, Database.table().size());
    assertTrue(Database.table().get(0) instanceof String);
  }

  @Test
  public void getIndexesReturnsArrayOfAllIndexes() {
    assertArrayEquals(new int[]{0}, Database.ids());
  }

  @Test
  public void addingAnObjectReturnsItsIndex() {
    assertEquals(1, Database.add("Aw snap son!"));
  }


}

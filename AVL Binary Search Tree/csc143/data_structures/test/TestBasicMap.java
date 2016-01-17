package csc143.data_structures.test;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////                                                                                                           IMPORTS
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// imports for testings
import org.junit.*;
import static org.junit.Assert.*;
import csc143.data_structures.*;

/**
 * Tests a Basic Tree Map
 * @author Minhhue H. Khuu
 * @version Assignment 11: Simple Map STANDARD VERSION
 */
public class TestBasicMap {
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                            FIELDS
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  // testing fields
  private MyBasicMap<Integer,String> test;
  private MyBasicMap<String,Integer> test2;
  private MyBasicMap<Integer,String> tests;
  private MyBasicMap<String,Integer> tests2;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                           @BEFORE
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Intialize tests fields
   */
  @Before
  public void getStarted() {
    // create 2 different test instances for different tyeps
    tests = new MyBasicMap<Integer,String>();
    tests2= new MyBasicMap<String,Integer>();
    // give basic values to work with 
    test = new MyBasicMap<Integer,String>();
    test.put(1,"One");
    test.put(2,"Two");
    test.put(3,"Three");
    test.put(4,"Four");
    test.put(5,"Five");
    test.put(6,"Six");
    test.put(7,"Seven");
    test.put(8,"Eight");
    test.put(9,"Nine");
    test.put(10,"Ten");
    test2= new MyBasicMap<String,Integer>();
    test2.put("One",1);
    test2.put("Two",2);
    test2.put("Three",3);
    test2.put("Four",4);
    test2.put("Five",5);
    test2.put("Six",6);
    test2.put("Seven",7);
    test2.put("Eight",8);
    test2.put("Nine",9);
    test2.put("Ten",10);
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                       TEST put()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if put can put stuff
   */
  @Test
  public void testPut1() {
    tests.put(1,"One");
    tests.put(2,"Two");
    tests.put(3,"Three");
    tests.put(4,"Four");
    tests.put(5,"Five");
    tests.put(6,"Six");
    tests.put(7,"Seven");
    tests.put(8,"Eight");
    tests.put(9,"Nine");
    tests.put(10,"Ten");
  }
  
  /**
   * Test if put can put stuff for different generics
   */
  @Test
  public void testPut2() {
    tests2.put("One",1);
    tests2.put("Two",2);
    tests2.put("Three",3);
    tests2.put("Four",4);
    tests2.put("Five",5);
    tests2.put("Six",6);
    tests2.put("Seven",7);
    tests2.put("Eight",8);
    tests2.put("Nine",9);
    tests2.put("Ten",10);
  }
  
  /**
   * Tests for the exception for the put method
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPut3() {
    // will raise an IllegalArgumentException exception
    tests.put(null, null);
    tests.put(null,"S");
    tests.put(1,null);
    tests2.put(null,null);
    tests2.put("S",null);
    tests2.put(null,1);
    test.put(null,null);
    test2.put(null,null);
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                               TEST containsKeys()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if containKeys returns right boolean values
   */
  @Test
  public void testContainKeys1() {
    assertTrue("Testing if boolean value of containsKey() is right", test.containsKey(10));
    assertTrue("Testing if boolean value of containsKey() is right", test.containsKey(1));
    assertTrue("Testing if boolean value of containsKey() is right", test.containsKey(3));
    assertTrue("Testing if boolean value of containsKey() is right", test.containsKey(5));
    assertFalse("Testing if boolean value of containsKey() is right, using wrong int", test.containsKey(140));
    assertFalse("Testing if boolean value of containsKey() is right, using double", test.containsKey(3.4));
    assertFalse("Testing if boolean value of containsKey() is right, using java.awt.Color", 
                test.containsKey(java.awt.Color.RED));
  }
  
  /**
   * Test if containsKey returns right boolean values
   */
  @Test
  public void testContainsKey2() {
    assertTrue("Testing if boolean value of containsKey() is right", test2.containsKey("Four"));
    assertTrue("Testing if boolean value of containsKey() is right", test2.containsKey("Five"));
    assertTrue("Testing if boolean value of containsKey() is right", test2.containsKey("Ten"));
    assertTrue("Testing if boolean value of containsKey() is right", test2.containsKey("Two"));
    assertFalse("Testing if boolean value of containsKey() is right, using chars", test2.containsKey('f'));
    assertFalse("Testing if boolean value of containsKey() is right, using java.awt.Color", 
                test2.containsKey(java.awt.Color.BLUE));
    assertFalse("Testing if boolean value of containsKey() is right, using ints", test2.containsKey(123));
    assertFalse("Testing if boolean value of containsKey() is right, using doubles", test2.containsKey(Math.PI));
    assertFalse("Testing if boolean value of containsKey() is right, using wrong String", test2.containsKey("ASD"));
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                         TEST get()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if the get method returns correct generic values
   */
  @Test
  public void testGet1() {
    assertEquals("Testing if return value of get is correct", "Three", test.get(3));
    assertEquals("Testing if return value of get is correct", "Five", test.get(5));
    assertEquals("Testing if return value of get is correct", "Six", test.get(6));
    assertEquals("Testing if return value of get is correct", "One", test.get(1));
    assertEquals("Testing if return value of get is correct", "Eight", test.get(8));
    assertEquals("Testing if return value of get is correct", null , test.get("APPLE"));
    assertEquals("Testing if return value of get is correct", null , test.get(java.awt.Color.BLUE));
    assertEquals("Testing if return value of get is correct", null , test.get(Math.PI));
  }
  
  /**
   * Test if the get method returns correct generic values
   */
  @Test
  public void testGet2() {
    assertEquals("Testing if return value of get is correct", new Integer(3), test2.get("Three"));
    assertEquals("Testing if return value of get is correct", new Integer(5), test2.get("Five"));
    assertEquals("Testing if return value of get is correct", new Integer(7), test2.get("Seven"));
    assertEquals("Testing if return value of get is correct", new Integer(9), test2.get("Nine"));
    assertEquals("Testing if return value of get is correct", new Integer(10), test2.get("Ten"));
    assertEquals("Testing if return value of get is correct", null , test2.get("APPLE"));
    assertEquals("Testing if return value of get is correct", null , test2.get(java.awt.Color.WHITE));
    assertEquals("Testing if return value of get is correct", null , test2.get(new java.io.File("ASD")));
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                     TEST isEmpty()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if the correct boolean value is given for isEmpty method
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsEmpty() {
    assertTrue("Testing isEmpty() method", tests.isEmpty());
    assertTrue("Testing isEmpty() method", tests2.isEmpty());
    assertFalse("Testing isEmpty() method", test.isEmpty());
    assertFalse("Testing isEmpty() method", test2.isEmpty());
    
    // will raise an IllegalArgumentException exception
    tests.put(null, null);
    tests.put(null,"S");
    tests.put(1,null);
    
    assertTrue("Testing isEmpty() method", tests.isEmpty());
    tests.put(1,"s");
    assertTrue("Testing isEmpty() method", tests.isEmpty());
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                       TEST clear()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if the correct boolean value is given for clear method
   */
  @Test
  public void testClear() {
    // test 1
    assertFalse("Testing isEmpty() method", test.isEmpty());
    test.clear();
    assertTrue("Testing isEmpty() method", test.isEmpty());
    // test 2
    assertFalse("Testing isEmpty() method", test2.isEmpty());
    test2.clear();
    assertTrue("Testing isEmpty() method", test2.isEmpty());
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                        TEST size()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if the return size is correct
   */
  @Test
  public void testSize() {
    assertEquals("Testing size() method", 10, test.size());
    assertEquals("Testing size() method", 10, test2.size());
    assertEquals("Testing size() method", 0, tests.size());
    assertEquals("Testing size() method", 0, tests2.size());
    // duplicate keys will replace old values and size will not change
    test.put(1,"AAA");
    assertEquals("Testing size() method", 10, test.size());
    // regular increase in size
    test2.put("One Hundred", 100);
    assertEquals("Testing size() method", 11, test2.size());
    
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                                                                    TEST toString()
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Test if the string representation is as planned (preorder with proper spacing)
   */
  @Test
  public void testToString1() {
    tests.put(5,"five");
    tests.put(2,"two");
    tests.put(6,"six");
    tests.put(1,"one");
    tests.put(3,"three");
    tests.put(7,"seven");
    tests.put(4,"four");
    
    assertEquals("Testing toString() method", 
                 "(5:five (2:two (1:one () ()) (3:three () (4:four () ()))) (6:six () (7:seven () ())))",
                 tests.toString());
      
  }
  
  /**
   * Test if the string representation is as planned (preorder with proper spacing)
   */
  @Test
  public void testToString2() {
    assertEquals("Testing toString() method",
                 "(4:Four (2:Two (1:One () ()) (3:Three () ())) (8:Eight (6:Six (5:Five () ()) (7:Seven () ())) (9:Nine () (10:Ten () ()))))",
                 test.toString());
    
    assertEquals("Testing toString() method",
                 "(One:1 (Five:5 (Eight:8 () ()) (Four:4 () (Nine:9 () ()))) (Three:3 (Six:6 (Seven:7 () ()) (Ten:10 () ())) (Two:2 () ())))",
                 test2.toString());
    
  }
                 

    
}
package com.example;  
  
import static org.junit.Assert.assertEquals;  
  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.junit.runners.JUnit4;  
  
/** 
 * Tests for {@link Main}. 
 */  
@RunWith(JUnit4.class)  
public class MainTest {  
  
    @Test  
    public void test(){  
        assertEquals(new Main().test(10), 10);  
    }  
}
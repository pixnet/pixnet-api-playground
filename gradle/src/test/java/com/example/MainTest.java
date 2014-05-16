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
    public void test() throws Exception{  
	new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
    }  
}

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
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.request());
    }  
    @Test
    public void set_nonce() throws Exception{
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.set_nonce());
    }  
    @Test
    public void set_timestamp() throws Exception{
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.set_timestamp());
    } 
    @Test
    public void get() throws Exception{
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	assertEquals(ptp.getOauthToken(),"");
    } 
}
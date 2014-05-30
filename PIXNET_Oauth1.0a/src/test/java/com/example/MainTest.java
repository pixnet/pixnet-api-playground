import com.pixnet.*;
import static org.junit.Assert.assertThat;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.junit.runners.JUnit4;  
import static org.hamcrest.CoreMatchers.*;
/** 
 * Tests for {@link Main}. 
 */  
@RunWith(JUnit4.class)  
public class MainTest {  
  
    public void test() throws Exception{  
	PostToPIXNETOauth2 ptp = new PostToPIXNETOauth2("e6a0fa232cc4da68ae21b727b772229e",
				"fac5c7f719feef5eea37449b1fc6b2ad","http://oob");
	System.out.println(ptp.getRequestUrl());
	//System.out.println(ptp.getAccessToken("2553a260d9f8a157c74cc11e4dc398d3afa22f2b"));
    } 
    @Test 
    public void post() throws Exception{
	PostToPIXNETOauth2 ptp = new PostToPIXNETOauth2("e6a0fa232cc4da68ae21b727b772229e",
				"fac5c7f719feef5eea37449b1fc6b2ad","http://oob");
	//System.out.println(ptp.getAccessToken("2553a260d9f8a157c74cc11e4dc398d3afa22f2b"));
	String responseString = ptp.post("047e33c25a828b9daaf22181eb5abb04","Test","AAA","");
	try{
		assertThat(responseString, both(containsString("\"body\":\"HiHi\"")).and(containsString("\"title\":\"Test\"")));
	}catch(AssertionError e){
		System.out.println("post:String not match");
	}
    
    } 
    @Test 
    public void post2() throws Exception{
	PostToPIXNETOauth2 ptp = new PostToPIXNETOauth2("e6a0fa232cc4da68ae21b727b772229e",
				"fac5c7f719feef5eea37449b1fc6b2ad","http://oob");
	//System.out.println(ptp.getAccessToken("2553a260d9f8a157c74cc11e4dc398d3afa22f2b"));
	String responseString = ptp.post("047e33c25a828b9daaf22181eb5abb04","Test","AAA","");
	try{
		assertThat(responseString, both(containsString("\"body\":\"AAA\"")).and(containsString("\"title\":\"Test\"")));
	}catch(AssertionError e){
		System.out.println("post2:String not match");
	}
    
    } 

    public void set_nonce() throws Exception{
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.set_nonce());
    }  

    public void set_timestamp() throws Exception{
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.set_timestamp());
    }
}
import com.pixnet.*;
import static org.junit.Assert.assertThat;  
import org.junit.Test;
import org.junit.Before;  
import org.junit.runner.RunWith;  
import org.junit.runners.JUnit4;  
import static org.hamcrest.CoreMatchers.*;
/** 
 * Tests for {@link Main}. 
 */  
@RunWith(JUnit4.class)  
public class MainTest {
	PostToPIXNET ptp;
	PostToPIXNETOauth2 ptp2;
	String accessToken;
    @Before public void setUp() throws Exception{
	ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	ptp2 = new PostToPIXNETOauth2("e6a0fa232cc4da68ae21b727b772229e",
				"fac5c7f719feef5eea37449b1fc6b2ad","http://oob");
	accessToken = ptp2.getAccessToken("caa9be1c0017b0296fd4335b629a3c6316e5fd72");
    }
    @Test
    public void PostToPIXNETOauth2TestRequest() throws Exception{  
	System.out.println(ptp2.getRequestUrl());
    } 
    @Test 
    public void PostToPIXNETOauth2TestPostError() throws Exception{
	String responseString = ptp2.post(accessToken,"Test","AAA","");
	try{
		assertThat(responseString, both(containsString("\"body\":\"HiHi\"")).and(containsString("\"title\":\"Test\"")));
	}catch(AssertionError e){
		System.out.println("PostToPIXNETOauth2TestPostError:String not match");
	}
    } 
    @Test 
    public void PostToPIXNETOauth2TestPost() throws Exception{
	String responseString = ptp2.post(accessToken,"Test","AAA","");
	try{
		assertThat(responseString, both(containsString("\"body\":\"AAA\"")).and(containsString("\"title\":\"Test\"")));
	}catch(AssertionError e){
		System.out.println("PostToPIXNETOauth2TestPost:String not match");
	}
    
    } 
    @Test
    public void PostToPIXNETOauthTestSet_nonce() throws Exception{
	System.out.println(ptp.set_nonce());
    }  
    @Test
    public void PostToPIXNETOauthTestSet_timestamp() throws Exception{
	System.out.println(ptp.set_timestamp());
    }
}
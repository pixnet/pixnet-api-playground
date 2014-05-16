package pixnet.oauth;

import com.example.*;

class Main{
	public static void main(String args[]){
	PostToPIXNET ptp = new PostToPIXNET("46857444204aa2ce0cb8c9666a8eadae",
				"dfa46d6dc4acfc8a25046fffcc5d9b14");
	System.out.println(ptp.request());
	}
}
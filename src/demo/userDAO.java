package demo;

import java.util.HashMap;
import java.util.Map.Entry;

public class userDAO {

	public HashMap<String, String> userDetail=new HashMap<String,String>();

	public HashMap<String, String> getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(HashMap<String, String> userDetail) {
		this.userDetail = userDetail;
	}
	
	public boolean checkUser(String name, String pass)
	{
		for(Entry<String, String> map: getUserDetail().entrySet())
		{
			if(map.getKey().equals(name) && map.getValue().equals(pass))
			{
				return true;
			}
			
		}
		return false;
		}
	
	
}

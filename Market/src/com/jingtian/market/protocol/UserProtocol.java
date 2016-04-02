package com.jingtian.market.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.jingtian.market.domain.UserInfo;


public class UserProtocol extends BaseProtocol<UserInfo> {

	@Override
	public UserInfo paserJson(String json) {
		//"{name:'Jingtian Li',email:'tansent@gmail.com',url:'image/user.png'}"
		try {
			JSONObject jsonObject=new JSONObject(json);
			String name=jsonObject.getString("name");
			String email=jsonObject.getString("email");
			String url=jsonObject.getString("url");
			UserInfo userInfo=new UserInfo(name, url, email);
			return userInfo;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getKey() {
		return "user";
	}

}

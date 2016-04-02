package com.jingtian.market.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jingtian.market.domain.SubjectInfo;

public class SubjectProtocol extends BaseProtocol <List<SubjectInfo>>{

//	private SubjectInfo subjectInfo;

	@Override
	public String getKey() {
		return "subject";
	}

	public List<SubjectInfo> paserJson(String json) {
//		Gson gson = new Gson();
//		subjectInfoGson = gson.fromJson(json, SubjectInfoGson.class);
//		return subjectInfoGson.list;
		
		List<SubjectInfo> subjectInfos=new ArrayList<SubjectInfo>();
		try {
			JSONArray jsonArray=new JSONArray(json);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String des=jsonObject.getString("des");
				String url = jsonObject.getString("url");
				SubjectInfo info=new SubjectInfo(des, url);
				subjectInfos.add(info);
				
			}
			return subjectInfos;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

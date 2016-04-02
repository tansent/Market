package com.jingtian.market.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jingtian.market.domain.AppInfoGson;
import com.jingtian.market.domain.AppInfo;

public class HomeProtocol extends BaseProtocol <List<AppInfo>>{

	private List<String> pictures; //picture pager
	
	@Override
	public String getKey() {
		return "home";
	}

	/*Only standard json file can be parsed by gson
	 * standard: every array and object have a name behind(except the first one)*/
	@Override
	public List<AppInfo> paserJson(String json) {
		//using Gson to parse json string
//		Gson gson = new Gson();
//		AppInfoGson appInfoGson = gson.fromJson(json, AppInfoGson.class);
//		return appInfoGson.list;
		
		//using traditional way to parse json string
		List<AppInfo> appInfos=new ArrayList<AppInfo>();
		pictures=new ArrayList<String>();
		try {
			JSONObject jsonObject=new JSONObject(json);
			JSONArray jsonArray2 = jsonObject.getJSONArray("picture");
			for(int i=0;i<jsonArray2.length();i++){
				String str=jsonArray2.getString(i);
				pictures.add(str);
			}
			
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				long id=jsonObj.getLong("id");
				String name = jsonObj.getString("name");
				String packageName=jsonObj.getString("packageName");
				String iconUrl = jsonObj.getString("iconUrl");
				float stars=Float.parseFloat(jsonObj.getString("stars"));
				long size=jsonObj.getLong("size");
				String downloadUrl = jsonObj.getString("downloadUrl");
				String des = jsonObj.getString("des");
				AppInfo info=new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
				appInfos.add(info);
			}
			return appInfos;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getPictures() {
		return pictures;
	}
	
	
	

//	public List<ListInfo> load(int index){ //AppInfo
//		// load local data if there is(save data transport)
//		String json = loadLocal(index);
//		if (json == null) { //no local json
//			// request server
//			json = loadServer(index);
//			if (json != null) { //load successfully from the server
//				saveLocal(json, index);
//			}
//		}
//		if (json != null) { //get json result not null
//			return paserJsonUsingGson(json);
////			return paserJson(json);
//		}else {
//			return null;
//		}
//	}

//	/* using cache means - save file to local,
//	 * using cache is for saving data transfer, not saving time */
//	private String loadLocal(int index) {
//		// if the file expires, no more use local file
//		File dir=FileUtils.getCacheDir();// get cache directory
//		File file = new File(dir, "home_" + index); 
//		try {
//			FileReader fr=new FileReader(file);
//			BufferedReader br=new BufferedReader(fr);
//			long oldDate = Long.parseLong(br.readLine());
//			if(System.currentTimeMillis()>oldDate){
//				//if time has past the setting update time, do NOT use local json file
//				return null;
//			}else{
//				String str=null;
//				StringWriter sw=new StringWriter();
//				while((str=br.readLine())!=null){ //read to str (if there is data)
//				
//					sw.write(str); //write to sw
//				}
//				return sw.toString();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

//	private String loadServer(int index) {
		//in android 6.0, using xutils must import org.apache.http.legacy.jar
//		HttpUtils utils = new HttpUtils();
//		utils.send(HttpMethod.GET, "http://127.0.0.1:8090/" + "home" + "?index=" + index, new RequestCallBack<String>() {
//
//			@Override
//			public void onFailure(HttpException arg0, String arg1) {
//				Toast.makeText(UIUtil.getContext(), "Failure", 0).show();
//				
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> arg0) {
//				Toast.makeText(UIUtil.getContext(), arg0.result, 0).show();
//				
//			}
//			
//		}); 
//		return null;
//		HttpResult httpResult = HttpHelper.get(HttpHelper.URL+"home"+"?index="+index);
//		String json = httpResult.getString();
////		System.out.println(json);
//		return json;
//	}

//	private void saveLocal(String json, int index) {
//		// write an expiration period at the first line
//		BufferedWriter bw = null;
//		try {
//			File dir = FileUtils.getCacheDir();
//			File file = new File(dir, "home_"+index);
//			FileWriter fw = new FileWriter(file);
//			bw = new BufferedWriter(fw);
//			//the time written as a long type
//			bw.write(System.currentTimeMillis() + 1000 * 100 + ""); //1000 * 100 is the expiration period
//			bw.newLine(); //change line
//			bw.write(json); //save the entire json file to the file
//			bw.flush();
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			//close the streams in finally block
//			IOUtils.closeQuietly(bw);
//		}
//		
//	}

//	//1.write every json file into a local folder
//	//2.extract every data and store them into the database
//	private List<AppInfo> paserJson(String json) {
//		//parse json principle: big parenthesis-JSONObject ; brackets-JSONArray 
//		//first new a JSONObject; later, use the parent object to get
//		List<AppInfo> list = new ArrayList<AppInfo>();
//		try {
//			JSONObject jsonObject = new JSONObject(json);
//			JSONArray jsonArray = jsonObject.getJSONArray("list");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObj = jsonArray.getJSONObject(i);
//				long id = jsonObj.getLong("id");
//				String name = jsonObj.getString("name");
//				String packageName = jsonObj.getString("packageName");
//				String iconUrl = jsonObj.getString("iconUrl");
//				float stars = Float.parseFloat(jsonObj.getString("stars"));
//				long size = jsonObj.getLong("size");
//				String downloadUrl = jsonObj.getString("downloadUrl");
//				String des = jsonObj.getString("des");
//				AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
//				list.add(appInfo);
//			}
//			return list;
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return null; //if parse fails, return null
//		}
//	}
//	
//	//AppInfoGson.ListInfo
//	private List<ListInfo> paserJsonUsingGson(String json) {
//		Gson gson = new Gson();
//		AppInfoGson appInfoGson = gson.fromJson(json, AppInfoGson.class);
//		return appInfoGson.list;
//	}

	
}

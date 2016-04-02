package com.jingtian.market.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.http.HttpHelper.HttpResult;
import com.jingtian.market.utils.FileUtils;
import com.lidroid.xutils.util.IOUtils;

import android.os.SystemClock;

/*
 * Children class can specify a type to transfer when inherit or not
 */
public abstract class BaseProtocol <T> {

	public static String server_url = "http://127.0.0.1:8090/home?index=index"; 
	public static String URL = "http://127.0.0.1:8090/";
	
	//core function
	public T load(int index){ 
		//SystemClock.sleep(1000);
		
		// load local data if there is(save data transport)
		String json = loadLocal(index);
		if (json == null) { //no local json
			// request server
			json = loadServer(index);
			if (json != null) { //load successfully from the server
				saveLocal(json, index);
			}
		}
		if (json != null) { //get json result not null
			return paserJson(json);
//			return paserJson(json);
		}else {
			return null;
		}
	}
	
	/**
	 * explain the fragment key of the current page (eg. home, subject)
	 * @return
	 */
	public abstract String getKey();
	
	/* using cache means - save file to local,
	 * using cache is for saving data transfer, not saving time */
	private String loadLocal(int index) {
		// if the file expires, no more use local file
		File dir=FileUtils.getCacheDir();// get cache directory
		File file = new File(dir, getKey()+"_" + index + getParams());  //+ getParams(): for those pictures
		try {
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			long oldDate = Long.parseLong(br.readLine());
			if(System.currentTimeMillis()>oldDate){
				//if time has past the setting update time, do NOT use local json file
				return null;
			}else{
				String str=null;
				StringWriter sw=new StringWriter();
				while((str=br.readLine())!=null){ //read to str (if there is data)
				
					sw.write(str); //write to sw
				}
				return sw.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private String loadServer(int index) {
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
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL+getKey()+"?index="+index+getParams());
		if (httpResult != null) {
			String json = httpResult.getString();
			return json;
		}
		else {
			return null;
		}
	}
	
	/**
	 * if there are other parameters. Use this function to add 
	 * @return
	 */
	public String getParams() {
		return "";
	}

	private void saveLocal(String json, int index) {
		// write an expiration period at the first line
		BufferedWriter bw = null;
		try {
			File dir = FileUtils.getCacheDir();
			File file = new File(dir, getKey()+"_"+index);
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			//the time written as a long type
			bw.write(System.currentTimeMillis() + 1000 * 100 + ""); //1000 * 100 is the expiration period
			bw.newLine(); //change line
			bw.write(json); //save the entire json file to the file
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//close the streams in finally block
			IOUtils.closeQuietly(bw);
		}
		
	}
	
//	//1.write every json file into a local folder
//		//2.extract every data and store them into the database
//		private List<ListInfo> paserJson(String json) {
//			//parse json principle: big parenthesis-JSONObject ; brackets-JSONArray 
//			//first new a JSONObject; later, use the parent object to get
//			List<ListInfo> list = new ArrayList<ListInfo>();
//			try {
//				JSONObject jsonObject = new JSONObject(json);
//				JSONArray jsonArray = jsonObject.getJSONArray("list");
//				for (int i = 0; i < jsonArray.length(); i++) {
//					JSONObject jsonObj = jsonArray.getJSONObject(i);
//					long id = jsonObj.getLong("id");
//					String name = jsonObj.getString("name");
//					String packageName = jsonObj.getString("packageName");
//					String iconUrl = jsonObj.getString("iconUrl");
//					float stars = Float.parseFloat(jsonObj.getString("stars"));
//					long size = jsonObj.getLong("size");
//					String downloadUrl = jsonObj.getString("downloadUrl");
//					String des = jsonObj.getString("des");
//					ListInfo appInfo = new ListInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
//					list.add(appInfo);
//				}
//				return list;
//			} catch (JSONException e) {
//				e.printStackTrace();
//				return null; //if parse fails, return null
//			}
//		}
		
//		//AppInfoGson.ListInfo
//		private List<AppInfo> paserJsonUsingGson(String json) {
//			Gson gson = new Gson();
//			AppInfoGson appInfoGson = gson.fromJson(json, AppInfoGson.class);
//			return appInfoGson.list;
//		}
		
		/**
		 * parse json file
		 * @param json
		 * @return
		 */
		public abstract T paserJson(String json);
}

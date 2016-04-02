package com.jingtian.market.domain;

/*
 * extract information from a certain(homelist0) json file
 * 
 * parse json principle: big parenthesis-JSONObject ; brackets-JSONArray 
 * first new a JSONObject; later, use the parent object to get
 */
public class ListInfo {

//	"id": 1525490,
//    "name": "有缘网",
//    "packageName": "com.youyuan.yyhl",
//    "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
//    "stars": 4,
//    "size": 3876203,
//    "downloadUrl": "app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
//    "des": "产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、"
    	
    private long id; //id should be int or long
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private long size;
    private String downloadUrl;
    private String des;
    
	public ListInfo(long id, String name, String packageName, String iconUrl, float stars, long size, String downloadUrl,
			String des) {
		super();
		this.id = id;
		this.name = name;
		this.packageName = packageName;
		this.iconUrl = iconUrl;
		this.stars = stars;
		this.size = size;
		this.downloadUrl = downloadUrl;
		this.des = des;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public float getStars() {
		return stars;
	}
	public void setStars(float stars) {
		this.stars = stars;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public String toString() {
		return "AppInfo [id=" + id + ", name=" + name + ", packageName=" + packageName + ", iconUrl=" + iconUrl
				+ ", stars=" + stars + ", size=" + size + ", downloadUrl=" + downloadUrl + ", des=" + des + "]";
	}
    
    
}

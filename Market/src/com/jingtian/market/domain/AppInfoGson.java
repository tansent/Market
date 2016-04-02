package com.jingtian.market.domain;

import java.util.ArrayList;

/*
 * [] - array
 * {} - object
 */
public class AppInfoGson {

	public ArrayList<String> picture;
	public ArrayList<AppInfo> list;

	public class AppInfo {
		public long id; // id should be int or long
		public String name;
		public String packageName;
		public String iconUrl;
		public float stars;
		public long size;
		public String downloadUrl;
		public String des;

		public AppInfo(long id, String name, String packageName, String iconUrl, float stars, long size,
				String downloadUrl, String des) {
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
			return "ListInfo [id=" + id + ", name=" + name + ", packageName=" + packageName + ", iconUrl=" + iconUrl
					+ ", stars=" + stars + ", size=" + size + ", downloadUrl=" + downloadUrl + ", des=" + des + "]";
		}

	}

	@Override
	public String toString() {
		return "AppInfoGson [picture=" + picture + ", list=" + list + "]";
	}

}

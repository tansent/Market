package com.jingtian.market.domain;

import java.util.ArrayList;

public class SubjectInfo {

//	public ArrayList<SubjectInfo> list;
//	
//	public class SubjectInfo {
//		private String des;
//		private String url;
//
//		public SubjectInfo(String des, String url) {
//		super();
//		this.des = des;
//		this.url = url;
//		}
//		
//		public String getDes() {
//			return des;
//		}
//
//		public void setDes(String des) {
//			this.des = des;
//		}
//
//		public String getUrl() {
//			return url;
//		}
//
//		public void setUrl(String url) {
//			this.url = url;
//		}
//
//		@Override
//		public String toString() {
//			return "SubjectInfoGson [des=" + des + ", url=" + url + "]";
//		}
//		// }
//	}
//
//	@Override
//	public String toString() {
//		return "SubjectInfoGson [list=" + list + "]";
//	}
	
	
	 private String des;
	 private String url;
	
	 public SubjectInfo(String des, String url) {
	 super();
	 this.des = des;
	 this.url = url;
	 }
	 public String getDes() {
	 return des;
	 }
	 public void setDes(String des) {
	 this.des = des;
	 }
	 public String getUrl() {
	 return url;
	 }
	 public void setUrl(String url) {
	 this.url = url;
	 }
	 @Override
	 public String toString() {
	 return "SubjectInfoGson [des=" + des + ", url=" + url + "]";
	 }

}

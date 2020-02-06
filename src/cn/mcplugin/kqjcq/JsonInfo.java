package cn.mcplugin.kqjcq;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
@SuppressWarnings("all")
public class JsonInfo implements Runnable{
	public static String jsonStr = null;
	final static String api = "https://www.tianqiapi.com/api?version=epidemic&appid=23035354&appsecret=8YvlPNrz";
	public static void main(String[] args) {
		getDocument();
		System.out.println(getGlobalInfo());
	}
	
	public String getCityInfo(String city) {return null;}
	
	public static String getGlobalInfo() {
		JSONObject j = new JSONObject(jsonStr);
		JSONObject arr = j.getJSONObject("data");
		String finalStr = "数据更新至："+arr.getString("date")+"\n"
				+"确诊病例："+arr.getInt("diagnosed")+"\n"
				+"疑似病例："+arr.getInt("suspect")+"\n"
				+"治愈人数："+arr.getInt("cured")+"\n"
				+"死亡病例："+arr.getInt("death");
		

		return finalStr;

		

	}

	public static void getDocument() {
		//实例化defaultHttpClient
				DefaultHttpClient hc=new DefaultHttpClient();
				try {
					//实例化post方式访问并且把路径放入
					HttpPost httppost=new HttpPost(api);
					//把需要的参数传入
					//执行访问返回resp
					HttpResponse resp=hc.execute(httppost);
					//获取访问的结果
					HttpEntity entity=resp.getEntity();
					//把返回的结果转成字符串
					jsonStr = EntityUtils.toString(entity);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}//运行完后执行
				finally {
					hc.getConnectionManager().shutdown();
				}

	}

	@Override
	public void run() {
		getDocument();
		try {
			Thread.sleep(1000*600);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

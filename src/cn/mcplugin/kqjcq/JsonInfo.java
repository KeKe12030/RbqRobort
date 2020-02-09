package cn.mcplugin.kqjcq;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
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
		//System.out.println(jsonStr);
		JSONObject j = new JSONObject(jsonStr);
		JSONObject arr = j.getJSONObject("data");
		String finalStr = "数据更新至："+arr.getString("date")+"\n"
				+"确诊病例："+arr.getInt("diagnosed")+" 新增：+"+arr.getInt("diagnosedIncr")+"\n"
				+"严重病例："+arr.getInt("serious")+" 新增：+"+arr.getInt("seriousIncr")+"\n"
				+"疑似病例："+arr.getInt("suspect")+" 新增：+"+arr.getInt("suspectIncr")+"\n"
				+"治愈人数："+arr.getInt("cured")+" 新增：+"+arr.getInt("curedIncr")+"\n"
				+"死亡病例："+arr.getInt("death")+" 新增：+"+arr.getInt("deathIncr");
		return finalStr;



	}
	public static boolean isJsonOK(){
		if(jsonStr == null) {
			getDocument();
		}
		JSONObject js = new JSONObject(jsonStr);

		if(js.getInt("errcode")!=0) {
			return false;
		}
		return true;
	}
	public static void getDocument() {
		//实例化defaultHttpClient
		//		DefaultHttpClient hc=new DefaultHttpClient();
		//		while(true) {
		//			try {
		//				//实例化post方式访问并且把路径放入
		//				HttpPost httppost=new HttpPost(api);
		//				httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36 Edg/80.0.361.48");
		//				//把需要的参数传入
		//				//执行访问返回resp
		//				HttpResponse resp=hc.execute(httppost);
		//				//获取访问的结果
		//				HttpEntity entity=resp.getEntity();
		//				//把返回的结果转成字符串
		//				if(isJsonOK()) {
		//					jsonStr = EntityUtils.toString(entity);
		//					break;
		//				}else {
		//					System.out.println("错误代码：10000");
		//					continue;
		//				}
		//
		//			} catch (Exception e) {
		//				// TODO: handle exception
		//				e.printStackTrace();
		//			}//运行完后执行
		//			finally {
		//				hc.getConnectionManager().shutdown();
		//			}

		//		URL url = null;
		//		InputStream is = null;
		//		try {
		//			url = new URL(api);
		//			is = url.openStream();
		//			BufferedReader br = new BufferedReader(new InputStreamReader(is,"ASCII"));
		//			String msg = null;
		//			while((msg = br.readLine())!=null) {
		//				jsonStr += msg;
		//			}
		//			System.out.println(msg);
		//		}catch(Exception e) {
		//			e.printStackTrace();
		//		}
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedReader br = null;

		try {

			url = new URL(api);
			while(true) {
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
								+ " AppleWebKit/537.36 (KHTML, like Gecko) "
								+ "Chrome/80.0.3987.87 Safari/537.36 Edg/80.0.361.48");
				is = conn.getInputStream();
				br = new BufferedReader(new InputStreamReader(is,"ASCII"));
				int msg = 0;
				jsonStr = "";
				while((msg = br.read())!=-1) {
					jsonStr += (char)msg;
				}
				if(jsonStr.contains("\"errcode\":100")) {
					//System.out.println("开始"+jsonStr+"结束");
					continue;
				}else {
					//System.out.println("开始"+jsonStr+"结束");
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

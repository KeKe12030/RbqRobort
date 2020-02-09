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
		String finalStr = "���ݸ�������"+arr.getString("date")+"\n"
				+"ȷ�ﲡ����"+arr.getInt("diagnosed")+" ������+"+arr.getInt("diagnosedIncr")+"\n"
				+"���ز�����"+arr.getInt("serious")+" ������+"+arr.getInt("seriousIncr")+"\n"
				+"���Ʋ�����"+arr.getInt("suspect")+" ������+"+arr.getInt("suspectIncr")+"\n"
				+"����������"+arr.getInt("cured")+" ������+"+arr.getInt("curedIncr")+"\n"
				+"����������"+arr.getInt("death")+" ������+"+arr.getInt("deathIncr");
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
		//ʵ����defaultHttpClient
		//		DefaultHttpClient hc=new DefaultHttpClient();
		//		while(true) {
		//			try {
		//				//ʵ����post��ʽ���ʲ��Ұ�·������
		//				HttpPost httppost=new HttpPost(api);
		//				httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36 Edg/80.0.361.48");
		//				//����Ҫ�Ĳ�������
		//				//ִ�з��ʷ���resp
		//				HttpResponse resp=hc.execute(httppost);
		//				//��ȡ���ʵĽ��
		//				HttpEntity entity=resp.getEntity();
		//				//�ѷ��صĽ��ת���ַ���
		//				if(isJsonOK()) {
		//					jsonStr = EntityUtils.toString(entity);
		//					break;
		//				}else {
		//					System.out.println("������룺10000");
		//					continue;
		//				}
		//
		//			} catch (Exception e) {
		//				// TODO: handle exception
		//				e.printStackTrace();
		//			}//�������ִ��
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
					//System.out.println("��ʼ"+jsonStr+"����");
					continue;
				}else {
					//System.out.println("��ʼ"+jsonStr+"����");
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}

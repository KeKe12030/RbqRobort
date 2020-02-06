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
		String finalStr = "���ݸ�������"+arr.getString("date")+"\n"
				+"ȷ�ﲡ����"+arr.getInt("diagnosed")+"\n"
				+"���Ʋ�����"+arr.getInt("suspect")+"\n"
				+"����������"+arr.getInt("cured")+"\n"
				+"����������"+arr.getInt("death");
		

		return finalStr;

		

	}

	public static void getDocument() {
		//ʵ����defaultHttpClient
				DefaultHttpClient hc=new DefaultHttpClient();
				try {
					//ʵ����post��ʽ���ʲ��Ұ�·������
					HttpPost httppost=new HttpPost(api);
					//����Ҫ�Ĳ�������
					//ִ�з��ʷ���resp
					HttpResponse resp=hc.execute(httppost);
					//��ȡ���ʵĽ��
					HttpEntity entity=resp.getEntity();
					//�ѷ��صĽ��ת���ַ���
					jsonStr = EntityUtils.toString(entity);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}//�������ִ��
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}

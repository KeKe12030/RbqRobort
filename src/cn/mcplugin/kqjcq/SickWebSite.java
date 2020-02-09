package cn.mcplugin.kqjcq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SickWebSite implements Runnable{
	public static boolean testJiangSu = true;
	//public static String qurakHtml = null;
	public static String zhihuHtml = null;
	//final static String qurak = "https://www.zhihu.com/special/19681091";
	final static String zhihu = "https://www.zhihu.com/2019-nCoV/trends";
	public SickWebSite(){
		try {
			//getHtmlPageResponse(qurak);
			getHtmlPageResponse(zhihu);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	public static void getHtmlPageResponse(String url) throws IOException {
		URL url1 = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedReader br = null;

		try {
			url1 = new URL(url);
			conn = (HttpURLConnection)url1.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
							+ " AppleWebKit/537.36 (KHTML, like Gecko) "
							+ "Chrome/80.0.3987.87 Safari/537.36 Edg/80.0.361.48");
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			int msg = 0;
			zhihuHtml = "";
			while((msg = br.read())!=-1) {
				zhihuHtml += (char)msg;
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
	//解析xml获取ImageUrl地址
	public static String getImageUrl(){
		Document document = Jsoup.parse(zhihuHtml);//获取html文档
		Elements es = document.getElementsByTag("img");//获取元素节点等
		//System.out.println(document);
		for(Element e : es) {
			if(e.attr("alt").equals("疫情地图"))
				return e.attr("src");
		}
		return null;
	}

	//	public static String getSickInfo() throws IOException{
	//		Document document = Jsoup.parse(qurakHtml);//获取html文档
	//		Pattern p = Pattern.compile("\\d{2}\\-\\d{2} \\d{2}:\\d{2}");
	//		Matcher m = p.matcher(document.text());
	//		String time = null;
	//		while(m.find()) {
	//			time = m.group();//获取更新时间
	//		}
	//		//		return "数据更新至："+finalStr;
	//		//		int a = document.indexOf("数据更新至");
	//		//		int b = document.indexOf("疑似病例数会随确诊、解除或新增而变化");
	//		//		String f = document.substring(a,b);
	//		//		String[] arr = f.split("\\s+");
	//		//		System.out.println(arr.length);
	//		//		return "数据更新至："+time+"\n"+arr[4]+" "+arr[3]+" "+arr[5]+"新增："+arr[7]+"人 "+"\n"//arr6
	//		//		+arr[9]+" "+arr[8]+" "+arr[10]+" "+"新增："+arr[12]+"人 "+"\n"//arr11
	//		//		+arr[14]+" "+arr[13]+" "+arr[15]+"新增："+arr[17]+"人 \n"//arr16
	//		//		+arr[19]+" "+arr[18]+" "+arr[20]+"新增："+arr[22]+"人";//arr21
	//		Elements els1 = document.getElementsByClass("colum-item-current");//确定人数
	//		Elements els2 = document.getElementsByClass("colum-item-incr-count");//新增人数
	//		String[] trueNum = new String[3];
	//		String[] newNum = new String[3];
	//		int a = 0;
	//		int b = 0;
	//		for(Element e : els1) {
	//			if(a<3) {
	//				trueNum[a] = e.text();
	//				a++;
	//			}
	//		}
	//		for(Element e : els2) {
	//			if(b<3) {
	//				newNum[b] = e.text();
	//				b++;
	//			}
	//		}
	//		return "数据更新至："+time+"\n"
	//				+"确诊人数："+trueNum[0]+" "+"昨日新增："+newNum[0]+"\n"
	//				+"治愈人数："+trueNum[1]+" "+"昨日新增："+newNum[1]+"\n"
	//				+"死亡人数："+trueNum[2]+" "+"昨日新增："+newNum[2];
	//	}
	public static String getArea(String area){
		String document = Jsoup.parse(zhihuHtml).text().replace("省","");//获取html文档
		int a = document.indexOf("全国分市数据");
		String fin = document.substring(a);
		String[] arr = fin.split("\\s+");
		String areaInfo = null;
		if(arr.length > 0) {
			for(int i = 0; i<arr.length;i++) {
				if(arr[i].equals(area)) {
					areaInfo = arr[i]+"疫情最新信息：";
					areaInfo = areaInfo+"确诊："+arr[i+1]+"例"+"\n";
					areaInfo = areaInfo+"治愈："+arr[i+2]+"例"+"\n";
					areaInfo = areaInfo+"死亡："+arr[i+3]+"例"+"\n";
					return areaInfo;
				}
			}

		}
		return "没有找到"+area+"的疫情信息";
	}

	public static void main(String[] args) throws IOException {
		SickWebSite sws = new SickWebSite();
		System.out.println(getImageUrl());
		//		Thread t = new Thread(sws);
		//		t.start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				//getHtmlPageResponse(qurak);
				getHtmlPageResponse(zhihu);
				Thread.sleep(1000*600);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}

}

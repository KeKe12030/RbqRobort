package cn.mcplugin.kqjcq;

import java.io.IOException;
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
	public static String qurakHtml = null;
	public static String zhihuHtml = null;
	final static String qurak = "https://broccoli.uc.cn/apps/pneumonia/routes/index";
	public SickWebSite(){
		try {
			getHtmlPageResponse(qurak);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	public static void getHtmlPageResponse(String url) throws IOException {
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
		webClient.getOptions().setActiveXNative(false);
		//webClient.getOptions().setCssEnabled(false);//是否启用CSS
		webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

		webClient.getOptions().setTimeout(30000);//设置“浏览器”的请求超时时间
		webClient.waitForBackgroundJavaScript(1000*20);
		webClient.setJavaScriptTimeout(30000);//设置JS执行的超时时间
		HtmlPage page;
		try {
			page = webClient.getPage(url);
		} catch (Exception e) {
			webClient.close();
			//e.printStackTrace();
			throw e;
		}
		//webClient.waitForBackgroundJavaScript(30000);//该方法阻塞线程
		if(url.equals(qurak)) {
			qurakHtml = page.asXml();
		}
		webClient.close();
	}
	//解析xml获取ImageUrl地址
	public static String getImageUrl(){
		Document document = Jsoup.parse(qurakHtml);//获取html文档
		Elements es = document.getElementsByClass("cover-pic all-map mt10 mb20");//获取元素节点等
		for(Element e : es) {
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
		String document = Jsoup.parse(qurakHtml).text().replace("省","");//获取html文档
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
		//System.out.println(getSickInfo());
		//		Thread t = new Thread(sws);
		//		t.start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				getHtmlPageResponse(qurak);
				Thread.sleep(1000*600);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}

}

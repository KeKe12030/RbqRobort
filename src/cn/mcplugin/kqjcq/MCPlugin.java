package cn.mcplugin.kqjcq;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MCPlugin {
	public void htmlTurnImage(String html) throws Exception {
		JEditorPane ed = new JEditorPane(html);
		ed.setSize(200,200);

		//create a new image
		BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		//paint the editor onto the image
		SwingUtilities.paintComponent(image.createGraphics(), 
				ed, 
				new JPanel(), 
				0, 0, image.getWidth(), image.getHeight());
		//save the image to file
		ImageIO.write((RenderedImage)image, "jpg", new File("F:\\Desktop\\new.jpg"));
	}
	public String getHtml() throws IOException {
		String a = null;
		String b = null;
		String url = "https://3g.dxy.cn/newh5/view/pneumonia";
		Document doc = Jsoup.connect(url)
				.header("Accept", "*/*")
				//.header("Connection:", "keep-alive")//如果是这种方式，这里务必带上
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36")//伪装Chrome浏览器
				.timeout(30000)//超时时间30秒
				.get();
		Elements es = doc.getElementsByTag("p");
		for(Element e : es) {
			if(e.className().contains("mapTitle___"));
			a = e.toString();
			System.out.println(a);
			break;
		}
		for(Element e : es) {
			if(e.className().contains("confirmedNumber")) {
				b = e.toString();
				System.out.println(b);
				break;
			}
		}
		return a+"\n"+b;

	}
	public static void main(String[] args) throws Exception {
		MCPlugin mc = new MCPlugin();
		//String c = mc.getHtml();
		//mc.htmlTurnImage(c);
		JEditorPane ed = new JEditorPane(new URL("https://www.mcplugin.cn"));
		ed.setSize(2000,2000);

		//create a new image
		BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		//paint the editor onto the image
		SwingUtilities.paintComponent(image.createGraphics(), 
				ed, 
				new JPanel(), 
				0, 0, image.getWidth(), image.getHeight());
		ImageIO.write((RenderedImage)image, "png", new File("F:\\Desktop\\html.png"));

	}
}

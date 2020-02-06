package cn.mcplugin.kqjcq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WHComeOn implements Runnable{
	static Date d = null;
	static volatile List<Long> l = new ArrayList<Long>();
	public boolean listWriteInFile() {
		try{
			d = new Date(System.currentTimeMillis());
			File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\comeon"+d.getYear()+d.getMonth()+d.getDate()+".txt");
			if(!file.exists()){
				file.createNewFile();
			}
			//true = append file
			FileWriter fileWritter = new FileWriter(file,true);
			for(int i = 0; i < l.size(); i++) {
				fileWritter.write(l.get(i).toString()+"\n");
			}
			fileWritter.close();
			return true;
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	public boolean isContain(long qq) {
		for(long lon : l) {
			if(lon == qq) {
				return true;
			}
		}
		return false;
	}
	public boolean checkQQ(long qq) {
		//true 相等 false 不等
		System.out.println(l);
		System.out.println(l.contains(Long.valueOf(qq)));
//		if(l.contains(Long.valueOf(qq))) {
//			return false;
//		}
		d = new Date(System.currentTimeMillis());
		File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\comeon"+d.getYear()+d.getMonth()+d.getDate()+".txt");
		BufferedReader bis;
		try {
			bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String a = null;
			while((a = bis.readLine())!=null) {
				if(a.equals(String.valueOf(qq))) {
					bis.close();
					return true;
				}
			}
			bis.close();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		
	}
	public void addAllNum() {
		File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\AllComeon.txt");
		FileWriter fw = null;
		int a = getAllNum();
		try {
			fw = new FileWriter(file,false);
			fw.write((a+1)+"\n");
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getAllNum() {
		File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\AllComeon.txt");
		BufferedReader bis;
		try {
			bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String a = null;
			while((a = bis.readLine())!=null) {
				bis.close();
				return Integer.valueOf(a);
			}
			bis.close();
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	public int getNum() {
		d = new Date(System.currentTimeMillis());
		File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\comeon"+d.getYear()+d.getMonth()+d.getDate()+".txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		BufferedReader bis;
		try {
			bis = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			int i = 0;
			while(bis.readLine()!=null) {
				i++;
			}
			bis.close();
			return i;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}

	}
	public void longWriteInFile(Long qq) {
		try{
			d = new Date(System.currentTimeMillis());
			File file = new File("C:\\Users\\Administrator\\Desktop\\image\\wh\\comeon"+d.getYear()+d.getMonth()+d.getDate()+".txt");
			if(!file.exists()){
				file.createNewFile();
			}
			//true = append file
			FileWriter fileWritter = new FileWriter(file,true);
			fileWritter.write(qq+"\n");
			fileWritter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public int zan(long qq) throws InterruptedException {
		if(this.checkQQ(qq)) {
			return this.getNum();//相等
		}
		l.add(qq);//添加QQ
		this.longWriteInFile(qq);
		addAllNum();
		//this.longWriteInFile(qq);
		return 0;//不等
	}
	@Override
	public void run() {
		listWriteInFile();
		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		WHComeOn w = new WHComeOn();
//		System.out.println(w.zan(10001L));
//		System.out.println(w.zan(10002L));
//		System.out.println(w.zan(10004L));
//		System.out.println(w.zan(10002L));
//		System.out.println(w.zan(10003L));
//		System.out.println(w.zan(10004L));
		//w.addAllNum();
		//System.out.println(w.getAllNum());
		//w.longWriteInFile(10001L);
		//w.listWriteInFile();
		
//		Thread t = new Thread(new WHComeOn());
//		t.start();
	}
}

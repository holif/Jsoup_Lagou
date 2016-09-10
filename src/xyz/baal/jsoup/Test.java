package xyz.baal.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

public static List<String> zpURLlist = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		System.out.println("开始："+new Date().toGMTString());
/*		GetZPURL zpurl = new GetZPURL();
		zpurl.loadLocal("D:/lagou.html", "UTF-8", "http://www.lagou.com/");
		//zpurl.loadInternet("http://www.lagou.com/");
		for (String string : zpurl.getZpURLlist()) {

			GetJob getJob = new GetJob(string);
			getJob.init();
			getJob.writeCSVFile();
		}*/
		
		GetJob getJob = new GetJob("//www.lagou.com/zhaopin/Hadoop/");
		getJob.init();
		getJob.writeCSVFile();
		
		System.out.println("结束："+new Date().toGMTString());
	}
}

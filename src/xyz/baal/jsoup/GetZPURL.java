package xyz.baal.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetZPURL {
	
	private List<String> zpURLlist = new ArrayList<String>();
	
	public GetZPURL(){
		super();
	}
	
	public void loadInternet(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
				.timeout(5000)
				.get();
		} catch (IOException e) {
			System.out.println("获取招聘URL失败。");
			return;
		}
		Element content = doc.getElementById("container");
		Elements links = content.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			if(isZp(linkHref)){
				zpURLlist.add(linkHref);
			}
		}
	}
	
	public void loadLocal(String path ,String charset, String baseURL) throws IOException {

		File input = new File(path);
		Document doc = Jsoup.parse(input, charset, baseURL);
		Element content = doc.getElementById("container");
		Elements links = content.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			if(isZp(linkHref)){
				zpURLlist.add(linkHref);
			}
		}
	}
	
	public boolean isZp(String url){
		if(url.indexOf("//www.lagou.com/zhaopin/")!=-1&&url.length()>24){
			return true;
		}else {
			return false;
		}
	}

	public List<String> getZpURLlist() {
		return zpURLlist;
	}
}

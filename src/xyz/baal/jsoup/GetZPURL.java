package xyz.baal.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 根据拉勾网首页获取各个招聘职位首页链接
 * 
 * @author 
 *
 */
public class GetZPURL {
	
	private List<String> zpURLlist = new ArrayList<String>();//存放各个招聘职位首页链接
	
	public GetZPURL(){
		super();
	}
	
	/**
	 * 网络加载html文档
	 * @param url	文档url
	 */
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
	
	/**
	 * 从本地加载html文档
	 * @param path	文档路径
	 * @param charset	文档字符集
	 * @param baseURL	基本url，当链接中存在相对路径时作为前缀
	 * @throws IOException	文件不存在或无法读取时抛出此异常
	 */
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

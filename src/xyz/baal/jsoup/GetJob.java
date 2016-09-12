package xyz.baal.jsoup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.csvreader.CsvWriter;

/**
 * 获取拉勾网某一个职位的30x15条招聘信息
 * 
 * @author
 * 
 */
public class GetJob implements Runnable{

	private String zpUrl;// 某招聘职位对应的原始URL
	private List<String> zpUrlList = new ArrayList<String>(); // 每个分页对应的URL
	private List<String> jobUrlList = new ArrayList<String>();// 每条招聘信息对应的URL
	private List<Job> joblist = new ArrayList<Job>();// 存放30x15条招聘信息

	private static final String A_HREF = "//www.lagou.com/jobs/\\d+.html"; // href格式 //www.lagou.com/jobs/2350451.html
	private static final String PATH = "D:/"; // 文件存放路径

	private String jobName = "";//招聘职位名称

	/**
	 * 
	 * @param url 招聘职位首页url,如java、hadoop等招聘职位
	 */
	public GetJob(String url) {
		zpUrl = url;
	}

	/**
	 * 在此方法内完成某一招聘职位的450条数据抓取
	 */
	public void init() {
		
		// 构建30个分页URL
		zpUrlList.add(zpUrl + "?filterOption=3");
		for (int i = 2; i <= 30; i++) {
			zpUrlList.add(zpUrl + i + "/?filterOption=3");
		}

		// 提取每个分页中的招聘信息URL
		for (String string : zpUrlList) {
			Document doc = null;
			try {
				doc = Jsoup.connect("http:" + string)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
						.timeout(5000)
						.get();
			} catch (IOException e) {
				continue;
			}
			Element content = doc.getElementById("s_position_list");
			if (content == null) {
				continue;
			}

			Elements links = content.getElementsByTag("a");
			if (links == null) {
				continue;
			}
			for (Element link : links) {
				String linkHref = link.attr("href");
				Pattern pattern = Pattern.compile(A_HREF, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(linkHref);
				if (matcher.find()) {
					jobUrlList.add("http:" + linkHref);
				}
			}
			if (jobName == "") {
				jobName = doc.select("title").first().text().split("-")[0];
			}
		}

		// 根据招聘信息URL提取招聘详细信息
		for (String string : jobUrlList) {
			Job job = new Job();
			Document doc = null;
			try {
				doc = Jsoup.connect(string)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36")
						.timeout(5000)
						.get();
				job.setJobname(jobName);

				Element content = doc.getElementById("container");
				Element job_request = content.select(".job_request p").first();
				if (job_request != null) {
					if (job_request.child(0) != null) {
						job.setSalary(job_request.child(0).text());
						job.setPlace(job_request.child(1).text());
						job.setExperience(job_request.child(2).text());
						job.setEducational(job_request.child(3).text());
					} else {
						continue;
					}

				} else {
					continue;
				}

				Element cpy = doc.getElementById("job_company");
				if (cpy.childNodeSize()>=2) {
					job.setCompany(cpy.child(0).child(0).child(0).attr("alt"));
					job.setBusiness(cpy.child(1).child(0).child(0).ownText());
					job.setStage(cpy.child(1).child(2).child(0).ownText());
				} else {
					continue;
				}
				joblist.add(job);
			} catch (IOException e) {
				continue;
			}
		}
	}

	public List<Job> getJoblist() {
		return joblist;
	}

	/**
	 * 将采集数据写入txt文件中
	 */
	public void writeTxtFile() {
		if (joblist.size() == 0 || joblist == null) {
			return;
		}
		File file = new File(PATH + joblist.get(0).getJobname() + ".txt");
		FileWriter fw = null;
		BufferedWriter bw = null;
		Iterator<Job> iter = joblist.iterator();
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			while (iter.hasNext()) {
				bw.write(iter.next().toString());
				bw.newLine();
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将采集数据写入CSV文件中
	 */
	public void writeCSVFile() {
		CsvWriter wr = null;
		if (joblist.size() == 0 || joblist == null) {
			return;
		}
		try {
			String csvFilePath = PATH + joblist.get(0).getJobname() + ".csv";
			wr = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));
			String[] header = { "职位名称", "薪水", "工作地点", "工作经验", "学历", "公司名称", "公司业务", "发展阶段"};
			wr.writeRecord(header);
			for (Job job : joblist) {
				String[] jobstr = { job.getJobname(), job.getSalary(), job.getPlace(), job.getExperience(),
						job.getEducational(), job.getCompany(), job.getBusiness(), job.getStage() };
				wr.writeRecord(jobstr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wr != null) {
				wr.close();
			}
		}
	}

	@Override
	public void run() {
		init();
		writeCSVFile();
		System.out.println(jobName+"--End");
	}
}

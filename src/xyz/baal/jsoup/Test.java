package xyz.baal.jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

public static List<String> zpURLlist = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {

		 //创建等待任务队列   
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);   
        //创建线程池，池中保存的线程数为3，池中允许的最大线程数为4  
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,4,50,TimeUnit.MILLISECONDS,bqueue);   
		
		Runnable job1 = new GetJob("//www.lagou.com/zhaopin/iOS/");
		Runnable job2 = new GetJob("//www.lagou.com/zhaopin/C/");
		Runnable job3 = new GetJob("//www.lagou.com/zhaopin/C++/");
		Runnable job4 = new GetJob("//www.lagou.com/zhaopin/Python/");
		Runnable job5 = new GetJob("//www.lagou.com/zhaopin/HTML5/");
		Runnable job6 = new GetJob("//www.lagou.com/zhaopin/webqianduan/");
		
		pool.execute(job1);
		pool.execute(job2);
		pool.execute(job3);
		pool.execute(job4);
		pool.execute(job5);
		pool.execute(job6);
		//关闭线程池
		pool.shutdown();
	}
}

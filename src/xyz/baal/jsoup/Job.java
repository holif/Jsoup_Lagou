package xyz.baal.jsoup;

public class Job {
	
	private String jobname;//职位名称
	private String salary;//薪水
	private String place;//工作地点
	private String experience;//工作经验
	private String educational;//学历
	private String business;//业务
	private String stage;//发展阶段
	
	public Job() {
		super();
	}
	
	public Job(String jobname, String salary, String place, String experience, String educational, String business,
			String stage) {
		super();
		this.jobname = jobname;
		this.salary = salary;
		this.place = place;
		this.experience = experience;
		this.educational = educational;
		this.business = business;
		this.stage = stage;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getEducational() {
		return educational;
	}
	public void setEducational(String educational) {
		this.educational = educational;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	@Override
	public String toString() {
		return "Job [jobname=" + jobname + ", salary=" + salary + ", place=" + place + ", experience=" + experience
				+ ", educational=" + educational + ", business=" + business + ", stage=" + stage + "]";
	}
}
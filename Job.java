public class Job{
	public Job(int starttimeseek) {
		this.startSeekTime = starttimeseek;
	}
	int jobID;
	int totalTime;
	int executedTime;
	int startSeekTime;
	
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public int getExecutedTime() {
		return executedTime;
	}
	public void setExecutedTime(int executedTime) {
		this.executedTime = executedTime;
	}
}

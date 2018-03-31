import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Vector;


public class jobscheduler {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		try {
			String inputFile = "";
			if(args==null || args.length <= 0) {
				return;
			}
			else {
			inputFile = args[0];
			}

			DataInputStream dis;
			DataOutputStream dos;
			
            dis= new DataInputStream(new FileInputStream(inputFile));
			dos= new DataOutputStream(new FileOutputStream("output_file.txt"));
			String line = null;

			int seek_time = 0;
			int event_time = -1;
			Vector<Job> listJob = new Vector<Job>();
			int plus_time = 0;
			boolean isExecutedLine = true;
			do {
				if(seek_time < event_time) {
					plus_time = 5;
					seek_time+=plus_time;
				}
				else {
					if(isExecutedLine) {
						line  = dis.readLine();
						if(line==null) {
							 break;
						}
						line = line.trim();
					}
					if(line!=null && line.length() > 0) {
						int index = line.indexOf(":");
						if(index==-1) {
							continue;
						}
						
						String strTime = line.substring(0, index);
						event_time = Integer.parseInt(strTime);
						String nextToken = line.substring(index + 1).trim();
						
						if(nextToken.length() < 0) {
							continue;
						}
						if(seek_time < event_time) {
							isExecutedLine = false;
							continue;
						}
						int index_startBracket = nextToken.indexOf("(");
						int index_endBracket = nextToken.indexOf(")");
						
						if(index_endBracket > index_startBracket) {
							
							String command = nextToken.substring(0, index_startBracket);
							String strParameter = nextToken.substring(index_startBracket + 1, index_endBracket);
							String strParam[] = strParameter.split(",");
							
							switch(command) {
							case "Insert":
								if(strParam.length >= 2) {
									Job jobData = new Job(seek_time);
									jobData.setJobID(Integer.parseInt(strParam[0]));
									jobData.setTotalTime(Integer.parseInt(strParam[1]));
									listJob.add(jobData);
									isExecutedLine = true;
								}
								continue;
							case "PrintJob":
								if(strParam.length >= 2) {
									int currentjob1 = Integer.parseInt(strParam[0]);
									int currentjob2 = Integer.parseInt(strParam[1]);
									
									RedBlackImplementation rbt = new RedBlackImplementation();
									int a[] = new int[listJob.size()];
									for(int i = 0; i < listJob.size(); i++) {
										a[i] = listJob.get(i).jobID;
									}
									Arrays.sort(a);
									for(int v: a)
										rbt.insert(v);
									
									Integer next_job1 = rbt.next(currentjob1);
									Integer next_job2 = rbt.next(currentjob2);
									
									String stringWrite = null;
									if(next_job1==null && next_job2==null) {
										if(listJob.size() > 0) {
											stringWrite = "";
											for(int i = 0; i < listJob.size(); i++) {
												if(i > 0) {
													stringWrite += ",";
												}
												stringWrite += String.format("(%d,%d,%d)", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
												if(i == listJob.size() - 1) {
													stringWrite+="\n";
												}
											}
										}
										
									}
									else {
										stringWrite = "";
										if(next_job1!=null) {
											for(int i = 0; i < listJob.size(); i++) {
												if(listJob.get(i).jobID==next_job1) {
													stringWrite += String.format("(%d,%d,%d)", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
													break;
												}
											}
										}
										if(next_job2!=null) {
											for(int i = 0; i < listJob.size(); i++) {
												if(listJob.get(i).jobID==next_job2) {
													stringWrite += String.format("(%d,%d,%d)", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
													break;
												}
											}
										}
										stringWrite+="\n";
									}
									if(stringWrite==null) {
										stringWrite = "(0,0,0)\n";
									}
								    dos.writeBytes(stringWrite);
									isExecutedLine = true;
								}
								else if(strParam.length >= 1) {
									int nPrintJob = Integer.parseInt(strParam[0]);
									
									String stringWrite = null;
									for(int i = 0; i < listJob.size(); i++) {
										if(listJob.get(i).jobID==nPrintJob) {
											stringWrite = String.format("(%d,%d,%d)\n", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
											break;
										}
									}
									if(stringWrite==null) {
										stringWrite = "(0,0,0)\n";
									}
									
									dos.writeBytes(stringWrite);
									
									isExecutedLine = true;
								}
								continue;
							case "NextJob":
								
								if(strParam.length >= 1) {
									int currentjob = Integer.parseInt(strParam[0]);
									
									RedBlackImplementation rbt = new RedBlackImplementation();
									int a[] = new int[listJob.size()];
									for(int i = 0; i < listJob.size(); i++) {
										a[i] = listJob.get(i).jobID;
									}
									Arrays.sort(a);
									for(int v: a)
										rbt.insert(v);
									
									Integer nNextJob = rbt.next(currentjob);
									String stringWrite = null;
									if(nNextJob!=null) {
										for(int i = 0; i < listJob.size(); i++) {
											if(listJob.get(i).jobID==nNextJob) {
												stringWrite = String.format("(%d,%d,%d)\n", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
												break;
											}
										}
									}
									if(stringWrite==null) {
										stringWrite = "(0,0,0)\n";
									}
									
									dos.writeBytes(stringWrite);
									isExecutedLine = true;
								}
								continue;
							case "PreviousJob":
								if(strParam.length >= 1) {
									int currentjob = Integer.parseInt(strParam[0]);
									
									RedBlackImplementation rbt = new RedBlackImplementation();
									int a[] = new int[listJob.size()];
									for(int i = 0; i < listJob.size(); i++) {
										a[i] = listJob.get(i).jobID;
									}
									Arrays.sort(a);
									for(int v: a)
										rbt.insert(v);
									
									Integer nPrevJob = rbt.prev(currentjob);
									String stringWrite = null;
									if(nPrevJob!=null) {
										for(int i = 0; i < listJob.size(); i++) {
											if(listJob.get(i).jobID==nPrevJob) {
												stringWrite = String.format("(%d,%d,%d)\n", listJob.get(i).jobID, listJob.get(i).executedTime, listJob.get(i).totalTime);
												break;
											}
										}
									}
									if(stringWrite==null) {
										stringWrite = "(0,0,0)\n";
									}

									dos.writeBytes(stringWrite);
									isExecutedLine = true;
								}
								
								continue;
							}
						}
						else {
							continue;
						}
					}
				}
				//plus_time
				MinHeapImplementation minHeap = new MinHeapImplementation(listJob.size() + 3);
				RedBlackImplementation rbt = new RedBlackImplementation();
				
				int a[] = new int[listJob.size()];
				for(int i = 0; i < listJob.size(); i++) {
					minHeap.insert(listJob.get(i).executedTime);
					a[i] = listJob.get(i).jobID;
				}
				
				Arrays.sort(a);
				for(int v: a)
					rbt.insert(v);
				
				minHeap.MinHeapImplementation();
				int min_execution_time = -1;
				if(listJob.size()==1) {
					min_execution_time = listJob.get(0).getExecutedTime();
				}
				else {
					min_execution_time = minHeap.remove();
				}
		        for(int i = 0; i < listJob.size(); i++) {
		        	if(listJob.get(i).executedTime==min_execution_time) {
		        		listJob.get(i).executedTime += plus_time;
		        		if(listJob.get(i).executedTime>=listJob.get(i).totalTime) {
		        			listJob.remove(i);
		        		}
		        		break;
		        	}
		        }
			}
			while(line!=null);
			
			dos.close();
			dis.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}


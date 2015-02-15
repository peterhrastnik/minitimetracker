package at.hrastnik.minitimetracker;

import java.util.Date;


public class TaskEntry implements Cloneable{

	private Integer id;

	private String taskId;
    private String taskDescription;
    private Date start;
    private Date finish;

    
    public TaskEntry(String taskId, String description, Date start, Date finish) {
        this.setTaskId(taskId);
        this.setTaskDescription(description);
        this.setStart(start);
        this.setFinish(finish);
        
    }
    
    
    @Override
	protected TaskEntry clone() {
		try {
			return (TaskEntry)super.clone();
		} catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
		
	}



	public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}
 
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

   
    
}

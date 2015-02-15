package at.hrastnik.minitimetracker;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

	
	public void testDerby() throws Exception{
		
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		Class.forName(driver);
		String url = "jdbc:derby:miniTimeTrackerDB;create=true";
		Connection c = DriverManager.getConnection(url);
	}
	
	
	public Connection getConnection() throws Exception {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		Class.forName(driver);
		String url = "jdbc:derby:miniTimeTrackerDB;create=true";
		Connection c = DriverManager.getConnection(url);
		return c;
	}

	
	public List<TaskEntry> getLatestTasks(int count) throws Exception {
	
		Connection conn = this.getConnection();
		
		PreparedStatement stmt = conn.prepareStatement("select * from TASK order by start DESC FETCH FIRST ? ROWS ONLY");
		stmt.setInt(1, count);
		ResultSet rs = stmt.executeQuery();
		;
		List<TaskEntry> latestTasks = new ArrayList<TaskEntry>();
		
		while (rs.next()) {
			TaskEntry task = new TaskEntry(
					rs.getString(2),
					rs.getString(3),
					rs.getTimestamp(4),
					rs.getTimestamp(5)
					);
			task.setId(rs.getInt(1));
			
			latestTasks.add(task);
		}
		
		
		return latestTasks;
	};

	
	public String getLatestDescription(String taskId) throws Exception {
		Connection conn = this.getConnection();
		
		PreparedStatement stmt = conn.prepareStatement("select TASK_DESCRIPTION from TASK where TASK_ID=? ORDER by start DESC FETCH FIRST 1 ROWS ONLY");
	
		stmt.setString(1, taskId);
		
		ResultSet rs = stmt.executeQuery();
		
		String description = null;
		while(rs.next()) {
			description = rs.getString(1);
		}
		return description;
	}
	
	public List<String> getRelevantTaskIds(int maxDaysBack) throws Exception {
		
		Connection conn = this.getConnection();
		
		PreparedStatement stmt = conn.prepareStatement("select DISTINCT(TASK_ID) from TASK");
		//stmt.setInt(1, count);
		ResultSet rs = stmt.executeQuery();
		;
		List<String> relevantTasks = new ArrayList<String>();
		
		while (rs.next()) {
			relevantTasks.add(rs.getString(1));
			
		}
		
		
		return relevantTasks;
	};	
	
	
	public void addRow(TaskEntry taskEntry) throws Exception {
		Connection conn = this.getConnection();
		try {
			
			
			String sql = "INSERT INTO TASK (TASK_ID, TASK_DESCRIPTION, START, FINISH) "
					+ "VALUES (?, ?, ?,  ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql,  new String[] {"ID"});
			
			stmt.setString(1, taskEntry.getTaskId());
			stmt.setString(2, taskEntry.getTaskDescription());
			
			stmt.setTimestamp(3, new Timestamp(taskEntry.getStart().getTime()));
			stmt.setTimestamp(4, taskEntry.getFinish() == null ? null: new Timestamp(taskEntry.getFinish().getTime()));
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			taskEntry.setId(id);
			rs.close();
			stmt.close();
		} finally {
			conn.commit();
			conn.close();
		}

		
	}


	public void update(TaskEntry task) throws Exception {
		Connection conn = this.getConnection();
		try {
			
			
			String sql = "UPDATE TASK set TASK_ID=?, TASK_DESCRIPTION=?, START=?, FINISH=? where ID=? ";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, task.getTaskId());
			stmt.setString(2, task.getTaskDescription());
			stmt.setTimestamp(3, task.getStart() == null ? null: new Timestamp(task.getStart().getTime()));
			stmt.setTimestamp(4, task.getFinish() == null ? null: new Timestamp(task.getFinish().getTime()));
			
			stmt.setInt(5, task.getId());
			
			stmt.executeUpdate();
		
			stmt.close();
		} finally {
			conn.commit();
			conn.close();
		}
		
		
	}
	
}

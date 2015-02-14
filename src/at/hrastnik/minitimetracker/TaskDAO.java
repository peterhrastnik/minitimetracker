package at.hrastnik.minitimetracker;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class TaskDAO {

	
	public void testDerby() throws Exception{
		System.out.println("Testing DERBY");
		
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
//	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
// 	taskId VARCHAR(50) NOT NULL,
// 	taskDescription VARCHAR(500),
// 	start DATE NOT NULL,
// 	finish DATE,
	
	public void addRow(TaskEntry taskEntry) throws Exception {
		Connection conn = this.getConnection();
		
		String sql = "INSERT INTO TASK (taskId, taskDescription, start, finish) "
				+ "VALUES (?, ?, ?,  ?)";
		
		PreparedStatement stmt = conn.prepareStatement(sql,  new String[] {"ID"});
		
		stmt.setString(1, taskEntry.getTaskId());
		stmt.setString(2, taskEntry.getTaskDescription());
		stmt.setTimestamp(3, new Timestamp(taskEntry.getStart().getTime()));
		stmt.setTimestamp(4, null);
		stmt.executeUpdate();
		
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
	
		int id = rs.getInt(1);
		System.out.println(id);
		taskEntry.setId(id);

		
	}
	
}

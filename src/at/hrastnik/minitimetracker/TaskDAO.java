package at.hrastnik.minitimetracker;

import java.sql.Connection;
import java.sql.DriverManager;

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
	
	
	public void addRow(TaskEntry taskEntry) {
		System.out.println("ADDING ROW TO DB");
	}
	
}

package at.hrastnik.minitimetracker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TasksTableModel extends AbstractTableModel {

    private String[]   columnNames = new String[] { "Task", "Start", "End", "Duration" };

    //private Object[][] data = { { "1", "a", "b", "c" }, { "2", "a", "b", "c" }, { "3", "a", "b", "c" }, { "4", "a", "b", "c" } };

 


    private TaskDAO dao = new TaskDAO();
    
    public static int MAX_MODEL_SIZE = 10;


	public void addRow(TaskEntry task) {
   
        
        try {
			this.getDao().addRow(task);
			System.out.println("Added "+ task.getId());
		} catch (Exception e1) {

			e1.printStackTrace();
		}       
        
        fireTableRowsInserted(0, 0);
    }
    
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
    	try {
			List<TaskEntry> taskEntries = this.getDao().getLatestTasks(MAX_MODEL_SIZE);
			return taskEntries.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	if (row >= MAX_MODEL_SIZE) {
    		return null;
    	}
    	String val = "N/A";
    	try {
			List<TaskEntry> latestTasks = this.getDao().getLatestTasks(MAX_MODEL_SIZE);
			
			switch (col) {
			case 0:
				return Utils.nullSafeToString(latestTasks.get(row).getTaskId());
			case 1:
				return Utils.nullSafeToString(latestTasks.get(row).getTaskDescription());
			case 2:
				System.out.println(latestTasks.get(row).getStart());
				return Utils.timestampToString(latestTasks.get(row).getStart());
			case 3:
				System.out.println(latestTasks.get(row).getFinish());
				return Utils.timestampToString(latestTasks.get(row).getFinish());
			default:
				return val;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return val;
 
    }

    
	public TaskEntry getEntryAtRow(int row) throws Exception{
		return this.getDao().getLatestTasks(MAX_MODEL_SIZE).get(row);
	
	}
    
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        //data[row][col] = value;
        fireTableCellUpdated(row, col);
    }


    

    public TaskDAO getDao() {
		return dao;
	}


	public void setDao(TaskDAO dao) {
		this.dao = dao;
	}



    
}

package at.hrastnik.minitimetracker;

import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TasksTableModel extends AbstractTableModel {

    private String[]   columnNames = new String[] { "Task", "Start", "End", "Duration" };

    private TaskDAO dao = new TaskDAO();
    
    public static int MAX_MODEL_SIZE = 10;


	public void addRow(TaskEntry task) {
   
        
        try {
			this.getDao().addRow(task);
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
				return Utils.timestampToString(latestTasks.get(row).getStart());
			case 3:
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

    public boolean isCellEditable(int row, int col) {
            return true;
    }


    public void setValueAt(Object value, int row, int col) {
        
        try {
            TaskEntry task = this.getDao().getLatestTasks(MAX_MODEL_SIZE).get(row);
            
            switch (col) {
            case 0:
                task.setTaskId(value.toString());
                break;
            case 1:
                task.setTaskDescription(value.toString());
                break;
            case 2:
                task.setStart(Utils.StringToTimestamp(value.toString()));
                break;
            case 3:
                task.setFinish(Utils.StringToTimestamp(value.toString()));
                break;
            default:
                break;
            }
            this.getDao().update(task);
            fireTableCellUpdated(row, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    

    public TaskDAO getDao() {
		return dao;
	}


	public void setDao(TaskDAO dao) {
		this.dao = dao;
	}



    
}

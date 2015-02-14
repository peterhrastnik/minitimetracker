package at.hrastnik.minitimetracker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TasksTableModel extends AbstractTableModel {

    private String[]   columnNames = new String[] { "Task", "Start", "End", "Duration" };

    //private Object[][] data = { { "1", "a", "b", "c" }, { "2", "a", "b", "c" }, { "3", "a", "b", "c" }, { "4", "a", "b", "c" } };

    
    private List<TaskEntry> data = new ArrayList<TaskEntry>(); 

    
    
    public void addRow(TaskEntry task) {
        this.getData().add(task);
        fireTableRowsInserted(1, 1);
    }
    
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return this.getData().size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        System.out.println("HALLO !!!!");
        String val = "N/A";
        
        if (col == 0) {
        	return Utils.nullSafeToString(this.getData().get(row).getTaskId());
        } else if (col == 1) {
        	return Utils.nullSafeToString(this.getData().get(row).getTaskDescription());
        } else if (col == 2) {
        	return Utils.nullSafeToString( this.getData().get(row).getStart());
        } else if (col == 3) {        	
        	return Utils.nullSafeToString(this.getData().get(row).getFinish());
        }
        
        return val;
 
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


    
    public List<TaskEntry> getData() {
        return data;
    }


    
    public void setData(List<TaskEntry> data) {
        this.data = data;
    }

}

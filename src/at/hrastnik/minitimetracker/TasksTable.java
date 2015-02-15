package at.hrastnik.minitimetracker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TasksTable extends JTable {

	private MiniTimeTracker miniTimeTracker;
	

	public TasksTable(TasksTableModel tableModel, MiniTimeTracker miniTimeTracker){
		super(tableModel);
		this.setMiniTimeTracker(miniTimeTracker);
		
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		 Component c = super.prepareRenderer(renderer, row, column);

		 if (this.getMiniTimeTracker().getCurrTask() != null) {
			 if (row == 0) {
				 c.setBackground(new Color(180, 255, 180));
			 } else {
				 c.setBackground(Color.WHITE);
			 }
		 };
		 

	     return c;
		
	}
	
	
	public MiniTimeTracker getMiniTimeTracker() {
		return miniTimeTracker;
	}

	public void setMiniTimeTracker(MiniTimeTracker miniTimeTracker) {
		this.miniTimeTracker = miniTimeTracker;
	}

}

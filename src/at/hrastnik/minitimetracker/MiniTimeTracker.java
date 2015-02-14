package at.hrastnik.minitimetracker;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;


public class MiniTimeTracker extends JFrame {


    private TasksTableModel tasksTableModel;
    private JTable table;
    private TaskDAO dao = new TaskDAO();
    
    
    public TaskDAO getDao() {
		return dao;
	}





	public void setDao(TaskDAO dao) {
		this.dao = dao;
	}





	public static void main(String[] args) throws Exception {
        System.out.println( "HALLO");

        
        
        TaskDAO dao = new TaskDAO();
        dao.testDerby();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MiniTimeTracker mtt = new MiniTimeTracker();
                mtt.setVisible(true);
            }
        });
        
    }

    
    
    
    
    MiniTimeTracker() {
        
        this.setTasksTableModel(new TasksTableModel());
        
        this.setTitle("Mini Time Tracker");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
        final JPanel mig = new JPanel(new MigLayout());
        
        //JPanel inputsPanel = new JPanel(new FlowLayout());
        //JPanel tablePanel = new JPanel(new FlowLayout());
        //tablePanel.setSize(600, 400);

        //mig.add(inputsPanel);
        
        
        JTextField  taskId = new JTextField("taskId", 8);
        JTextField  taskDesc = new JTextField("taskDef", 15);;
        JButton     startButton = new JButton("start");
        JButton     stopButton = new JButton("stop");
        
        startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                    System.out.println("HALLO" + e.getID());
                    TaskEntry task = new TaskEntry("taskId", null, new Date(), null);
                    MiniTimeTracker.this.getTasksTableModel().addRow(task);
                    try {
						MiniTimeTracker.this.getDao().addRow(task);
						System.out.println("Added "+ task.getId());
					} catch (Exception e1) {
		
						e1.printStackTrace();
					}
                }
               
            }
        });
        
        this.setTable(new JTable(this.getTasksTableModel()));
        
        mig.add(taskId);
        mig.add(taskDesc);
        mig.add(startButton); 
        mig.add(stopButton, "wrap");
        
        mig.add(this.getTable(), "span");
        
        
        this.getContentPane().add(mig);
        
        setVisible(true);
        
  
    }



    
    public TasksTableModel getTasksTableModel() {
        return tasksTableModel;
    }



    
    public void setTasksTableModel(TasksTableModel tasksTableModel) {
        this.tasksTableModel = tasksTableModel;
    }



    
    public JTable getTable() {
        return table;
    }



    
    public void setTable(JTable table) {
        this.table = table;
    }
    
}

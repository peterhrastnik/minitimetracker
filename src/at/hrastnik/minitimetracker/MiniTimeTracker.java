package at.hrastnik.minitimetracker;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;


public class MiniTimeTracker extends JFrame {


    private TasksTableModel tasksTableModel;
    private JTable table;
    private TaskDAO dao = new TaskDAO();
    
    private JTextField taskId; 
    private JTextField taskDesc;

    private TaskEntry currTask = null;



	public static void main(String[] args) throws Exception {

        
        
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
        
        
        this.setTaskId(new JTextField("taskId", 8));
        this.setTaskDesc(new JTextField("taskDef", 25));
        JButton     startButton = new JButton("start");
        JButton     stopButton = new JButton("stop");
        
        startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                	
                	stopCurrentTask();
                	
                    TaskEntry task = new TaskEntry(
                    		MiniTimeTracker.this.getTaskId().getText(), 
                    		MiniTimeTracker.this.getTaskDesc().getText(), 
                    		new Date(), 
                    		null);
                    MiniTimeTracker.this.startNewTask(task);
                }
               
            }
        });
        
        this.setTable(new JTable(this.getTasksTableModel()));
        this.getTable().setAutoscrolls(true);
        this.getTable().setSize(600, 300);
        
		this.getTable().addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					try {
						TaskEntry doubleClickedTask = MiniTimeTracker.this.getTasksTableModel().getEntryAtRow(row);
						
	                	stopCurrentTask();
						
						TaskEntry newTask = doubleClickedTask.clone();
						newTask.setId(null);
						newTask.setStart(new Date());
						newTask.setFinish(null);
	
						MiniTimeTracker.this.startNewTask(newTask);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
        
        
        
        mig.add(this.getTaskId());
        mig.add(this.getTaskDesc());
        mig.add(startButton); 
        mig.add(stopButton, "wrap");
        
        mig.add(this.getTable(), "span, width 600:600:600");
        
        
        this.getContentPane().add(mig);
        
        setVisible(true);
        
  
    }


	private void stopCurrentTask() {
		if (MiniTimeTracker.this.getCurrTask() != null) {
			MiniTimeTracker.this.getCurrTask().setFinish(new Date());
			try {
				MiniTimeTracker.this.getDao().update(MiniTimeTracker.this.getCurrTask());
			} catch (Exception e1) {
		
				e1.printStackTrace();
			}
		}
	}


	private void startNewTask(TaskEntry newTask) {
		MiniTimeTracker.this.getTasksTableModel().addRow(newTask);
		MiniTimeTracker.this.setCurrTask(newTask);
		MiniTimeTracker.this.getTable().setRowSelectionInterval(0, 0);
		
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
    
    
    
    public TaskDAO getDao() {
		return dao;
	}





	public void setDao(TaskDAO dao) {
		this.dao = dao;
	}





	public JTextField getTaskId() {
		return taskId;
	}





	public void setTaskId(JTextField taskId) {
		this.taskId = taskId;
	}





	public JTextField getTaskDesc() {
		return taskDesc;
	}





	public void setTaskDesc(JTextField taskDesc) {
		this.taskDesc = taskDesc;
	}





	public TaskEntry getCurrTask() {
		return currTask;
	}





	public void setCurrTask(TaskEntry currTask) {
		this.currTask = currTask;
	}







}

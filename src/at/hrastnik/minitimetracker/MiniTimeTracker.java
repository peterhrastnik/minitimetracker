package at.hrastnik.minitimetracker;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.StringUtils;

import net.miginfocom.swing.MigLayout;


public class MiniTimeTracker extends JFrame {


    private TasksTableModel tasksTableModel;
    private TasksTable table;
    private TaskDAO dao = new TaskDAO();
    
    private JTextField taskId; 
    private JTextField taskDesc;

    private TaskEntry currTask = null;



	public static void main(String[] args) throws Exception {

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
        this.setSize(650, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
        final JPanel mig = new JPanel(new MigLayout());
        
        this.setTaskId(new JTextField("PD-", 8));
        this.setTaskDesc(new JTextField("", 25));
        JButton startButton = new JButton("start");
        JButton stopButton = new JButton("stop");
        
        

        this.setTable(new TasksTable(this.getTasksTableModel(), this));
        this.getTable().setAutoscrolls(true);
        this.getTable().setSize(600, 300);
 
        mig.add(this.getTaskId());
        mig.add(this.getTaskDesc());
        mig.add(startButton); 
        mig.add(stopButton, "wrap");
        
        mig.add(this.getTable(), "span, width 600:600:600");
        
        this.addListeners(startButton, stopButton);
        
        this.getContentPane().add(mig);
        
        setVisible(true);
         
    }

	private void nextTask() {
		stopCurrentTask();
		
		if (StringUtils.isEmpty(MiniTimeTracker.this.getTaskDesc().getText())) {
			try {
				MiniTimeTracker.this.getTaskDesc().setText(MiniTimeTracker.this.getDao().getLatestDescription(MiniTimeTracker.this.getTaskId().getText()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		TaskEntry task = new TaskEntry(
				MiniTimeTracker.this.getTaskId().getText(), 
				MiniTimeTracker.this.getTaskDesc().getText(), 
				new Date(), 
				null);
		MiniTimeTracker.this.startNewTask(task);
	}



	private void addListeners(JButton startButton, JButton stopButton) {
		
		//New task return key strokes on text fields
		AbstractAction nextTaskKeyAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				MiniTimeTracker.this.nextTask();
			}
		};
		
		MiniTimeTracker.this.getTaskId().getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start");
		MiniTimeTracker.this.getTaskId().getActionMap().put("start", nextTaskKeyAction);
		
		MiniTimeTracker.this.getTaskDesc().getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start");
		MiniTimeTracker.this.getTaskDesc().getActionMap().put("start", nextTaskKeyAction);
		
		
		
		
		
		//New task on click start
		startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {	
                	MiniTimeTracker.this.nextTask();
                }
               
            }


        });
		
		//stop task on click stop
        stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                if (e.getID() == ActionEvent.ACTION_PERFORMED) {
                	
                	stopCurrentTask();
                	MiniTimeTracker.this.getTable().repaint();
                }
				
			}
		});
	
        //start task on mouse double click on table
		this.getTable().addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					try {
						TaskEntry doubleClickedTask = MiniTimeTracker.this.getTasksTableModel().getEntryAtRow(row);
						
	                	MiniTimeTracker.this.stopCurrentTask();
						
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
        
        //stop task on exit
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                MiniTimeTracker.this.stopCurrentTask();
                e.getWindow().dispose();
            }
        });
        
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
		this.getTable().clearSelection();
		MiniTimeTracker.this.setCurrTask(null);
	}


	private void startNewTask(TaskEntry newTask) {
		MiniTimeTracker.this.getTasksTableModel().addRow(newTask);
		MiniTimeTracker.this.setCurrTask(newTask);
		
		
	}
    
    
    
    public TasksTableModel getTasksTableModel() {
        return tasksTableModel;
    }



    
    public void setTasksTableModel(TasksTableModel tasksTableModel) {
        this.tasksTableModel = tasksTableModel;
    }



    
    public TasksTable getTable() {
        return table;
    }



    
    public void setTable(TasksTable table) {
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

package com21;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ChangePWD extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel old_pwd = null;
	private JLabel new_pwd = null;
	private JLabel renew_pwd = null;
	private JPasswordField fold = null;
	private JPasswordField fnew = null;
	private JPasswordField fre = null;
	private JButton submit = null;
	private Connect connect = null;  //  @jve:decl-index=0:
	private Connection conn = null;
	private JLabel status = null;
	private int emp_id = 0;

	/**
	 * This is the default constructor
	 */
	public ChangePWD(int id) {
		super();
		initialize();
		emp_id = id;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		/*
		 * 
		 * Center Screen
		 * 
		 */
		Dimension d = this.getToolkit().getScreenSize();
	    int screenWidth = d.width;
	    int screenHeight = d.height;
	    int centerX = screenWidth / 2;
	    int centerY = screenHeight / 2;
	    
	    int xPos = centerX - this.getWidth() / 2;
	    int yPos = centerY - this.getHeight() / 2;
	    this.setLocation(xPos, yPos);   
	    //End Center Screen
		//My Code
		connect = new Connect();
		conn = connect.getConn(); 
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			status = new JLabel();
			status.setBounds(new Rectangle(176, 127, 89, 28));
			status.setText("JLabel");
			renew_pwd = new JLabel();
			renew_pwd.setBounds(new Rectangle(19, 86, 59, 26));
			renew_pwd.setText("re-new");
			new_pwd = new JLabel();
			new_pwd.setBounds(new Rectangle(20, 51, 58, 28));
			new_pwd.setText("new pwd");
			old_pwd = new JLabel();
			old_pwd.setBounds(new Rectangle(20, 18, 59, 26));
			old_pwd.setText("old pwd");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(old_pwd, null);
			jContentPane.add(new_pwd, null);
			jContentPane.add(renew_pwd, null);
			jContentPane.add(getFold(), null);
			jContentPane.add(getFnew(), null);
			jContentPane.add(getFre(), null);
			jContentPane.add(getSubmit(), null);
			jContentPane.add(status, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes fold	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFold() {
		if (fold == null) {
			fold = new JPasswordField();
			fold.setBounds(new Rectangle(90, 18, 111, 25));
		}
		return fold;
	}

	/**
	 * This method initializes fnew	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFnew() {
		if (fnew == null) {
			fnew = new JPasswordField();
			fnew.setBounds(new Rectangle(89, 53, 113, 29));
		}
		return fnew;
	}

	/**
	 * This method initializes fre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFre() {
		if (fre == null) {
			fre = new JPasswordField();
			fre.setBounds(new Rectangle(89, 92, 114, 26));
		}
		return fre;
	}

	/**
	 * This method initializes submit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSubmit() {
		if (submit == null) {
			submit = new JButton();
			submit.setBounds(new Rectangle(74, 127, 78, 28));
			submit.setText("submit");
			submit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String sql = "SELECT EMP_PWD FROM EMPLOYEES "+
					"WHERE EMP_ID="+"'"+emp_id+"'";
					
					try {
						Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet rs = stm.executeQuery(sql);
						if(rs.next()){
							String op = rs.getString("EMP_PWD");
							if(op.equals(fold.getText())){
								if(fnew.getText().equals(fre.getText())){
									sql = "UPDATE EMPLOYEES "+
									"SET EMP_PWD = "+"'"+fnew.getText()+"' "+
									"WHERE EMP_ID="+"'"+emp_id+"'";
									
									stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
									rs = stm.executeQuery(sql);
									backend.jTextPass.setText(fnew.getText());
								}else{
									status.setText("พลาดเวิดไม่ตรงกัน");
								}
							}else{
								status.setText("พลาดเวิดไม่ถูกต้อง");
							}
						}
						
						
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						//test.setText("error");
					}
				}
			});
		}
		return submit;
	}

}

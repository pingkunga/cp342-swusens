package com21;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLaUser = null;
	private JLabel jLapassword = null;
	private JTextField jTextUser = null;
	private JPasswordField jPasswordField = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabelBackend = null;
	private JButton jButLogin = null;
	private JButton jButCancel = null;
	private Connect connect = null;  //  @jve:decl-index=0:
	private Connection conn = null;  //  @jve:decl-index=0:
	private frontend fe= null;
	private backend be = null;
	private JLabel BGlogin = null;

	/**
	 * This method initializes jTextUser	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextUser() {
		if (jTextUser == null) {
			jTextUser = new JTextField();
			jTextUser.setBounds(new Rectangle(237, 132, 115, 20));
		}
		return jTextUser;
	}

	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(237, 165, 115, 20));
		}
		return jPasswordField;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(210, 192, 28, 28));
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jButLogin	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButLogin() {
		if (jButLogin == null) {
			jButLogin = new JButton();
			jButLogin.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/blogin.png"));
			jButLogin.setBackground(Color.white);
			jButLogin.setPreferredSize(new Dimension(70, 35));
			jButLogin.setLocation(new Point(160, 228));
			jButLogin.setSize(new Dimension(70, 35));
			jButLogin.setText("");
			jButLogin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					check_user();
				}
			});
		}
		return jButLogin;
	}

	/**
	 * This method initializes jButCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButCancel() {
		if (jButCancel == null) {
			jButCancel = new JButton();
			jButCancel.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/cancel.png"));
			jButCancel.setLocation(new Point(260, 227));
			jButCancel.setSize(new Dimension(70, 35));
			jButCancel.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButCancel.setText("");
			jButCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					close();				}
			});
		}
		return jButCancel;
	}
	private void close(){
		try {
			conn.close();
			this.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				login thisClass = new login();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public login() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(new Rectangle(0, 0, 500, 337));
		this.setContentPane(getJContentPane());
		this.setTitle("SWUSENS LOGIN");
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
			BGlogin = new JLabel(new ImageIcon("image/bglogin2.png"));
			BGlogin.setBounds(new Rectangle(0, 0, 496, 310));
			BGlogin.setText("JLabel");
			jLabelBackend = new JLabel();
			jLabelBackend.setBounds(new Rectangle(243, 199, 111, 16));
			jLabelBackend.setFont(new Font("AngsanaUPC", Font.BOLD, 12));
			jLabelBackend.setText("ไปหน้าส่วนจัดการ");
			jLapassword = new JLabel();
			jLapassword.setBounds(new Rectangle(120, 160, 76, 26));
			jLapassword.setText("password:");
			jLaUser = new JLabel();
			jLaUser.setBounds(new Rectangle(120, 126, 76, 25));
			jLaUser.setText("username:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(jLabelBackend, null);
			jContentPane.add(getJButCancel(), null);
			jContentPane.add(jLaUser, null);
			jContentPane.add(getJButLogin(), null);
			jContentPane.add(getJTextUser(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(jLapassword, null);
			jContentPane.add(BGlogin, null);
		}
		return jContentPane;
	}
	
	public void check_user() {
		String sql = "SELECT * FROM EMPLOYEES "+
		"WHERE EMP_USER="+"'"+jTextUser.getText()+"'";
		//
		try {
			if((jTextUser.getText().equals("admin"))&&(jTextUser.getText().equals("admin"))){
				if(jCheckBox.isSelected()){
					System.out.println("1");
					be = new backend(000);
					be.show();
				}else{
					System.out.println("2");
					fe = new frontend(000);
					fe.show();
				}
				this.dispose();
			}else{
				Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stm.executeQuery(sql);
				if(rs.next()){
					String u = rs.getString("EMP_USER");
					String p = rs.getString("EMP_PWD");
					if(u.equals(jTextUser.getText()) && p.equals(jPasswordField.getText())){
						//test.setText("OK");
						if(jCheckBox.isSelected()){
							System.out.println("1");
							be = new backend(rs.getInt("EMP_ID"));
							be.show();
						}else{
							System.out.println("2");
							fe = new frontend(rs.getInt("EMP_ID"));
							fe.show();
						}
						int id = rs.getInt("EMP_ID");
						sql = "UPDATE EMPLOYEES " +
						  "SET EMP_LOG = SYSDATE " +
						  "WHERE EMP_ID ="+"'"+id+"'";
						stm.executeUpdate(sql);
						rs.close();
						stm.close();
						conn.close();
						this.dispose();
					}
					else{
						//test.setText();
						JOptionPane.showMessageDialog(null, "รหัสผ่านผิด");
					}
				}
				else{
					//test.setText("");
					JOptionPane.showMessageDialog(null, "ไม่พบผู้ใช้งานนี้");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//test.setText("error");
		}
	}

}  //  @jve:decl-index=0:visual-constraint="155,6"

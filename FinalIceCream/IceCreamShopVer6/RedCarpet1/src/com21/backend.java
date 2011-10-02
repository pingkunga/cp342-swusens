package com21;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTabbedPane;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Point;

public class backend extends JFrame {

	private static final long serialVersionUID = 1L;
	private ButtonGroup pickOne = null;  //  @jve:decl-index=0:
	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JTabbedPane jTabbedBack = null;
	private JPanel jPanelSummary = null;
	private JPanel jPanelStock = null;
	private JPanel jPanelSize = null;
	private JPanel jPanelEmployees = null;
	private JPanel jPanel4 = null;
	private JRadioButton jSize = null;
	/**
	 * This method initializes jTabbedBack	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	//ตัวแปรที่จำเป็นต้องใช้
	public static String date = null;
	private String picPath = null;
	private Connect connect = null;  //  @jve:decl-index=0:
	private ResultSetTableModel rm = null;
	private PreparedStatement pstmt = null;
	private Connection conn;  //  @jve:decl-index=0:
	private Statement stmt;  //  @jve:decl-index=0:
	private ResultSet rset;  //  @jve:decl-index=0:
	private JTabbedPane getJTabbedBack() {
		if (jTabbedBack == null) {
			jTabbedBack = new JTabbedPane();
			jTabbedBack.setBounds(new Rectangle(15, 64, 887, 371));
			jTabbedBack.addTab("Summary", null, getJPanelSummary(), null);
			jTabbedBack.addTab("Stock", null, getJPanelStock(), null);
			jTabbedBack.addTab("Size", null, getJPanelSize(), null);
			jTabbedBack.addTab("Promotion", null, getJPanelPromotion(), null);
			jTabbedBack.addTab("Employees", null, getJPanelEmployees(), null);
			jTabbedBack.addTab("Order", null, getJPanel4(), null);
			
			
			//----------------------------------------
			//			ตรวจจับการเปลี่ยน tab
			//----------------------------------------
			jTabbedBack.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					//System.out.println("stateChanged()"); // TODO Auto-generated Event stub stateChanged()
					int sel = jTabbedBack.getSelectedIndex();
					String sql;
					//System.out.println(sel);
					//ตรวจดูว่าจะเลือก tab ไหน
					if(sel == 0){
						initSummary();
					}else if(sel == 1){
						initStock();
					}else if(sel == 2){
						initSize();
					}else if(sel == 3){
						initPromotion();
					}else if(sel == 4){
						initEmp();
					}else if(sel == 5){
						initOrder();
					}
				}
			});
		}
		return jTabbedBack;
	}
	private void initBackEnd(int emp_id){
		String sql = "SELECT * FROM EMPLOYEES "+
		"WHERE EMP_ID="+"'"+emp_id+"'";
		
		try {
			Connection conn = connect.getConn(); 
			Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()){
				jLabelLogOnID.setText(rs.getString("EMP_ID"));
				jLabelLogOn.setText("["+rs.getString("EMP_NAME")+" "+rs.getString("EMP_SURNAME")+"]");
				//String u = rs.getString("EMP_USER");
				//String p = rs.getString("EMP_PWD");
			}
			rs.close();
			stm.close();
			conn.close();
		}catch(Exception e){
			
		}
	}
	//----------------------------------------
	//			เตรียมหน้าสรุปขึ้นมา
	//----------------------------------------
	private void initSummary(){
		String sql = "SELECT SUM(TOTAL) FROM ORDER_HEADS " +
				"WHERE TO_CHAR(ORDER_DATE,'DD/MM/YYYY')= TO_CHAR(SYSDATE, " +
				"'DD/MM/YYYY')";
		jLabelSumOneDay.setText(String.valueOf(getRow(sql,0)));
		
		sql = "SELECT SUM(TOTAL) FROM ORDER_HEADS " +
			"WHERE ORDER_DATE BETWEEN(SYSDATE-7) AND SYSDATE";
		jLabelSumOneWeek.setText(String.valueOf(getRow(sql,0)));
		
		sql = "SELECT SUM(TOTAL) FROM ORDER_HEADS " +
		"WHERE ORDER_DATE BETWEEN(SYSDATE-30) AND SYSDATE";
		jLabelSumOneMonth.setText(String.valueOf(getRow(sql,0)));
		
		//ไอติม 3 รส
		sql = "SELECT COUNT(STOCK_ID), STOCK_ID " +
				"FROM CONSISTS " +
				"WHERE STOCK_ID IN " +
				"(SELECT STOCK_ID " +
				"FROM STOCKS " +
				"WHERE STOCK_TYPE = 1) " +
				"GROUP BY STOCK_ID " +
				"ORDER BY COUNT(STOCK_ID) DESC";
		getMostIce(sql);
	}
	private int getRow(String sql, int mode){
		int ans = 0;
		try{
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				ans = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ans;
	}
	private void getMostIce(String sql){
		try{
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			//1
			if(rs.next()){
				sql = "SELECT STOCK_PIC "+
				"FROM STOCKS "+
				"WHERE STOCK_ID="+"'"+rs.getInt("STOCK_ID")+"'";
				
				int pic_1 = getRow(sql,0);
				
				ImageIcon img =new ImageIcon(UploadBlob.getImage(pic_1,conn));
				jLabelIceFirst.setIcon(resizeImg(img.getImage(),166,144));
			}
			//2
			if(rs.next()){
				sql = "SELECT STOCK_PIC "+
				"FROM STOCKS "+
				"WHERE STOCK_ID="+"'"+rs.getInt("STOCK_ID")+"'";
				
				int pic_1 = getRow(sql,0);
				
				ImageIcon img =new ImageIcon(UploadBlob.getImage(pic_1,conn));
				jLabelIceSecond.setIcon(resizeImg(img.getImage(),166,144));
			}
			//3
			if(rs.next()){
				sql = "SELECT STOCK_PIC "+
				"FROM STOCKS "+
				"WHERE STOCK_ID="+"'"+rs.getInt("STOCK_ID")+"'";
				
				int pic_1 = getRow(sql,0);
				
				ImageIcon img =new ImageIcon(UploadBlob.getImage(pic_1,conn));
				jLabelIceThird.setIcon(resizeImg(img.getImage(),166,144));
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//----------------------------------------
	//			เตรียมหน้า stock
	//----------------------------------------
	private void initStock(){
		String sql = "SELECT S.STOCK_ID, S.STOCK_NAME, T.TYPE_NAME "+
				"FROM STOCKS S JOIN STOCK_TYPES T "+
				"ON(S.STOCK_TYPE = T.TYPE_ID)";
		jTableStock.setModel(retriveTable(sql));
		jComboStockType.removeAllItems();
		jLaStockID.setText("0");
		jTextStockName.setText("");
		jTextPicPath.setText("");
		ImageIcon img =new ImageIcon("image/noicon.jpg");
		jLabelStockImg.setIcon(img);
		jLabel3.setVisible(false);
		jLaStockID.setVisible(false);
		
		sql = "SELECT TYPE_NAME FROM STOCK_TYPES";
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);
			rset.first();
			//int i = 146;
			while(!rset.isAfterLast()){
				jComboStockType.addItem(rset.getString("TYPE_NAME"));
				rset.next();
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//----------------------------------------
	//			เตรียมหน้า size & promotion
	//----------------------------------------
	private void initSize(){
		String sql = "SELECT * FROM SIZES";
		jTableSize.setModel(retriveTable(sql));
		//Clear text
		jLabelSizeID.setText("0");
		jTextSizeName.setText("");
		jTextMaxIce.setText("");
		jTextMaxTopping.setText("");
		jTextSizePrice.setText("");
		ImageIcon img =new ImageIcon("image/noicon.jpg");
		jLabelSizePic.setIcon(img);
		jLabel4.setVisible(false);
		jLabelSizeID.setVisible(false);
		
	}
	private void initPromotion(){
		String sql = "SELECT P.PROMOTION_ID, P.PROMOTION_NAME, S.SIZE_NAME," +
				"P.DISCOUNT, TO_CHAR(P.PROMOTION_START, 'DD MONTH YYYY', 'NLS_CALENDAR=''THAI BUDDHA') PROMOTION_START" +
				", TO_CHAR(P.PROMOTION_END, 'DD MONTH YYYY', 'NLS_CALENDAR=''THAI BUDDHA') PROMOTION_END " +
				"FROM PROMOTIONS P JOIN SIZES S " +
				"ON(P.SIZE_ID = S.SIZE_ID)";
		jTablePro.setModel(retriveTable(sql));
		jComboSize.removeAllItems();
		jLaProID.setText("0");
		jTextFieldProName.setText("");
		jTextAreaProDesc.setText("");
		jTextFieldProDis.setText("");
		jTextFieldStartPro.setText("");
		jTextFieldEndPro.setText("");
		jLabel41.setVisible(false);
		jLaProID.setVisible(false);
		//สร้างให้เลือก
		//pickOne = new ButtonGroup();
		sql = "SELECT SIZE_ID, SIZE_NAME FROM SIZES " +
			"WHERE SIZE_ID NOT IN(SELECT SIZE_ID FROM PROMOTIONS)";
		//pickOne.add(jRadioButton);
		//pickOne.add(jRadioButton1);
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);
			rset.first();
			while(!rset.isAfterLast()){
				jComboSize.addItem(rset.getString("SIZE_NAME"));
				rset.next();
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//----------------------------------------
	//			เตรียมหน้าพนักงานขึ้นมา
	//----------------------------------------
	private void initEmp(){
		String sql = "SELECT EMP_ID, EMP_NAME, EMP_SURNAME, TO_CHAR(EMP_DOB, 'DD MONTH YYYY', 'NLS_CALENDAR=''THAI BUDDHA') " +
				"EMP_DOB, TO_CHAR(EMP_LOG, 'DD MONTH YYYY', 'NLS_CALENDAR=''THAI BUDDHA') EMP_LOG FROM EMPLOYEES";
		//String sql = "SELECT * FROM EMPLOYEES";
		jTableEmp.setModel(retriveTable(sql));
		
		jLaEmpid.setText("0");
		jTextName.setText("");
		jTextSurname.setText("");
		jTextDOB.setText("");
		jTextUser.setText("");
		jTextPass.setText("");
		jTextsyslog.setText("");
		ImageIcon img =new ImageIcon("image/noicon.jpg");
		jLabelEmpPic.setIcon(img);
		jLabel18.setVisible(false);
		jLaEmpid.setVisible(false);
	}
	private void initOrder(){
		String sql = "SELECT OA.ORDER_ID, E.EMP_NAME, E.EMP_SURNAME, " +
				"TO_CHAR(OA.ORDER_DATE,'DD MONTH YYYY HH:MM','NLS_CALENDAR=''THAI BUDDHA') ORDER_DATE, " +
				"OA.TOTAL " +
				"FROM ORDER_HEADS OA JOIN EMPLOYEES E " +
				"ON (OA.EMP_ID = E.EMP_ID)";
		//String sql = "SELECT * FROM EMPLOYEES";
		System.out.println(sql);
		jTableOrder.setModel(retriveTable(sql));
		
		sql = "SELECT EMP_ID, EMP_NAME, EMP_SURNAME FROM EMPLOYEES"; 
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);
			rset.first();
			while(!rset.isAfterLast()){
				jComboBoxEmp.addItem(rset.getString("EMP_ID")+": "+rset.getString("EMP_NAME")+" "+rset.getString("EMP_SURNAME"));
				rset.next();
			}
			rset.close();
			stmt.close();
			conn.close();			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private ResultSetTableModel retriveTable(String sql){
		//TableColumnModel columnNames = {"รหัสพนักงาน", "ชื่อ", "นามสกุล", "วันเกิด", "สถานะ"};
		//ดึงข้อมูลมารอในตาราง
		try {
			conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);
			rm = new ResultSetTableModel(rset);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
	}
	
	private boolean delete(String sql){
		boolean result = true;
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement();
			int s = stmt.executeUpdate(sql);
			if(s!=0){
				result = true;
			}else{
				result = false;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	private void save(String sql){
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(sql);
			//("SELECT EMP_ID, EMP_NAME, EMP_SURNAME, EMP_DOB, EMP_STATUS FROM EMPLOYEES");
			//rm = new ResultSetTableModel(rset);
			//jTableEmp.setTableHeader(tableHeader);
			//jTableEmp = new JTable(rm, columnNames);
			//jTableEmp.setModel(rm);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	//----------------------------------------
	//			เตรียมหน้าพนักงานขึ้นมา
	//----------------------------------------

	/**
	 * This method initializes jPanelSummary	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	
	private JPanel getJPanelSummary() {
		if (jPanelSummary == null) {
			jLabelbg1 = new JLabel();
			jLabelbg1.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg1.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg1.setText("JLabel");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(600, 120, 38, 16));
			jLabel16.setText("บาท");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(600, 75, 38, 16));
			jLabel15.setText("บาท");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(600, 30, 38, 16));
			jLabel14.setText("บาท");
			jLabelSumOneMonth = new JLabel();
			jLabelSumOneMonth.setBounds(new Rectangle(500, 120, 38, 16));
			jLabelSumOneMonth.setText("0");
			jLabelSumOneWeek = new JLabel();
			jLabelSumOneWeek.setBounds(new Rectangle(500, 75, 38, 16));
			jLabelSumOneWeek.setText("0");
			jLabelSumOneDay = new JLabel();
			jLabelSumOneDay.setBounds(new Rectangle(500, 30, 38, 16));
			jLabelSumOneDay.setFont(new Font("Angsana New", Font.BOLD, 24));
			jLabelSumOneDay.setText("0");
			jLabelIceThird = new JLabel();
			jLabelIceThird.setBounds(new Rectangle(423, 224, 130, 110));
			jLabelIceThird.setText("JLabel");
			jLabelIceSecond = new JLabel();
			jLabelIceSecond.setBounds(new Rectangle(257, 225, 130, 110));
			jLabelIceSecond.setText("JLabel");
			jLabelIceFirst = new JLabel();
			jLabelIceFirst.setBounds(new Rectangle(83, 225, 130, 110));
			jLabelIceFirst.setText("JLabel");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(30, 165, 280, 16));
			jLabel13.setText("รสชาดไอศรีมที่ถูกใจ 3 อันดับ");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(30, 120, 280, 16));
			jLabel12.setText("รวมรายได้ใน 1 เดือนที่ผ่านมา");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(30, 75, 280, 16));
			jLabel10.setText("รวมรายได้ของ 7 วันที่ผ่านมา");
			jLabel9 = new JLabel();
			jLabel9.setText("รวมรายได้ใน 1 วัน");
			jLabel9.setBounds(new Rectangle(30, 30, 280, 16));
			jPanelSummary = new JPanel();
			jPanelSummary.setLayout(null);
			jPanelSummary.setName("");
			jPanelSummary.setBackground(Color.white);
			jPanelSummary.add(jLabel9, null);
			jPanelSummary.add(jLabel10, null);
			jPanelSummary.add(jLabel12, null);
			jPanelSummary.add(jLabel13, null);
			jPanelSummary.add(jLabelIceFirst, null);
			jPanelSummary.add(jLabelIceSecond, null);
			jPanelSummary.add(jLabelIceThird, null);
			jPanelSummary.add(jLabelSumOneDay, null);
			jPanelSummary.add(jLabelSumOneWeek, null);
			jPanelSummary.add(jLabelSumOneMonth, null);
			jPanelSummary.add(jLabel14, null);
			jPanelSummary.add(jLabel15, null);
			jPanelSummary.add(jLabel16, null);
			jPanelSummary.add(jLabelbg1, null);
		}
		return jPanelSummary;
	}

	/**
	 * This method initializes jPanelStock	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelStock() {
		if (jPanelStock == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(295, 187, 38, 16));
			jLabel19.setText("JLabel");
			jLabelbg2 = new JLabel();
			jLabelbg2.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg2.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg2.setText("JLabel");
			jLabelPicID = new JLabel();
			jLabelPicID.setBounds(new Rectangle(159, 182, 38, 16));
			jLabelPicID.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabelPicID.setText("0");
			jLabelPicID.setVisible(false);
			jLabelStockImg = new JLabel();
			jLabelStockImg.setBounds(new Rectangle(92, 27, 188, 144));
			jLabelStockImg.setText("");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(330, 150, 40, 16));
			jLabel7.setText("รูป");
			jLaStockID = new JLabel();
			jLaStockID.setBounds(new Rectangle(400, 30, 25, 16));
			jLaStockID.setText("0");
			jLabel311 = new JLabel();
			jLabel311.setBounds(new Rectangle(330, 110, 40, 16));
			jLabel311.setText("ชนืด");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(330, 70, 40, 16));
			jLabel31.setText("ชื่อ");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(330, 30, 40, 16));
			jLabel3.setText("รหัส");
			jPanelStock = new JPanel();
			jPanelStock.setLayout(null);
			jPanelStock.setBackground(Color.white);
			jPanelStock.add(getJPanel6(), null);
			jPanelStock.add(jLabel3, null);
			jPanelStock.add(jLabel31, null);
			jPanelStock.add(jLabel311, null);
			jPanelStock.add(jLaStockID, null);
			jPanelStock.add(getJTextStockName(), null);
			jPanelStock.add(getJComboStockType(), null);
			jPanelStock.add(getJButStockSave(), null);
			jPanelStock.add(getJButStockDelete(), null);
			jPanelStock.add(jLabel7, null);
			jPanelStock.add(getJTextPicPath(), null);
			jPanelStock.add(getJButtonPic(), null);
			jPanelStock.add(jLabelStockImg, null);
			jPanelStock.add(jLabel19, null);
			jPanelStock.add(jLabelPicID, null);
			jPanelStock.add(jLabelbg2, null);
			jPanelStock.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					initStock();
				}
			});
		}
		return jPanelStock;
	}

	/**
	 * This method initializes jPanelSize	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSize() {
		if (jPanelSize == null) {
			jLabelbg3 = new JLabel();
			jLabelbg3.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg3.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg3.setText("");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(280, 195, 38, 16));
			jLabel8.setText("รูป");
			jLabelSizePicID = new JLabel();
			jLabelSizePicID.setBounds(new Rectangle(83, 190, 38, 16));
			jLabelSizePicID.setText("0");
			jLabelSizePicID.setVisible(false);
			jLabelSizePic = new JLabel();
			jLabelSizePic.setBounds(new Rectangle(37, 16, 178, 164));
			jLabelSizePic.setText("");
			jLabelSizeID = new JLabel();
			jLabelSizeID.setBounds(new Rectangle(420, 20, 38, 16));
			jLabelSizeID.setText("0");
			jLabel5111 = new JLabel();
			jLabel5111.setBounds(new Rectangle(280, 160, 111, 16));
			jLabel5111.setText("ราคา");
			jLabel511 = new JLabel();
			jLabel511.setBounds(new Rectangle(280, 125, 127, 26));
			jLabel511.setText("จำนวน Max topping");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(280, 90, 132, 27));
			jLabel51.setText("จำนวน Max ice-cream");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(280, 55, 38, 16));
			jLabel5.setText("ชื่อ");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(280, 20, 38, 16));
			jLabel4.setText("รหัส");
			jPanelSize = new JPanel();
			jPanelSize.setLayout(null);
			jPanelSize.setEnabled(true);
			jPanelSize.setBackground(Color.white);
			jPanelSize.add(getJPanel(), null);
			jPanelSize.add(jLabel4, null);
			jPanelSize.add(jLabel5, null);
			jPanelSize.add(jLabel51, null);
			jPanelSize.add(jLabel511, null);
			jPanelSize.add(jLabel5111, null);
			jPanelSize.add(getJTextSizeName(), null);
			jPanelSize.add(getJTextMaxIce(), null);
			jPanelSize.add(getJTextMaxTopping(), null);
			jPanelSize.add(getJTextSizePrice(), null);
			jPanelSize.add(jLabelSizeID, null);
			jPanelSize.add(getJButSaveSize(), null);
			jPanelSize.add(getJButSizeDel(), null);
			jPanelSize.add(jLabelSizePic, null);
			jPanelSize.add(jLabelSizePicID, null);
			jPanelSize.add(jLabel8, null);
			jPanelSize.add(getJTextSizePicPath(), null);
			jPanelSize.add(getJButtonPicSize(), null);
			jPanelSize.add(jLabelbg3, null);
			jPanelSize.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					initSize();
				}
			});
		}
		return jPanelSize;
	}

	/**
	 * This method initializes jPanelEmployees	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelEmployees() {
		if (jPanelEmployees == null) {
			jLabelbg5 = new JLabel();
			jLabelbg5.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg5.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg5.setText("");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(270, 10, 38, 24));
			jLabel18.setText("รหัส");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(270, 130, 38, 16));
			jLabel11.setText("รูป");
			jLabelEmpPicID = new JLabel();
			jLabelEmpPicID.setBounds(new Rectangle(83, 181, 38, 16));
			jLabelEmpPicID.setEnabled(true);
			jLabelEmpPicID.setText("0");
			jLabelEmpPicID.setVisible(false);
			jLabelEmpPic = new JLabel();
			jLabelEmpPic.setBounds(new Rectangle(27, 15, 204, 145));
			jLabelEmpPic.setText("");
			jLabel611 = new JLabel();
			jLabel611.setBounds(new Rectangle(270, 170, 106, 26));
			jLabel611.setText("เข้าสู่ระบบล่าสุด");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(489, 100, 56, 21));
			jLabel61.setText("รหัสผ่าน");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(270, 100, 61, 25));
			jLabel6.setText("ชื่อผู้ใช้");
			jLaEmpid = new JLabel();
			jLaEmpid.setBounds(new Rectangle(340, 10, 93, 25));
			jLaEmpid.setText("0");
			jLaEmpid.setEnabled(true);
			jLaEmpid.setFont(new Font("AngsanaUPC", Font.BOLD, 18));
			jLaEmpid.setVisible(false);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(270, 70, 63, 27));
			jLabel2.setText("วันเกิด");
			jLabel2.setFont(new Font("AngsanaUPC", Font.BOLD, 18));
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(470, 40, 59, 26));
			jLabel1.setText("นามสกุล");
			jLabel1.setFont(new Font("AngsanaUPC", Font.BOLD, 18));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(270, 40, 60, 26));
			jLabel.setFont(new Font("AngsanaUPC", Font.BOLD, 18));
			jLabel.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel.setText("ชื่อ");
			jPanelEmployees = new JPanel();
			jPanelEmployees.setLayout(null);
			jPanelEmployees.setBackground(Color.white);
			jPanelEmployees.add(getJPanel5(), null);
			jPanelEmployees.add(getJButAddEmp(), null);
			jPanelEmployees.add(jLabel, null);
			jPanelEmployees.add(jLabel1, null);
			jPanelEmployees.add(jLabel2, null);
			jPanelEmployees.add(getJButDelEmp(), null);
			jPanelEmployees.add(jLaEmpid, null);
			jPanelEmployees.add(getJTextName(), null);
			jPanelEmployees.add(getJTextSurname(), null);
			jPanelEmployees.add(getJTextDOB(), null);
			jPanelEmployees.add(getJButDOB(), null);
			jPanelEmployees.add(jLabel6, null);
			jPanelEmployees.add(jLabel61, null);
			jPanelEmployees.add(jLabel611, null);
			jPanelEmployees.add(getJTextsyslog(), null);
			jPanelEmployees.add(getJTextUser(), null);
			jPanelEmployees.add(getJTextPass(), null);
			jPanelEmployees.add(getJButChangePwd(), null);
			jPanelEmployees.add(jLabelEmpPic, null);
			jPanelEmployees.add(jLabelEmpPicID, null);
			jPanelEmployees.add(jLabel11, null);
			jPanelEmployees.add(getJTextEmpPicPath(), null);
			jPanelEmployees.add(getJButtonPicEmp(), null);
			jPanelEmployees.add(jLabel18, null);
			jPanelEmployees.add(jLabelbg5, null);
			jPanelEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					initEmp();
				}
			});
		}
		return jPanelEmployees;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabelbg6 = new JLabel();
			jLabelbg6.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg6.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg6.setText("JLabel");
			jLabelMa = new JLabel();
			jLabelMa.setBounds(new Rectangle(542, 29, 71, 16));
			jLabelMa.setText("จัดการโดย");
			jLabelEnd = new JLabel();
			jLabelEnd.setBounds(new Rectangle(229, 28, 38, 16));
			jLabelEnd.setText("สิ้นสุด");
			jLabelStart = new JLabel();
			jLabelStart.setBounds(new Rectangle(19, 29, 38, 16));
			jLabelStart.setText("เริ่ม");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBackground(Color.white);
			jPanel4.add(getJPanelOrder(), null);
			jPanel4.add(getJTextFieldStart(), null);
			jPanel4.add(getJButtonStart(), null);
			jPanel4.add(jLabelStart, null);
			jPanel4.add(jLabelEnd, null);
			jPanel4.add(getJTextFieldEnd(), null);
			jPanel4.add(getJButtonEnd(), null);
			jPanel4.add(getJComboBoxOA(), null);
			jPanel4.add(jLabelMa, null);
			jPanel4.add(getJComboBoxEmp(), null);
			jPanel4.add(getJButtonSearch(), null);
			jPanel4.add(jLabelbg6, null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	
	
	
	private JPanel jPanel5 = null;
	private JScrollPane jScrollPaneEmp = null;
	private JTable jTableEmp = null;
	private JButton jButAddEmp = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JButton jButDelEmp = null;
	private JLabel jLaEmpid = null;
	private JTextField jTextName = null;
	private JTextField jTextSurname = null;
	public static JTextField jTextDOB = null;
	private JPanel jPanel6 = null;
	private JScrollPane jScrollPaneStock = null;
	private JTable jTableStock = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel31 = null;
	private JLabel jLabel311 = null;
	private JLabel jLaStockID = null;
	private JTextField jTextStockName = null;
	private JComboBox jComboStockType = null;
	private JButton jButStockSave = null;
	private JButton jButStockDelete = null;
	private JPanel jPanelPromotion = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPaneSize = null;
	private JTable jTableSize = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel51 = null;
	private JLabel jLabel511 = null;
	private JLabel jLabel5111 = null;
	private JTextField jTextSizeName = null;
	private JTextField jTextMaxIce = null;
	private JTextField jTextMaxTopping = null;
	private JTextField jTextSizePrice = null;
	private JLabel jLabelSizeID = null;
	private JButton jButSaveSize = null;
	private JButton jButSizeDel = null;
	private JLabel jLabel41 = null;
	private JButton jButProSave = null;
	private JButton jButProDel = null;
	private JLabel jLabel411 = null;
	private JLabel jLabel4111 = null;
	private JLabel jLabel41111 = null;
	private JLabel jLabel411111 = null;
	private JLabel jLabel4111111 = null;
	private JLabel jLabel41111111 = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPanePro = null;
	private JTable jTablePro = null;
	private JLabel jLaProID = null;
	private JTextField jTextFieldProName = null;
	private JScrollPane jScrollPaneProDesc = null;
	private JTextArea jTextAreaProDesc = null;
	private JTextField jTextFieldProDis = null;
	private JComboBox jComboSize = null;
	public static JTextField jTextFieldStartPro = null;
	public static JTextField jTextFieldEndPro = null;
	private JButton jButStartDate = null;
	private JButton jButEndPro = null;
	private JButton jButDOB = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel61 = null;
	private JLabel jLabel611 = null;
	private JTextField jTextsyslog = null;
	private JTextField jTextUser = null;
	public static JPasswordField jTextPass = null;
	private JButton jButChangePwd = null;
	private JPanel jPanelOrder = null;
	private JScrollPane jScrollPaneOrder = null;
	private JTable jTableOrder = null;
	public static JTextField jTextFieldStart = null;
	private JButton jButtonStart = null;
	private JLabel jLabelStart = null;
	private JLabel jLabelEnd = null;
	public static JTextField jTextFieldEnd = null;
	private JButton jButtonEnd = null;
	private JComboBox jComboBoxOA = null;
	private JLabel jLabelMa = null;
	private JComboBox jComboBoxEmp = null;
	private JLabel jLabel7 = null;
	private JTextField jTextPicPath = null;
	private JButton jButtonPic = null;
	private JLabel jLabelStockImg = null;
	private JLabel jLabelPicID = null;
	private JLabel jLabelSizePic = null;
	private JLabel jLabelSizePicID = null;
	private JLabel jLabel8 = null;
	private JTextField jTextSizePicPath = null;
	private JButton jButtonPicSize = null;
	private JLabel jLabelEmpPic = null;
	private JLabel jLabelEmpPicID = null;
	private JLabel jLabel11 = null;
	private JTextField jTextEmpPicPath = null;
	private JButton jButtonPicEmp = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel12 = null;
	private JLabel jLabel13 = null;
	private JLabel jLabelIceFirst = null;
	private JLabel jLabelIceSecond = null;
	private JLabel jLabelIceThird = null;
	private JLabel jLabelSumOneDay = null;
	private JLabel jLabelSumOneWeek = null;
	private JLabel jLabelSumOneMonth = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JLabel jLabelLogOn = null;
	private JButton jButtonLogOut = null;
	private JLabel jLabelLogOnID = null;
	private JLabel jLabel17 = null;
	private JLabel BGmain = null;
	private JLabel jLabel18 = null;
	private JButton jButtonSearch = null;
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.setBounds(new Rectangle(12, 233, 857, 96));
			jPanel5.add(getJScrollPaneEmp(), gridBagConstraints);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPaneEmp	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneEmp() {
		if (jScrollPaneEmp == null) {
			jScrollPaneEmp = new JScrollPane();
			jScrollPaneEmp.setViewportView(getJTableEmp());
		}
		return jScrollPaneEmp;
	}

	/**
	 * This method initializes jTableEmp	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableEmp() {
		if (jTableEmp == null) {
			jTableEmp = new JTable();
			jTableEmp.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					System.out.println(jTableEmp.getSelectedRow());
					System.out.println(jTableEmp.getValueAt(jTableEmp.getSelectedRow(), 0));
					String id = jTableEmp.getValueAt(jTableEmp.getSelectedRow(), 0).toString();
					//----------------------------------------------
					//String sql = "SELECT EMP_ID, EMP_NAME, EMP_SURNAME, EMP_DOB, EMP_USER, EMP_PWD, EMP_LOG FROM EMPLOYEES WHERE EMP_ID ='103'";
					String sql ="SELECT EMP_ID, EMP_NAME, EMP_SURNAME, " +
							"TO_CHAR(EMP_DOB, 'DD MONTH YYYY')\"EMP_DOB\", " +
							"EMP_USER, EMP_PWD, EMP_LOG, EMP_PIC FROM EMPLOYEES " +
							"WHERE EMP_ID ="+"'"+id+"'";
					System.out.println(sql);
					//เอาค่าลงใน FORM
					ResultSet rs = null;
					try {
						Connection conn = connect.getConn(); 
						Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = stmt.executeQuery(sql);
						if(rs.next()){
							jLaEmpid.setText(rs.getString("EMP_ID"));
							jTextName.setText(rs.getString("EMP_NAME"));
							jTextSurname.setText(rs.getString("EMP_SURNAME"));
							jTextDOB.setText(rs.getString("EMP_DOB"));
							jTextUser.setText(rs.getString("EMP_USER"));
							jTextPass.setText(rs.getString("EMP_PWD"));
							jTextsyslog.setText(rs.getString("EMP_LOG"));
							jLabelEmpPicID.setText(rs.getString("EMP_PIC"));
							//แสดงรูป
							//UploadBlob upload = new UploadBlob(); 
							ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("EMP_PIC"),conn));
							jLabelEmpPic.setIcon(resizeImg(img.getImage(),166,144));
							
							jLabel18.setVisible(true);
							jLaEmpid.setVisible(true);
						}
						rs.close();
						stmt.close();
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
				
		}
		return jTableEmp;
	}

	/**
	 * This method initializes jButAddEmp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButAddEmp() {
		if (jButAddEmp == null) {
			jButAddEmp = new JButton();
			jButAddEmp.setBounds(new Rectangle(750, 20, 70, 35));
			jButAddEmp.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsave2.png"));
			jButAddEmp.setText("");
			jButAddEmp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					String id = jLaEmpid.getText();
					String empName = jTextName.getText();
					String empSurname = jTextSurname.getText();
					String dob = jTextDOB.getText();
					String user = jTextUser.getText();
					String pass = jTextPass.getText();
					String sql = null;
					int picture_id = Integer.parseInt(jLabelEmpPicID.getText());
					System.out.println("Pic: "+picture_id);
					String picturePath = jTextEmpPicPath.getText();
					Savepoint savepb = null;
					boolean fail = false;
					int pic = 0;
					try{
						conn.setAutoCommit(false);
						savepb = conn.setSavepoint();
						if(id.equals("0")){
							//แสดงว่ามัน คือ การ insert
							pic = uploadImg(picturePath,0);
							System.out.println("----------:" +pic);
							//แสดงว่ามัน คือ การ insert
							sql = "INSERT INTO EMPLOYEES (EMP_ID, EMP_NAME, EMP_SURNAME, EMP_DOB, EMP_USER," +
									" EMP_PWD, EMP_LOG, EMP_PIC)" +
									"VALUES (EMPLOYEES_SEQ.NEXTVAL, " +
									"'"+empName+"'," +
									"'"+empSurname+"'," +
									"TO_DATE('"+makeYear(dob)+"','DD/MM/YYYY'), " +
									"'"+user+"'," +
									"'"+pass+"'," +
									"SYSDATE," +
									"'"+pic+"')";
							System.out.println(sql);
							//insert
							save(sql);
						}else{
							int tmppic = Integer.parseInt(jLabelEmpPicID.getText());
							//System.out.println("Tmp: "+tmppic);
							pic = uploadImg(picturePath,picture_id);
							
							//แสดงว่ามัน คือ การ edit
							sql = "UPDATE EMPLOYEES " +
								  "SET EMP_NAME = " + "'"+empName+"'," +
										"EMP_SURNAME = "+"'"+empSurname+"'," +
										"EMP_DOB = TO_DATE('"+makeYear(dob)+"','DD/MM/YYYY'), " +
										"EMP_USER = " +"'"+user+"'," +
										"EMP_PWD = " +"'"+pass+"'," +
										"EMP_PIC = " +"'"+tmppic+"'" +
								  "WHERE EMP_ID ="+"'"+id+"'";
							System.out.println(sql);
							save(sql);
						}
						conn.commit();
					}catch(Exception e1){
						fail = true;
						e1.printStackTrace();
					}
					//ถ้ามัน Fail
					if(fail){
						try{
							conn.rollback(savepb);
							conn.commit();
						}catch(Exception e1){
							
							e1.printStackTrace();
						}
					}
					initEmp();
				}
			});
		}
		return jButAddEmp;
	}

	/**
	 * This method initializes jButDelEmp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButDelEmp() {
		if (jButDelEmp == null) {
			jButDelEmp = new JButton();
			jButDelEmp.setBounds(new Rectangle(750, 60, 70, 35));
			jButDelEmp.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bdelete1.png"));
			jButDelEmp.setText("");
			jButDelEmp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ลบพนักงาน
					String id = jTableEmp.getValueAt(jTableEmp.getSelectedRow(), 0).toString();
					System.out.println(id);
					if(id!=null){
						String sql = "SELECT EMP_PIC FROM EMPLOYEES "+
						"WHERE EMP_ID="+"'"+id+"'";
						try {
							int pic = getLastId(sql);
							sql = "DELETE FROM PICTURES WHERE BLOB_ID="+"'"+pic+"'";
							System.out.println(sql);
							delete(sql);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						sql = "DELETE FROM EMPLOYEES WHERE EMP_ID="+"'"+id+"'";
						delete(sql);
						initEmp();
					}else{
						JOptionPane.showMessageDialog(null, "เลือก id");
					}
				
				}

				
			});
		}
		return jButDelEmp;
	}
	/**
	 * This method initializes jTextName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextName() {
		if (jTextName == null) {
			jTextName = new JTextField();
			jTextName.setBounds(new Rectangle(340, 40, 114, 20));
		}
		return jTextName;
	}
	/**
	 * This method initializes jTextSurname	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextSurname() {
		if (jTextSurname == null) {
			jTextSurname = new JTextField();
			jTextSurname.setBounds(new Rectangle(540, 40, 153, 20));
		}
		return jTextSurname;
	}
	/**
	 * This method initializes jTextDOB	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDOB() {
		if (jTextDOB == null) {
			jTextDOB = new JTextField();
			jTextDOB.setBounds(new Rectangle(340, 70, 157, 20));
			jTextDOB.setEditable(false);
		}
		return jTextDOB;
	}
	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.setBounds(new Rectangle(16, 213, 846, 117));
			jPanel6.add(getJScrollPaneStock(), gridBagConstraints1);
		}
		return jPanel6;
	}
	/**
	 * This method initializes jScrollPaneStock	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneStock() {
		if (jScrollPaneStock == null) {
			jScrollPaneStock = new JScrollPane();
			jScrollPaneStock.setViewportView(getJTableStock());
		}
		return jScrollPaneStock;
	}
	/**
	 * This method initializes jTableStock	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableStock() {
		if (jTableStock == null) {
			jTableStock = new JTable();
			jTableStock.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					System.out.println(jTableStock.getSelectedRow());
					System.out.println(jTableStock.getValueAt(jTableStock.getSelectedRow(), 0));
					String id = jTableStock.getValueAt(jTableStock.getSelectedRow(), 0).toString();
					//----------------------------------------------
					//String sql = "SELECT * FROM SIZES WHERE SIZE_ID="+"'"+id+"'";
					String sql = "SELECT S.STOCK_ID, S.STOCK_NAME, T.TYPE_NAME ,S.STOCK_PIC "+
					"FROM STOCKS S JOIN STOCK_TYPES T "+
					"ON(S.STOCK_TYPE = T.TYPE_ID) "+
					"WHERE S.STOCK_ID="+"'"+id+"'";
					//เอาค่าลงใน FORM
					ResultSet rs = null;
					try {
						Connection conn = connect.getConn(); 
						Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = stmt.executeQuery(sql);
						if(rs.next()){
							jLaStockID.setText(rs.getString("STOCK_ID"));
							jTextStockName.setText(rs.getString("STOCK_NAME"));
							jComboStockType.setSelectedItem(rs.getString("TYPE_NAME"));
							jLabelPicID.setText(rs.getString("STOCK_PIC"));
							//แสดงรูป
							//UploadBlob upload = new UploadBlob(); 
							ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("STOCK_PIC"),conn));
							jLabelStockImg.setIcon(resizeImg(img.getImage(),166,144));
							
							jLabel3.setVisible(true);
							jLaStockID.setVisible(true);
						}
						rs.close();
						stmt.close();
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
			});
		}
		return jTableStock;
	}
	 private ImageIcon resizeImg(Image img, int w, int h) {
	        int nw = (w);
	        int nh = (h);
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage dst = new BufferedImage(w, h, type);
	        Graphics2D g2 = dst.createGraphics();
	        g2.drawImage(img, 0, 0, nw, nh, this);
	        g2.dispose();
	        return new ImageIcon(dst);
	    }

	/**
	 * This method initializes jTextStockName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextStockName() {
		if (jTextStockName == null) {
			jTextStockName = new JTextField();
			jTextStockName.setBounds(new Rectangle(400, 70, 174, 20));
		}
		return jTextStockName;
	}
	/**
	 * This method initializes jComboStockType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboStockType() {
		if (jComboStockType == null) {
			jComboStockType = new JComboBox();
			jComboStockType.setBounds(new Rectangle(400, 110, 176, 20));
		}
		return jComboStockType;
	}
	/**
	 * This method initializes jButStockSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButStockSave() {
		if (jButStockSave == null) {
			jButStockSave = new JButton();
			jButStockSave.setBounds(new Rectangle(668, 34, 70, 35));
			jButStockSave.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsave2.png"));
			jButStockSave.setText("");
			jButStockSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//Save Stock
					String id = jLaStockID.getText();
					String stockName = jTextStockName.getText();
					int picture_id = Integer.parseInt(jLabelPicID.getText());
					String type = jComboStockType.getSelectedItem().toString();
					String sql = "SELECT TYPE_ID FROM STOCK_TYPES WHERE TYPE_NAME = "+"'"+type+"'";
					int type_id = getID(sql);
					String picturePath = jTextPicPath.getText();
					Savepoint savepb = null;
					boolean fail = false;
					int pic = 0;
					try{
						conn.setAutoCommit(false);
						savepb = conn.setSavepoint();
						if(id.equals("0")){
							//แสดงว่ามัน คือ การ insert
							pic = uploadImg(picturePath,0);
							
							//ยัดข้อมูลปกติ
							sql = "INSERT INTO STOCKS (STOCK_ID, STOCK_NAME, STOCK_TYPE, STOCK_PIC )" +
									"VALUES (STOCKS_SEQ.NEXTVAL, " +
									"'"+stockName+"'," +
									"'"+type_id+"'," +
									"'"+pic+"')";
							System.out.println(sql);
							//insert
							save(sql);
						}else{
							//แสดงว่ามัน คือ การ edit
							//ลบรูปเก่าออก
							pic = uploadImg(picturePath,picture_id);
							sql = "UPDATE STOCKS " +
								  "SET STOCK_NAME = " + "'"+stockName+"'," +
										"STOCK_TYPE = " +type_id+"," +
										"STOCK_PIC = " +picture_id+" " +
								  "WHERE STOCK_ID = "+id+"";
							System.out.println(sql);
							save(sql);
						}
						conn.commit();
					}catch(Exception e1){
						fail = true;
						e1.printStackTrace();
					}
					//ถ้ามัน Fail
					if(fail){
						try{
							conn.rollback(savepb);
							conn.commit();
						}catch(Exception e1){
							
							e1.printStackTrace();
						}
					}
					initStock();
				}
			});
		}
		return jButStockSave;
	}
	private int uploadImg(String picturePath,int id){
		int pic_id = -1;
		 try {
			 UploadBlob upload = new UploadBlob(); 
			 // load the image from file 
			 InputStream inputStream = new FileInputStream(picturePath); 
			 // store in database 
			 upload.loadBlob(id, picturePath, "jpeg", inputStream, conn); 
			 //upload.close(); 
			 System.out.println("Finished...^^"); 
			 inputStream.close();
			 //ดีง id ออกมา
			 String sql = "SELECT BLOB_ID FROM PICTURES ORDER BY BLOB_ID DESC";
			 pic_id = getLastId(sql);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return pic_id;
	}
	private int getLastId(String sql)throws Exception{
		//conn = connect.getConn(); 
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rse= stmt.executeQuery(sql);
		//rse.last();
		int pic_id = -1;
		if(rse.next()){
			pic_id = rse.getInt(1);
			System.out.println("Pic: "+pic_id);
		}
		rse.close();
		stmt.close();
		//conn.close();
		return pic_id;
	}
	/**
	 * This method initializes jButStockDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButStockDelete() {
		if (jButStockDelete == null) {
			jButStockDelete = new JButton();
			jButStockDelete.setBounds(new Rectangle(668, 110, 70, 35));
			jButStockDelete.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bdelete1.png"));
			jButStockDelete.setText("");
			jButStockDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					String id = jTableStock.getValueAt(jTableStock.getSelectedRow(), 0).toString();
					System.out.println(id);
					if(id!=null){
						String sql = "SELECT STOCK_PIC FROM STOCKS "+
						"WHERE STOCK_ID="+"'"+id+"'";
						try {
							int pic = getLastId(sql);
							sql = "DELETE FROM PICTURES WHERE BLOB_ID="+"'"+pic+"'";
							System.out.println(sql);
							delete(sql);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						sql = "DELETE FROM STOCKS WHERE STOCK_ID="+"'"+id+"'";
						delete(sql);
						initStock();
					}else{
						JOptionPane.showMessageDialog(null, "เลือก id");
					}
				}
			});
		}
		return jButStockDelete;
	}
	/**
	 * This method initializes jPanelPromotion	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelPromotion() {
		if (jPanelPromotion == null) {
			jLabelbg4 = new JLabel();
			jLabelbg4.setBounds(new Rectangle(0, 0, 800, 400));
			jLabelbg4.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGjpanel.png"));
			jLabelbg4.setText("JLabel");
			jLaProID = new JLabel();
			jLaProID.setBounds(new Rectangle(145, 15, 38, 16));
			jLaProID.setText("0");
			jLabel41111111 = new JLabel();
			jLabel41111111.setBounds(new Rectangle(390, 195, 62, 16));
			jLabel41111111.setText("วันสิ้นโปร");
			jLabel4111111 = new JLabel();
			jLabel4111111.setBounds(new Rectangle(30, 195, 88, 16));
			jLabel4111111.setText("วันเริ่มโปร");
			jLabel411111 = new JLabel();
			jLabel411111.setBounds(new Rectangle(30, 165, 50, 16));
			jLabel411111.setText("ส่วนลด");
			jLabel41111 = new JLabel();
			jLabel41111.setBounds(new Rectangle(30, 135, 56, 16));
			jLabel41111.setText("ขนาด");
			jLabel4111 = new JLabel();
			jLabel4111.setBounds(new Rectangle(30, 75, 85, 16));
			jLabel4111.setText("คำอธิบาย");
			jLabel411 = new JLabel();
			jLabel411.setBounds(new Rectangle(30, 45, 25, 16));
			jLabel411.setText("ชื่อ");
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(30, 15, 37, 16));
			jLabel41.setText("รหัส");
			jPanelPromotion = new JPanel();
			jPanelPromotion.setLayout(null);
			jPanelPromotion.setBackground(Color.white);
			jPanelPromotion.add(jLabel41, null);
			jPanelPromotion.add(getJButProSave(), null);
			jPanelPromotion.add(getJButProDel(), null);
			jPanelPromotion.add(jLabel411, null);
			jPanelPromotion.add(jLabel4111, null);
			jPanelPromotion.add(jLabel41111, null);
			jPanelPromotion.add(jLabel411111, null);
			jPanelPromotion.add(jLabel4111111, null);
			jPanelPromotion.add(jLabel41111111, null);
			jPanelPromotion.add(getJPanel1(), null);
			jPanelPromotion.add(jLaProID, null);
			jPanelPromotion.add(getJTextFieldProName(), null);
			jPanelPromotion.add(getJScrollPaneProDesc(), null);
			jPanelPromotion.add(getJTextFieldProDis(), null);
			jPanelPromotion.add(getJComboSize(), null);
			jPanelPromotion.add(getJTextFieldStartPro(), null);
			jPanelPromotion.add(getJTextFieldEndPro(), null);
			jPanelPromotion.add(getJButStartDate(), null);
			jPanelPromotion.add(getJButEndPro(), null);
			jPanelPromotion.add(jLabelbg4, null);
			jPanelPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					initPromotion();
				}
			});
		}
		return jPanelPromotion;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBounds(new Rectangle(16, 231, 842, 96));
			jPanel.add(getJScrollPaneSize(), gridBagConstraints2);
		}
		return jPanel;
	}
	/**
	 * This method initializes jScrollPaneSize	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneSize() {
		if (jScrollPaneSize == null) {
			jScrollPaneSize = new JScrollPane();
			jScrollPaneSize.setViewportView(getJTableSize());
		}
		return jScrollPaneSize;
	}
	/**
	 * This method initializes jTableSize	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableSize() {
		if (jTableSize == null) {
			jTableSize = new JTable();
			jTableSize.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					System.out.println(jTableSize.getSelectedRow());
					System.out.println(jTableSize.getValueAt(jTableSize.getSelectedRow(), 0));
					String id = jTableSize.getValueAt(jTableSize.getSelectedRow(), 0).toString();
					//----------------------------------------------
					String sql = "SELECT * FROM SIZES WHERE SIZE_ID="+"'"+id+"'";
					System.out.println(sql);
					//เอาค่าลงใน FORM
					ResultSet rs = null;
					try {
						//rset = getRow(sql);
						Connection conn = connect.getConn(); 
						Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery(sql);
						if(rs.next()){
							jLabelSizeID.setText(rs.getString("SIZE_ID"));
							jTextSizeName.setText(rs.getString("SIZE_NAME"));
							jTextMaxIce.setText(rs.getString("MAX_ICE_CREAM"));
							jTextMaxTopping.setText(rs.getString("MAX_TOPPING"));
							jTextSizePrice.setText(rs.getString("PRICE"));
							jLabelSizePicID.setText(rs.getString("SIZE_PIC"));
							//แสดงรูป
							//UploadBlob upload = new UploadBlob(); 
							ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("SIZE_PIC"),conn));
							jLabelSizePic.setIcon(resizeImg(img.getImage(),166,144));
						
							jLabel4.setVisible(true);
							jLabelSizeID.setVisible(true);
						}
						rs.close();
						stm.close();
						conn.close();
				
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						 
				}
			});
		}
		return jTableSize;
	}
	/**
	 * This method initializes jTextSizeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextSizeName() {
		if (jTextSizeName == null) {
			jTextSizeName = new JTextField();
			jTextSizeName.setBounds(new Rectangle(420, 55, 182, 19));
		}
		return jTextSizeName;
	}
	/**
	 * This method initializes jTextMaxIce	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextMaxIce() {
		if (jTextMaxIce == null) {
			jTextMaxIce = new JTextField();
			jTextMaxIce.setBounds(new Rectangle(420, 90, 181, 20));
		}
		return jTextMaxIce;
	}
	/**
	 * This method initializes jTextMaxTopping	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextMaxTopping() {
		if (jTextMaxTopping == null) {
			jTextMaxTopping = new JTextField();
			jTextMaxTopping.setBounds(new Rectangle(420, 125, 181, 20));
		}
		return jTextMaxTopping;
	}
	/**
	 * This method initializes jTextSizePrice	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextSizePrice() {
		if (jTextSizePrice == null) {
			jTextSizePrice = new JTextField();
			jTextSizePrice.setBounds(new Rectangle(420, 160, 181, 20));
		}
		return jTextSizePrice;
	}
	/**
	 * This method initializes jButSaveSize	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButSaveSize() {
		if (jButSaveSize == null) {
			jButSaveSize = new JButton();
			jButSaveSize.setBounds(new Rectangle(668, 34, 70, 35));
			jButSaveSize.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsave2.png"));
			jButSaveSize.setText("");
			jButSaveSize.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ดูที่ id ถ้ามีแสดงว่าเก็บการ edit
					String sql = null;
					String id = jLabelSizeID.getText();
					String sizeName = jTextSizeName.getText();
					int maxice = Integer.parseInt(jTextMaxIce.getText());
					int maxtop = Integer.parseInt(jTextMaxTopping.getText());
					int price = Integer.parseInt(jTextSizePrice.getText());
					int picture_id = Integer.parseInt(jLabelSizePicID.getText());
					String picturePath = jTextSizePicPath.getText();
					Savepoint savepb = null;
					boolean fail = false;
					int pic = 0;
					try{
						conn.setAutoCommit(false);
						savepb = conn.setSavepoint();
						if(id.equals("0")){
							//แสดงว่ามัน คือ การ insert
							pic = uploadImg(picturePath,0);
							System.out.println("****:"+pic);
						
							//แสดงว่ามัน คือ การ insert
							sql = "INSERT INTO SIZES(SIZE_ID, SIZE_NAME, MAX_ICE_CREAM, MAX_TOPPING, PRICE, SIZE_PIC) " +
									"VALUES (SIZES_SEQ.NEXTVAL, " +
									"'"+sizeName+"'," +
									"'"+maxice+"'," +
									"'"+maxtop+"'," +
									"'"+price+"'," +
									"'"+pic+"')";
							System.out.println(sql);
							//insert
							save(sql);
						}else{
							//แสดงว่ามัน คือ การ edit
							//ลบรูปเก่าออก
							pic = uploadImg(picturePath,picture_id);
							sql = "UPDATE SIZES " +
								  "SET SIZE_NAME = " + "'"+sizeName+"'," +
										"MAX_ICE_CREAM = " +maxice+"," +
										"MAX_TOPPING = " +maxtop+"," +
										"PRICE = " +price+"," +
										"SIZE_PIC = " +picture_id+" " +
								  "WHERE SIZE_ID = "+id+"";
							System.out.println(sql);
							save(sql);
						}
						conn.commit();
					}catch(Exception e1){
						fail = true;
						e1.printStackTrace();
					}
					//ถ้ามัน Fail
					if(fail){
						try{
							conn.rollback(savepb);
							conn.commit();
						}catch(Exception e1){
							
							e1.printStackTrace();
						}
					}
					initSize();
					
				}
			});
		}
		return jButSaveSize;
	}
	/**
	 * This method initializes jButSizeDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButSizeDel() {
		if (jButSizeDel == null) {
			jButSizeDel = new JButton();
			jButSizeDel.setBounds(new Rectangle(668, 110, 70, 35));
			jButSizeDel.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bdelete1.png"));
			jButSizeDel.setText("");
			jButSizeDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ลบพนักงาน
					String id = jTableSize.getValueAt(jTableSize.getSelectedRow(), 0).toString();
					System.out.println(id);
					if(id!=null){
						String sql = "SELECT SIZE_PIC FROM SIZES "+
						"WHERE SIZE_ID="+"'"+id+"'";
						try {
							int pic = getLastId(sql);
							sql = "DELETE FROM PICTURES WHERE BLOB_ID="+"'"+pic+"'";
							System.out.println(sql);
							delete(sql);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						sql = "DELETE FROM SIZES WHERE SIZE_ID="+"'"+id+"'";
						delete(sql);
						initSize();
					}else{
						JOptionPane.showMessageDialog(null, "เลือก id");
					}
				}
			});
		}
		return jButSizeDel;
	}
	/**
	 * This method initializes jButProSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButProSave() {
		if (jButProSave == null) {
			jButProSave = new JButton();
			jButProSave.setBounds(new Rectangle(500, 45, 75, 35));
			jButProSave.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsave2.png"));
			jButProSave.setText("");
			jButProSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					
					String id = jLaProID.getText();
					String proName = jTextFieldProName.getText();
					String proDesc = jTextAreaProDesc.getText();
					String size = jComboSize.getSelectedItem().toString();
					String sql = "SELECT SIZE_ID FROM SIZES WHERE SIZE_NAME="+"'"+size+"'";
					int size_id = getID(sql);
					int discount = Integer.parseInt(jTextFieldProDis.getText());
					String startPro = jTextFieldStartPro.getText();
					String endPro = jTextFieldEndPro.getText();
					if(id.equals("0")){
						//แสดงว่ามัน คือ การ insert
						sql = "INSERT INTO PROMOTIONS (PROMOTION_ID, PROMOTION_NAME, PROMOTION_DESC, SIZE_ID, DISCOUNT, " +
								"PROMOTION_START, PROMOTION_END)" +
								"VALUES (PROMOTIONS_SEQ.NEXTVAL, " +
								"'"+proName+"'," +
								"'"+proDesc+"'," +
								"'"+size_id+"'," +
								"'"+discount+"'," +
								"TO_DATE('"+makeYear(startPro)+"','DD/MM/YYYY'), " +
								"TO_DATE('"+makeYear(endPro)+"','DD/MM/YYYY'))";
						System.out.println(sql);
						//insert
						save(sql);
					}else{
						//แสดงว่ามัน คือ การ edit
						sql = "UPDATE PROMOTIONS " +
							  "SET PROMOTION_NAME = " + "'"+proName+"'," +
									"PROMOTION_DESC = "+"'"+proDesc+"'," +
									"SIZE_ID = " +"'"+size_id+"'," +
									"DISCOUNT = " +"'"+discount+"'," +
									"PROMOTION_START = TO_DATE('"+makeYear(startPro)+"','DD/MM/YYYY'), " +
									"PROMOTION_END = TO_DATE('"+makeYear(endPro)+"','DD/MM/YYYY') " +
							  "WHERE PROMOTION_ID ="+"'"+id+"'";
						System.out.println(sql);
						save(sql);
					}
					initPromotion();
				}
			});
		}
		return jButProSave;
	}
	private int getID(String sql){
		
		System.out.println(sql);
		int size_id = 0;
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery(sql);
			rset.first();
			while(!rset.isAfterLast()){
				size_id = rset.getInt(1);
				System.out.println(size_id);
				rset.next();
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return size_id;
	}
	/**
	 * This method initializes jButProDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButProDel() {
		if (jButProDel == null) {
			jButProDel = new JButton();
			jButProDel.setBounds(new Rectangle(500, 94, 75, 35));
			jButProDel.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bdelete1.png"));
			jButProDel.setText("");
			jButProDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					String id = jTablePro.getValueAt(jTablePro.getSelectedRow(), 0).toString();
					System.out.println(id);
					if(id!=null){
						String sql = "DELETE FROM PROMOTIONS WHERE PROMOTION_ID="+"'"+id+"'";
						delete(sql);
						initPromotion();
					}else{
						JOptionPane.showMessageDialog(null, "เลือก id");
					}
				}
			});
		}
		return jButProDel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBounds(new Rectangle(14, 224, 852, 90));
			jPanel1.add(getJScrollPanePro(), gridBagConstraints3);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jScrollPanePro	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPanePro() {
		if (jScrollPanePro == null) {
			jScrollPanePro = new JScrollPane();
			jScrollPanePro.setViewportView(getJTablePro());
		}
		return jScrollPanePro;
	}
	/**
	 * This method initializes jTablePro	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTablePro() {
		if (jTablePro == null) {
			jTablePro = new JTable();
			jTablePro.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					System.out.println(jTablePro.getSelectedRow());
					System.out.println(jTablePro.getValueAt(jTablePro.getSelectedRow(), 0));
					String id = jTablePro.getValueAt(jTablePro.getSelectedRow(), 0).toString();
					//----------------------------------------------
					String sql = "SELECT P.PROMOTION_ID, P.PROMOTION_NAME, P.PROMOTION_DESC, S.SIZE_NAME," +
					"P.DISCOUNT, TO_CHAR(P.PROMOTION_START, 'DD MONTH YYYY')\"PROMOTION_START\", TO_CHAR(P.PROMOTION_END, 'DD MONTH YYYY') " +
					"\"PROMOTION_END\"" +
					"FROM PROMOTIONS P JOIN SIZES S " +
					"ON(P.SIZE_ID = S.SIZE_ID) "+
					"WHERE P.PROMOTION_ID="+"'"+id+"'";
					
					//เอาค่าลงใน FORM
					ResultSet rs = null;
					try {
						Connection conn = connect.getConn();
						Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery(sql);
						if(rs.next()){
							jLaProID.setText(rs.getString("PROMOTION_ID"));
							jTextFieldProName.setText(rs.getString("PROMOTION_NAME"));
							jTextAreaProDesc.setText(rs.getString("PROMOTION_DESC"));
							jComboSize.addItem(rs.getString("SIZE_NAME"));
							jComboSize.setSelectedItem(rs.getString("SIZE_NAME"));
							jTextFieldProDis.setText(rs.getString("DISCOUNT"));
							jTextFieldStartPro.setText(rs.getString("PROMOTION_START"));
							jTextFieldEndPro.setText(rs.getString("PROMOTION_END"));
							jLabel41.setVisible(true);
							jLaProID.setVisible(true);

						}
						rs.close();
						stmt.close();
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jTablePro;
	}
	/**
	 * This method initializes jTextFieldProName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldProName() {
		if (jTextFieldProName == null) {
			jTextFieldProName = new JTextField();
			jTextFieldProName.setBounds(new Rectangle(145, 45, 226, 20));
		}
		return jTextFieldProName;
	}
	/**
	 * This method initializes jScrollPaneProDesc	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneProDesc() {
		if (jScrollPaneProDesc == null) {
			jScrollPaneProDesc = new JScrollPane();
			jScrollPaneProDesc.setBounds(new Rectangle(145, 75, 229, 44));
			jScrollPaneProDesc.setViewportView(getJTextAreaProDesc());
		}
		return jScrollPaneProDesc;
	}
	/**
	 * This method initializes jTextAreaProDesc	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaProDesc() {
		if (jTextAreaProDesc == null) {
			jTextAreaProDesc = new JTextArea();
		}
		return jTextAreaProDesc;
	}
	/**
	 * This method initializes jTextFieldProDis	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldProDis() {
		if (jTextFieldProDis == null) {
			jTextFieldProDis = new JTextField();
			jTextFieldProDis.setBounds(new Rectangle(145, 165, 227, 20));
		}
		return jTextFieldProDis;
	}
	/**
	 * This method initializes jComboSize	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboSize() {
		if (jComboSize == null) {
			jComboSize = new JComboBox();
			jComboSize.setBounds(new Rectangle(145, 135, 126, 25));
		}
		return jComboSize;
	}
	/**
	 * This method initializes jTextFieldStartPro	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldStartPro() {
		if (jTextFieldStartPro == null) {
			jTextFieldStartPro = new JTextField();
			jTextFieldStartPro.setBounds(new Rectangle(145, 195, 170, 20));
			jTextFieldStartPro.setEditable(false);
			jTextFieldStartPro.setText("");
		}
		return jTextFieldStartPro;
	}
	/**
	 * This method initializes jTextFieldEndPro	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldEndPro() {
		if (jTextFieldEndPro == null) {
			jTextFieldEndPro = new JTextField();
			jTextFieldEndPro.setBounds(new Rectangle(483, 195, 169, 20));
			jTextFieldEndPro.setText("--/--/--");
		}
		return jTextFieldEndPro;
	}
	/**
	 * This method initializes jButStartDate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButStartDate() {
		if (jButStartDate == null) {
			jButStartDate = new JButton();
			jButStartDate.setBounds(new Rectangle(322, 190, 30, 26));
			jButStartDate.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/calendar1.png"));
			jButStartDate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					DatePicker dp = new DatePicker(1);
					dp.show();
					/*System.out.println("-"+backend.date);
					jTextFieldStartPro.setText(backend.date);
					jTextFieldStartPro.show();*/
				}
			});
		}
		return jButStartDate;
	}
	/**
	 * This method initializes jButEndPro	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButEndPro() {
		if (jButEndPro == null) {
			jButEndPro = new JButton();
			jButEndPro.setBounds(new Rectangle(659, 190, 30, 26));
			jButEndPro.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/calendar1.png"));
			jButEndPro.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					DatePicker dp = new DatePicker(2);
					dp.show();
					System.out.println("-"+backend.date);
					//jTextFieldStartPro.setText(backend.date);
					//jTextFieldStartPro.show();
				}
			});
		}
		return jButEndPro;
	}
	/**
	 * This method initializes jButDOB	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButDOB() {
		if (jButDOB == null) {
			jButDOB = new JButton();
			jButDOB.setBounds(new Rectangle(507, 70, 30, 26));
			jButDOB.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/calendar1.png"));
			jButDOB.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					DatePicker dp = new DatePicker(3);
					dp.show();
					System.out.println("-"+backend.date);
					jTextFieldStartPro.setText(backend.date);
					jTextFieldStartPro.show();
				}
			});
		}
		return jButDOB;
	}
	/**
	 * This method initializes jTextsyslog	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextsyslog() {
		if (jTextsyslog == null) {
			jTextsyslog = new JTextField();
			jTextsyslog.setBounds(new Rectangle(395, 173, 168, 20));
			jTextsyslog.setEditable(false);
		}
		return jTextsyslog;
	}
	/**
	 * This method initializes jTextUser	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextUser() {
		if (jTextUser == null) {
			jTextUser = new JTextField();
			jTextUser.setBounds(new Rectangle(340, 100, 137, 20));
		}
		return jTextUser;
	}
	/**
	 * This method initializes jTextPass	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextPass() {
		if (jTextPass == null) {
			jTextPass = new JPasswordField();
			jTextPass.setBounds(new Rectangle(551, 100, 160, 20));
		}
		return jTextPass;
	}
	/**
	 * This method initializes jButChangePwd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButChangePwd() {
		if (jButChangePwd == null) {
			jButChangePwd = new JButton();
			jButChangePwd.setBounds(new Rectangle(724, 100, 140, 21));
			jButChangePwd.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/chaPass.png"));
			jButChangePwd.setText("");
			jButChangePwd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					int tmp = Integer.parseInt(jLaEmpid.getText());
					if(tmp!=0){
						ChangePWD cpwd = new ChangePWD(tmp);
						cpwd.show();
					}
					
				}
			});
		}
		return jButChangePwd;
	}
	/**
	 * This method initializes jPanelOrder	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelOrder() {
		if (jPanelOrder == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.gridx = 0;
			jPanelOrder = new JPanel();
			jPanelOrder.setLayout(new GridBagLayout());
			jPanelOrder.setBounds(new Rectangle(13, 93, 856, 234));
			jPanelOrder.add(getJScrollPaneOrder(), gridBagConstraints4);
		}
		return jPanelOrder;
	}
	/**
	 * This method initializes jScrollPaneOrder	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneOrder() {
		if (jScrollPaneOrder == null) {
			jScrollPaneOrder = new JScrollPane();
			jScrollPaneOrder.setViewportView(getJTableOrder());
		}
		return jScrollPaneOrder;
	}
	/**
	 * This method initializes jTableOrder	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private orderDetail od = null;
	private JLabel jLabelbg1 = null;
	private JLabel jLabelbg2 = null;
	private JLabel jLabel19 = null;
	private JLabel jLabelbg3 = null;
	private JLabel jLabelbg4 = null;
	private JLabel jLabelbg5 = null;
	private JLabel jLabelbg6 = null;
	private JTable getJTableOrder() {
		if (jTableOrder == null) {
			jTableOrder = new JTable();
			jTableOrder.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					String id = jTableOrder.getValueAt(jTableOrder.getSelectedRow(), 0).toString();
					System.out.println(id);
					int emp_id = Integer.parseInt(id);
					od = new orderDetail(emp_id);
					od.setVisible(true);
				}
			});
		}
		return jTableOrder;
	}
	/**
	 * This method initializes jTextFieldStart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldStart() {
		if (jTextFieldStart == null) {
			jTextFieldStart = new JTextField();
			jTextFieldStart.setBounds(new Rectangle(65, 27, 121, 20));
			jTextFieldStart.setEditable(false);
		}
		return jTextFieldStart;
	}
	/**
	 * This method initializes jButtonStart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setLocation(new Point(191, 22));
			jButtonStart.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/calendar1.png"));
			jButtonStart.setSize(new Dimension(30, 26));
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					DatePicker dp = new DatePicker(4);
					dp.show();
				}
			});
		}
		return jButtonStart;
	}
	/**
	 * This method initializes jTextFieldEnd	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldEnd() {
		if (jTextFieldEnd == null) {
			jTextFieldEnd = new JTextField();
			jTextFieldEnd.setBounds(new Rectangle(275, 26, 121, 20));
			jTextFieldEnd.setEditable(false);
		}
		return jTextFieldEnd;
	}
	/**
	 * This method initializes jButtonEnd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEnd() {
		if (jButtonEnd == null) {
			jButtonEnd = new JButton();
			jButtonEnd.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/calendar1.png"));
			jButtonEnd.setSize(new Dimension(30, 26));
			jButtonEnd.setLocation(new Point(404, 22));
			jButtonEnd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					DatePicker dp = new DatePicker(5);
					dp.show();
				}
			});
		}
		return jButtonEnd;
	}
	/**
	 * This method initializes jComboBoxOA	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxOA() {
		if (jComboBoxOA == null) {
			jComboBoxOA = new JComboBox();
			jComboBoxOA.setBounds(new Rectangle(448, 26, 85, 22));
			String item[] = {"","และ","หรือ"};
			for(int i=0; i<item.length; i++){
				jComboBoxOA.addItem(item[i]);
			}
		}
		return jComboBoxOA;
	}
	/**
	 * This method initializes jComboBoxEmp	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxEmp() {
		if (jComboBoxEmp == null) {
			jComboBoxEmp = new JComboBox();
			jComboBoxEmp.setBounds(new Rectangle(622, 28, 209, 19));
		}
		return jComboBoxEmp;
	}
	/**
	 * This method initializes jTextPicPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextPicPath() {
		if (jTextPicPath == null) {
			jTextPicPath = new JTextField();
			jTextPicPath.setBounds(new Rectangle(400, 150, 131, 20));
		}
		return jTextPicPath;
	}
	/**
	 * This method initializes jButtonPic	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonPic() {
		if (jButtonPic == null) {
			jButtonPic = new JButton();
			jButtonPic.setBounds(new Rectangle(540, 145, 30, 25));
			jButtonPic.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/Folder-icon1.png"));
			jButtonPic.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					File file, directory;
					picPath = "";
					JFileChooser chooser = new JFileChooser();
					int status = chooser.showSaveDialog(null);
					//press open
					if(status==JFileChooser.APPROVE_OPTION){
						file = chooser.getSelectedFile();
						directory = chooser.getCurrentDirectory();
						System.out.println("Directory: "+directory.getName());		
						System.out.println("Name: "+file.getName());
						//show the full path
						System.out.println("Full path: "+file.getAbsolutePath());
						picPath = file.getAbsolutePath();
						jTextPicPath.setText(file.getAbsolutePath());
					}
				}
			});
		}
		return jButtonPic;
	}
	/**
	 * This method initializes jTextSizePicPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextSizePicPath() {
		if (jTextSizePicPath == null) {
			jTextSizePicPath = new JTextField();
			jTextSizePicPath.setBounds(new Rectangle(420, 195, 143, 20));
		}
		return jTextSizePicPath;
	}
	/**
	 * This method initializes jButtonPicSize	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonPicSize() {
		if (jButtonPicSize == null) {
			jButtonPicSize = new JButton();
			jButtonPicSize.setBounds(new Rectangle(570, 190, 30, 25));
			jButtonPicSize.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/Folder-icon1.png"));
			jButtonPicSize.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					File file, directory;
					picPath = "";
					JFileChooser chooser = new JFileChooser();
					int status = chooser.showSaveDialog(null);
					//press open
					if(status==JFileChooser.APPROVE_OPTION){
						file = chooser.getSelectedFile();
						directory = chooser.getCurrentDirectory();
						System.out.println("Directory: "+directory.getName());		
						System.out.println("Name: "+file.getName());
						//show the full path
						System.out.println("Full path: "+file.getAbsolutePath());
						picPath = file.getAbsolutePath();
						jTextSizePicPath.setText(file.getAbsolutePath());
					}
					
				}
			});
		}
		return jButtonPicSize;
	}
	/**
	 * This method initializes jTextEmpPicPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextEmpPicPath() {
		if (jTextEmpPicPath == null) {
			jTextEmpPicPath = new JTextField();
			jTextEmpPicPath.setBounds(new Rectangle(340, 130, 139, 20));
		}
		return jTextEmpPicPath;
	}
	/**
	 * This method initializes jButtonPicEmp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonPicEmp() {
		if (jButtonPicEmp == null) {
			jButtonPicEmp = new JButton();
			jButtonPicEmp.setBounds(new Rectangle(490, 128, 30, 26));
			jButtonPicEmp.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/Folder-icon1.png"));
			jButtonPicEmp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					File file, directory;
					picPath = "";
					JFileChooser chooser = new JFileChooser();
					int status = chooser.showSaveDialog(null);
					//press open
					if(status==JFileChooser.APPROVE_OPTION){
						file = chooser.getSelectedFile();
						directory = chooser.getCurrentDirectory();
						System.out.println("Directory: "+directory.getName());		
						System.out.println("Name: "+file.getName());
						//show the full path
						System.out.println("Full path: "+file.getAbsolutePath());
						picPath = file.getAbsolutePath();
						jTextEmpPicPath.setText(file.getAbsolutePath());
					}
				}
			});
		}
		return jButtonPicEmp;
	}
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				backend thisClass = new backend();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}*/

	/**
	 * This is the default constructor
	 */
	public backend(int emp_id) {
		super();
		initialize(emp_id);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize(int emp_id){
		this.setSize(926, 479);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("SWUSENS ระบบหลังร้าน");
		this.setVisible(true);
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
		initBackEnd(emp_id);
		initSummary();
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BGmain = new JLabel(new ImageIcon("image/BGmain3.png"));
			BGmain.setBounds(new Rectangle(1, 1, 972, 451));
			BGmain.setText("JLabel");
			BGmain.setVisible(true);
			BGmain.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
				}
			});
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(539, 59, 99, 22));
			jLabel17.setFont(new Font("Angsana New", Font.BOLD, 24));
			jLabel17.setForeground(new Color(255, 51, 51));
			jLabel17.setText("รหัสพนักงาน:");
			jLabelLogOnID = new JLabel();
			jLabelLogOnID.setBounds(new Rectangle(642, 56, 28, 25));
			jLabelLogOnID.setFont(new Font("Angsana New", Font.BOLD, 24));
			jLabelLogOnID.setForeground(new Color(255, 51, 51));
			jLabelLogOnID.setText("101");
			jLabelLogOn = new JLabel();
			jLabelLogOn.setBounds(new Rectangle(673, 57, 190, 24));
			jLabelLogOn.setFont(new Font("Angsana New", Font.BOLD, 24));
			jLabelLogOn.setForeground(new Color(255, 51, 51));
			jLabelLogOn.setText("123456789101112131415");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setSize(new Dimension(918, 388));
			jContentPane.add(getJTabbedBack(), null);
			jContentPane.add(jLabelLogOn, null);
			jContentPane.add(getJButtonLogOut(), null);
			jContentPane.add(jLabelLogOnID, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(BGmain, null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jButtonLogOut	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonLogOut() {
		if (jButtonLogOut == null) {
			jButtonLogOut = new JButton();
			jButtonLogOut.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/exit1.png"));
			jButtonLogOut.setSize(new Dimension(30, 30));
			jButtonLogOut.setLocation(new Point(859, 51));
			jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
						close();
					}
					
				}
			});
		}
		return jButtonLogOut;
	}
	private void close(){
		this.dispose();
	}
	/**
	 * This method initializes jButtonSearch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSearch() {
		if (jButtonSearch == null) {
			jButtonSearch = new JButton();
			jButtonSearch.setBounds(new Rectangle(838, 23, 30, 30));
			jButtonSearch.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/Zoom-.png"));
			jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					String sql = "SELECT OA.ORDER_ID, E.EMP_NAME, E.EMP_SURNAME, TO_CHAR(OA.ORDER_DATE,'DD MONTH YYYY HH:MM', 'NLS_CALENDAR=''THAI BUDDHA') ORDER_DATE, OA.TOTAL " +
					"FROM ORDER_HEADS OA JOIN EMPLOYEES E " +
					"ON (OA.EMP_ID = E.EMP_ID)";
					
					if((!(jTextFieldStart.getText().equals("")))&&(!(jTextFieldEnd.getText().equals("")))){
						//System.out.println("get");
						sql = sql + "WHERE TO_CHAR(OA.ORDER_DATE,'DD MONTH YYYY', 'NLS_CALENDAR=''THAI BUDDHA') BETWEEN "+
							"TO_CHAR(TO_DATE('"+makeYear(jTextFieldStart.getText())+"','DD/MM/YYYY'),'DD MONTH YYYY') AND "+
							"TO_CHAR(TO_DATE('"+makeYear(jTextFieldEnd.getText())+"','DD/MM/YYYY')+1,'DD MONTH YYYY') ";
					}
					if(jComboBoxOA.getSelectedIndex()==1){
						sql = sql + "AND ";
						String emp = jComboBoxEmp.getSelectedItem().toString();
						String emp_id = emp.substring(0, emp.indexOf(":"));
						sql = sql + "(E.EMP_ID = '"+emp_id+"')";
					}else if(jComboBoxOA.getSelectedIndex()==2){
						sql = sql + "OR ";
						String emp = jComboBoxEmp.getSelectedItem().toString();
						String emp_id = emp.substring(0, emp.indexOf(":"));
						sql = sql + "(EMP_ID = '"+emp_id+"')";
					}
					
					System.out.println(sql);
					System.out.println(makeYear(jTextFieldStart.getText()));
					jTableOrder.setModel(retriveTable(sql));
					
				}
			});
		}
		return jButtonSearch;
	}
	private String makeYear(String input){
		String day = input.substring(0, input.indexOf("-"));
		System.out.println(day);
		int tmp = input.indexOf("-")+1;
		String month = input.substring(tmp, input.indexOf("-",tmp));
		String mm = null;
		if(month.equals("มกราคม")){
			mm = "01";
		}else if(month.equals("กุมภาพันธ์")){
			mm = "02";
		}else if(month.equals("มีนาคม")){
			mm = "03";
		}else if(month.equals("เมษายน")){
			mm = "04";
		}else if(month.equals("พฤษภาคม")){
			mm = "05";
		}else if(month.equals("มิถุนายน")){
			mm = "06";
		}else if(month.equals("กรกฏาคม")){
			mm = "07";
		}else if(month.equals("สิงหาคม")){
			mm = "08";
		}else if(month.equals("กันยายน")){
			mm = "09";
		}else if(month.equals("ตุลาคม")){
			mm = "10";
		}else if(month.equals("พฤศจิกายน")){
			mm = "11";
		}else if(month.equals("ธันวาคม")){
			mm = "12";
		}
		int tmp2 = input.indexOf("-",tmp);
		System.out.println(month);
		String year = input.substring(tmp2+1);
		System.out.println(year);
		int yyyy = Integer.parseInt(year)-543;
		//int yyyy = Integer.parseInt(year);
		return day+"/"+mm+"/"+String.valueOf(yyyy);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

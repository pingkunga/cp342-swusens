package com21;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JTabbedPane;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.awt.ComponentOrientation;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;


public class frontend extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelSize = null;
	private JPanel jPanelIce = null;
	private JPanel jPanelTop = null;
	//private JCheckBox jCheckBox = null;//  @jve:decl-index=0:
	private JPanel jPanelIceList = null;
	private JScrollPane jScrollPaneSize = null;
	private JScrollPane jScrollPaneIce = null;
	private JScrollPane jScrollPaneTop = null;
	private JButton jButIceNext = null;
	private JButton jButTopNext = null;
	//--------------------------------------
	private Connect connect = null;  //  @jve:decl-index=0:
	private ResultSetTableModel rm = null;
	private PreparedStatement pstmt = null;
	private Connection conn;  //  @jve:decl-index=0:
	private Statement stmt;  //  @jve:decl-index=0:
	private ResultSet rset;  //  @jve:decl-index=0:
	private Validation vt = null;  //  @jve:decl-index=0:
	//เอาไว้เก็บข้อมูลการซื้อในแต่ละถ้วย
	private iceCream ice_cream = null;  //  @jve:decl-index=0:
	private ArrayList<iceCream> productList = new ArrayList<iceCream>();  //  @jve:decl-index=0:
	private DefaultTableModel orderModel = null;
	private boolean size_ok = false;
	private boolean ice_ok = false;
	//--------------------------------------
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(12, 60, 888, 376));
			jTabbedPane.setFont(new Font("AngsanaUPC", Font.BOLD, 24));
			jTabbedPane.addTab("เลือกขนาด", null, getJPanelSize(), null);
			jTabbedPane.addTab("เลือกรส", null, getJPanelIce(), null);
			jTabbedPane.addTab("เลือกท็อปปิ้ง", null, getJPanelTop(), null);
			jTabbedPane.addTab("สรุปรายการ", null, getJPanelOrderSummary(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					//System.out.println("stateChanged()"); // TODO Auto-generated Event stub stateChanged()
					int sel = jTabbedPane.getSelectedIndex();
					//String sql;
					//System.out.println(sel);
					//ตรวจดูว่าจะเลือก tab ไหน
					if(sel == 0){
					}else if(sel == 1){
						/*if(size_ok){
							jTabbedPane.setSelectedIndex(1);
						}*/
					}else if(sel == 2){
						/*if(ice_ok){
							jTabbedPane.setSelectedIndex(2);
						}*/
						//jTabbedPane.setSelectedIndex(0);
					}else if(sel == 3){
						
					}
				}
			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanelSize	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSize() {
		if (jPanelSize == null) {
			jPanelSize = new JPanel();
			jPanelSize.setLayout(null);
			jPanelSize.setBackground(Color.white);
			jPanelSize.add(getJButSizeNext(), null);
			jPanelSize.add(getJScrollPaneSize(), null);
		}
		return jPanelSize;
	}
	//นีบจำนวนแถวที่มีในตารางนั้นๆ
	private int getCount(String sql){
		int rowCount = 0;
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				rowCount = rs.getInt(1);
				System.out.println(rowCount);
			}
			rs.close();
			stmt.close();
			conn.close();
			System.out.println(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rowCount;
	}
	//*******************************************
	//เอาไว้รองรับแบ่งช่อง
	//Size
	private JPanel sizePanel[] = null;
	//picture
	private JLabel sizePic[] = null;
	//เอาไว้เลิอกว่าจะเอาอันไหน
	private JRadioButton sizeRadio[] = null;
	//เอาไว้เก็บ Size_id ซึ่งจะซ่อนเอาไว้
	private JLabel sizeHidden[] = null;
	//บอกราคา
	private JLabel sizePrice[] = null;
	//จัดกลุ่มปุ่ม
	private ButtonGroup sizeGroup = new ButtonGroup();  //  @jve:decl-index=0:
	//Promotion
	private JLabel proHidden[] = null;
	private JLabel proName[] = null;
	private JLabel proDesc[] = null;
	private JLabel proDis[] = null;
	
	//SQL
	String sqlSize = "SELECT COUNT(SIZE_ID) FROM SIZES";
	
	//
	private JPanel jPanelSizeList = null;
	private JButton jButSizeNext = null;
	//*******************************************
	private void initSize(){
		/*size_ok = false;
		ice_ok = false;*/
		jPanelSizeList.removeAll();
		int j = 0;
		//สร้างถ้วยไอติม ^^
		ice_cream = new iceCream();
		//ดูจำนวน Record ในตาราง
		int record = getCount(sqlSize);
		System.out.println(record);
		//สร้าง GUI ตามมา
		sizePanel = new JPanel[record];
		sizeRadio = new JRadioButton[record];
		sizeHidden = new JLabel[record];
		sizePic = new JLabel[record];
		sizePrice = new JLabel[record];
		
		//-----------------------------------
		//int recordPro = getCount(sqlPro);
		proHidden = new JLabel[record];
		proName = new JLabel[record];
		proDesc = new JLabel[record];
		proDis = new JLabel[record];
		//ดึงข้อมูล
		String sql = "SELECT SIZE_NAME, SIZE_ID ,PRICE, SIZE_PIC FROM SIZES ORDER BY SIZE_ID";
		
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			/*if(rs == null){
				System.out.println(rs);
			}*/
			int i = 0;
			while(rs.next()){
				//สร้าง element
				sizePanel[i] = new JPanel();
				sizeRadio[i] = new JRadioButton();
				sizePic[i] = new JLabel();
				sizeHidden[i] = new JLabel();
				sizePrice[i] = new JLabel();
				//ยัดข้อมูล + แต่ง
				sizeRadio[i].setText(rs.getString("SIZE_NAME"));
				sizeRadio[i].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						for(int i = 0; i< getCount(sqlSize);i++){
							if(e.getSource()==sizeRadio[i]){
								System.out.println("<----"+sizeHidden[i].getText()+"---->");
								//JOptionPane.showMessageDialog(null,sizeRadio[i].getText()+" "+sizeHidden[i].getText());
							}
						}
					}
				});
				
				ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("SIZE_PIC"),conn));
				sizePic[i].setIcon(resizeImg(img.getImage(),125,125));
				//ใส่ลงใน Button Group
				sizeGroup.add(sizeRadio[i]);
				sizeRadio[i].setOpaque(false);
				sizeHidden[i].setText(rs.getString("SIZE_ID"));
				sizeHidden[i].setVisible(false);
				//sizeName[i].setText(rs.getString("SIZE_NAME"));
				sizePrice[i].setText("ราคา "+rs.getString("PRICE")+" บาท");
				
				//จีดลงใน Panel
				sizePanel[i].setSize(888, 125);
				sizePanel[i].setLayout(null);
				sizePanel[i].setOpaque(false);
				
				//หาโปรโมชั่นที่เกี่ยวข้อง
				String sqlPro = "SELECT PROMOTION_ID, PROMOTION_NAME, PROMOTION_DESC, DISCOUNT "+
				"FROM PROMOTIONS WHERE (SYSDATE BETWEEN PROMOTION_START AND PROMOTION_END+1) "+
				"AND (SIZE_ID ="+"'"+rs.getString("SIZE_ID")+"')";
				Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1 = stmt1.executeQuery(sqlPro);
				if(rs1.next()){
					proHidden[i] = new JLabel();
					proName[i] = new JLabel();
					proDesc[i] = new JLabel();
					proDis[i] = new JLabel();
					
					proHidden[i].setText(rs1.getString("PROMOTION_ID"));
					proName[i].setText(rs1.getString("PROMOTION_NAME"));
					proDesc[i].setText(rs1.getString("PROMOTION_DESC"));
					proDis[i].setText("ส่วนลด "+rs1.getString("DISCOUNT")+" บาท");
					
				}else{
					proHidden[i] = new JLabel();
					proName[i] = new JLabel();
					proDesc[i] = new JLabel();
					proDis[i] = new JLabel();
					
					proHidden[i].setText("0");
					proHidden[i].setVisible(false);
				}
				proHidden[i].setVisible(false);
				
				sizeHidden[i].setBounds(new Rectangle(43, 25, 34, 26));
				sizePanel[i].add(sizeHidden[i]);
				
				proHidden[i].setBounds(new Rectangle(43, 50, 34, 26));
				sizePanel[i].add(proHidden[i]);
				
				//sizePic[i].setLocation(127, 15);
				sizePic[i].setBounds(127, 15, 125, 125);
				//sizePic[i].setBounds(new Rectangle(127, 15, 200, 200));
				sizePanel[i].add(sizePic[i]);
				
				sizeRadio[i].setBounds(new Rectangle(287, 25, 136, 26));
				sizePanel[i].add(sizeRadio[i]);
				
				proName[i].setBounds(new Rectangle(287, 50, 139, 26));
				sizePanel[i].add(proName[i]);
				
				proDesc[i].setBounds(new Rectangle(478, 50, 156, 26));
				sizePanel[i].add(proDesc[i]);
				
				sizePrice[i].setBounds(new Rectangle(701, 25, 150, 26));
				sizePanel[i].add(sizePrice[i]);
				proDis[i].setBounds(new Rectangle(701, 50, 150, 26));
				sizePanel[i].add(proDis[i]);
				
				
				System.out.println("--:"+jPanelSizeList.getSize().height);
				int k = jPanelSizeList.getHeight()+125;
				//System.out.println("##:"+k);
				jPanelSizeList.setSize(887, k);
				jPanelSizeList.setPreferredSize(new Dimension(887, k));
				System.out.println("pheight"+jPanelSizeList.getHeight());
				System.out.println("scrollheight"+jScrollPaneSize.getHeight());
				if(jPanelSizeList.getHeight() > jScrollPaneSize.getHeight()){
					System.out.println("**"+jPanelSizeList.getHeight());
					jScrollPaneSize.createVerticalScrollBar(); 
				
				}
				//เอาลง Panel หลัก
				sizePanel[i].setLocation(0, j);
				jPanelSizeList.add(sizePanel[i]);
				i++;
				j = j + 125;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method initializes jButSizeNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButSizeNext() {
		if (jButSizeNext == null) {
			jButSizeNext = new JButton();
			jButSizeNext.setBounds(new Rectangle(779, 300, 70, 35));
			jButSizeNext.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bnext.png"));
			jButSizeNext.setText("");
			jButSizeNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ไปเลือก ice-cream
					//ตรวจการกด
					//size_ok = true;
					String sql = null;
					try {
						
						if(sizeGroup.getSelection()!=null){
							for(int i=0 ; i<getCount(sqlSize);i++){
								if(sizeRadio[i].isSelected() == true){
									//ดึงค่าจาก Hidden Size มา
									//เอาค่า 
									//1.size_id, size_name, price, max_ice_cream, max_topping
									sql = "SELECT SIZE_NAME, PRICE, "+
										"MAX_ICE_CREAM, MAX_TOPPING "+
										"FROM SIZES "+
										"WHERE SIZE_ID ="+"'"+sizeHidden[i].getText()+"'";
									Connection conn = connect.getConn(); 
									Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
									ResultSet rs = stmt.executeQuery(sql);
									if(rs.next()){
										ice_cream.setSize_id(Integer.parseInt(sizeHidden[i].getText()));
										ice_cream.setSize_name(rs.getString("SIZE_NAME"));
										ice_cream.setPrice(rs.getInt("PRICE"));
										ice_cream.setMax_ice_cream(rs.getInt("MAX_ICE_CREAM"));
										jLaMaxIce.setText(rs.getString("MAX_ICE_CREAM"));
										ice_cream.setMax_topping(rs.getInt("MAX_TOPPING"));
										jLaMaxTop.setText(rs.getString("MAX_TOPPING"));
									}
									
									//2.pro_id, dis_count
									if(!proHidden[i].getText().equals("0")){
										sql ="SELECT DISCOUNT " +
											"FROM PROMOTIONS " +
											"WHERE PROMOTION_ID = "+"'"+proHidden[i].getText()+"'";
										rs = stmt.executeQuery(sql);
										if(rs.next()){
											ice_cream.setPro_id(Integer.parseInt(proHidden[i].getText()));
											ice_cream.setDiscount(rs.getInt("DISCOUNT"));
										}
									}else{
										ice_cream.setPro_id(0);
										ice_cream.setDiscount(0);
									}
									System.out.println("<-------------------->");
									System.out.println(ice_cream.getSize_id());
									System.out.println(ice_cream.getSize_name());
									System.out.println(ice_cream.getPrice());
									System.out.println(ice_cream.getMax_ice_cream());
									System.out.println(ice_cream.getMax_topping());
									System.out.println(ice_cream.getPro_id());
									System.out.println(ice_cream.getDiscount());
									System.out.println("<-------------------->");
									rs.close();
									stmt.close();
									conn.close();
								}
							}
							jTabbedPane.setSelectedIndex(1);
							initIce();
							
						}else{
							//แสดงว่าไม่ได้เลือก ไปเลือกใหม่ซะ
							JOptionPane.showMessageDialog(null, "เลือก");
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButSizeNext;
	}
	
	/**
	 * This method initializes jPanelIce	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelIce() {
		if (jPanelIce == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(734, 310, 47, 16));
			jLabel9.setText("รสชาติ");
			jLaMaxIce = new JLabel();
			jLaMaxIce.setBounds(new Rectangle(703, 310, 20, 16));
			jLaMaxIce.setHorizontalAlignment(SwingConstants.CENTER);
			jLaMaxIce.setHorizontalTextPosition(SwingConstants.CENTER);
			jLaMaxIce.setText("n");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(663, 310, 29, 16));
			jLabel8.setText("จาก");
			jLaMinIce = new JLabel();
			jLaMinIce.setBounds(new Rectangle(629, 310, 21, 16));
			jLaMinIce.setHorizontalAlignment(SwingConstants.CENTER);
			jLaMinIce.setHorizontalTextPosition(SwingConstants.CENTER);
			jLaMinIce.setText("0");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(541, 310, 77, 16));
			jLabel7.setText("เลือกไปแล้ว");
			jPanelIce = new JPanel();
			jPanelIce.setLayout(null);
			jPanelIce.setFont(new Font("Angsana New", Font.PLAIN, 24));
			jPanelIce.setBackground(Color.white);
			jPanelIce.add(getJScrollPaneIce(), null);
			jPanelIce.add(getJButIceNext(), null);
			jPanelIce.add(jLabel7, null);
			jPanelIce.add(jLaMinIce, null);
			jPanelIce.add(jLabel8, null);
			jPanelIce.add(jLaMaxIce, null);
			jPanelIce.add(jLabel9, null);
			
		}
		return jPanelIce;
	}
	
	//เอาไว้เลือก
	//เอาไว้เก็บ Stock_id_ice ซึ่งจะซ่อนเอาไว้
	private int iceHidden[] = null;
	private JButton icePic[] = null;
	//บอกว่าขนาดใด
	private boolean iceCheck[] = null;
	//private JTextField iceCount[] = null;
	//sql
	String sqlIce = "SELECT COUNT(STOCK_ID) FROM STOCKS WHERE STOCK_TYPE = 1";  //  @jve:decl-index=0:
	//เลิอกรส
	private int iceCount = 0;
	private void initIce(){
		jPanelIceList.removeAll();
		iceCount = 0;
		jLaMinIce.setText("0");
		int record = getCount(sqlIce);
		System.out.println(record);
		//สร้าง GUI ตามมา
		iceHidden = new int[record];
		icePic = new JButton[record];
		iceCheck = new boolean[record];
		String sql = "SELECT STOCK_ID, STOCK_NAME, STOCK_PIC FROM STOCKS WHERE STOCK_TYPE = 1";
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()){
				//สร้าง element
				//sizeRadio[i].setVisible(true);
				iceHidden[i] = rs.getInt("STOCK_ID");
				iceCheck[i] = false; //บอกว่ายังไม่ถูกเลือก
				icePic[i] = new JButton();
				icePic[i].setActionCommand(String.valueOf(i));
				icePic[i].setBackground(new Color(238, 238, 238));
				icePic[i].setToolTipText(rs.getString("STOCK_NAME"));
				icePic[i].setSize(125, 125);
				ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("STOCK_PIC"),conn));
				icePic[i].setIcon(resizeImg(img.getImage(),135,135));
				//icePic[i].setIcon(img);
				icePic[i].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//ตรวจว่ามันถูกเลือก หรือไม่
						System.out.println("e.getID "+e.getActionCommand());
						int k = Integer.parseInt(e.getActionCommand());
						if(iceCheck[k]){
							icePic[k].setBackground(new Color(238, 238, 238));
							iceCheck[k] = false;
							iceCount--;
						}else{
							//เพิ่มขอบ
							icePic[k].setBackground(new Color(255, 51, 51));
							//iceCheck[k].
							iceCheck[k] = true;
							iceCount++;
							//มาร์ใน Boolean ว่ามีแล้ว
						}
						jLaMinIce.setText(String.valueOf(iceCount));
					}
				});
				
				//jPanelIceList.add(icePic[i],gc);
				jPanelIceList.add(icePic[i]);
				System.out.println("Press "+i);
				i++;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	//เอาไว้รองรับแบ่งช่อง
	private int topHidden[] = null;
	private JButton topPic[] = null;
	//บอกว่าขนาดใด
	private boolean topCheck[] = null;
	private JPanel topPanel[] = null;
	//เอาไว้เลือก
	private JCheckBox topCheckBox[] = null;
	//เอาไว้เก็บ Stock_id_ice ซึ่งจะซ่อนเอาไว้
	//บอกว่าขนาดใด
	//private JLabel sizeName[] = null;
	//เก็บจำนวน
	//private JTextField topCount[] = null;
	//sql
	String sqlTop = "SELECT COUNT(STOCK_ID) FROM STOCKS WHERE STOCK_TYPE = 2";  //  @jve:decl-index=0:
	private JPanel jPanelTopList = null;
	private JPanel jPanelOrderSummary = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTableOrder = null;
	private JButton jButOrderAgain = null;
	private JButton jButOderProceed = null;
	private JLabel jLabelTotalPrice = null;
	private JLabel jLabelTotalDiscount = null;
	private JLabel jLabelTotalNet = null;
	private JButton jButtonOrderDel = null;
	private JLabel jLabel = null;
	private JLabel jLabelLogOnID = null;
	private JLabel jLabelLogOn = null;
	private JButton jButtonLogOut = null;
	private GridBagLayout g1;
	private GridBagConstraints gc;  //  @jve:decl-index=0:
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLaMinIce = null;
	private JLabel jLabel8 = null;
	private JLabel jLaMaxIce = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLaMinTop = null;
	private JLabel jLabel11 = null;
	private JLabel jLaMaxTop = null;
	private JLabel jLabel12 = null;
	//เลิอกรส
	private int topCount = 0;
	private JLabel jLabelbg = null;
	private void initTop(){
		jPanelTopList.removeAll();
		topCount = 0;
		jLaMinTop.setText("0");
		int record = getCount(sqlIce);
		System.out.println(record);
		//สร้าง GUI ตามมา
		topHidden = new int[record];
		topPic = new JButton[record];
		topCheck = new boolean[record];
		
		String sql = "SELECT STOCK_ID, STOCK_NAME, STOCK_PIC FROM STOCKS WHERE STOCK_TYPE = 2";
		try {
			Connection conn = connect.getConn(); 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()){
				//สร้าง element
				//sizeRadio[i].setVisible(true);
				topHidden[i] = rs.getInt("STOCK_ID");
				topCheck[i] = false; //บอกว่ายังไม่ถูกเลือก
				topPic[i] = new JButton();
				topPic[i].setActionCommand(String.valueOf(i));
				topPic[i].setBackground(new Color(238, 238, 238));
				topPic[i].setToolTipText(rs.getString("STOCK_NAME"));
				topPic[i].setSize(125, 125);
				ImageIcon img =new ImageIcon(UploadBlob.getImage(rs.getInt("STOCK_PIC"),conn));
				topPic[i].setIcon(resizeImg(img.getImage(),135,135));
				//icePic[i].setIcon(img);
				topPic[i].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//ตรวจว่ามันถูกเลือก หรือไม่
						System.out.println("e.getID "+e.getActionCommand());
						int k = Integer.parseInt(e.getActionCommand());
						if(topCheck[k]){
							topPic[k].setBackground(new Color(238, 238, 238));
							topCheck[k] = false;
							topCount--;
						}else{
							//เพิ่มขอบ
							topPic[k].setBackground(new Color(255, 51, 51));
							//iceCheck[k].
							topCheck[k] = true;
							//มาร์ใน Boolean ว่ามีแล้ว
							topCount++;
						}
						jLaMinTop.setText(String.valueOf(topCount));
					}
				});
				
				//jPanelIceList.add(icePic[i],gc);
				jPanelTopList.add(topPic[i]);
				System.out.println("Press "+i);
				i++;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * This method initializes jPanelSizeList	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSizeList() {
		if (jPanelSizeList == null) {
			jPanelSizeList = new JPanel();
			jPanelSizeList.setLayout(null);
			jPanelSizeList.setBackground(new Color(255, 255, 153));
			jPanelSizeList.setOpaque(false);
			//jPanelSizeList.setSize(887, 5000);
			//jPanelSizeList.setPreferredSize(new Dimension(887, 5000));
		}
		return jPanelSizeList;
	}

	
	/**
	 * This method initializes jPanelTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(734, 310, 47, 16));
			jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel12.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabel12.setText("รสชาติ");
			jLaMaxTop = new JLabel();
			jLaMaxTop.setBounds(new Rectangle(703, 310, 20, 16));
			jLaMaxTop.setHorizontalAlignment(SwingConstants.CENTER);
			jLaMaxTop.setHorizontalTextPosition(SwingConstants.CENTER);
			jLaMaxTop.setText("n");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(663, 310, 29, 16));
			jLabel11.setText("จาก");
			jLaMinTop = new JLabel();
			jLaMinTop.setBounds(new Rectangle(629, 310, 21, 16));
			jLaMinTop.setHorizontalAlignment(SwingConstants.CENTER);
			jLaMinTop.setHorizontalTextPosition(SwingConstants.CENTER);
			jLaMinTop.setText("0");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(541, 310, 77, 16));
			jLabel10.setText("เลือกไปแล้ว");
			jPanelTop = new JPanel();
			jPanelTop.setLayout(null);
			jPanelTop.setBackground(Color.white);
			jPanelTop.add(getJScrollPaneTop(), null);
			jPanelTop.add(getJButTopNext(), null);
			jPanelTop.add(jLabel10, null);
			jPanelTop.add(jLaMinTop, null);
			jPanelTop.add(jLabel11, null);
			jPanelTop.add(jLaMaxTop, null);
			jPanelTop.add(jLabel12, null);
		}
		return jPanelTop;
	}


	/**
	 * This method initializes jPanelIceList	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelIceList() {
		if (jPanelIceList == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(4);
			gridLayout1.setColumns(6);
			jPanelIceList = new JPanel();
			//jPanelIceList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanelIceList.setLayout(gridLayout1);
			jPanelIceList.setOpaque(false);
		}
		return jPanelIceList;
	}

	/**
	 * This method initializes jScrollPaneSize	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneSize() {
		if (jScrollPaneSize == null) {
			jScrollPaneSize = new JScrollPane();
			jScrollPaneSize.setBounds(new Rectangle(1, 2, 880, 299));
			jScrollPaneSize.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPaneSize.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPaneSize.setViewportView(getJPanelSizeList());
		}
		return jScrollPaneSize;
	}

	/**
	 * This method initializes jScrollPaneIce	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneIce() {
		if (jScrollPaneIce == null) {
			jScrollPaneIce = new JScrollPane();
			jScrollPaneIce.setBounds(new Rectangle(0, 2, 883, 297));
			jScrollPaneIce.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPaneIce.setViewportView(getJPanelIceList());
		}
		return jScrollPaneIce;
	}

	/**
	 * This method initializes jScrollPaneTop	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneTop() {
		if (jScrollPaneTop == null) {
			jScrollPaneTop = new JScrollPane();
			jScrollPaneTop.setBounds(new Rectangle(1, 1, 880, 301));
			jScrollPaneTop.setViewportView(getJPanelTopList());
		}
		return jScrollPaneTop;
	}

	/**
	 * This method initializes jButIceNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButIceNext() {
		if (jButIceNext == null) {
			jButIceNext = new JButton();
			jButIceNext.setBounds(new Rectangle(801, 300, 70, 35));
			jButIceNext.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bnext.png"));
			jButIceNext.setText("");
			jButIceNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ตรวจสอบก่ิอนว่าเลือกตรวจตามข้อกำหนดปะ
					//สร้าง Ice-cream ตามที่กำหนดไว่
					//อ่านค่าใน TextBox ที่มีการ Check เก็บลง Array
					int count = 0;
					//ice_ok = true;
					ice_cream.initIce();
					for(int i = 0; i< getCount(sqlIce);i++){
						if(iceCheck[i]){
							count++;
						}
					}
					if(count==ice_cream.getMax_ice_cream()){
						int idx = 0;
						for(int i = 0; i< getCount(sqlIce);i++){
							if(iceCheck[i]){
								//ice_cream.setIce(Integer.parseInt(iceHidden[i].getText()), idx);
								ice_cream.setIce(iceHidden[i], idx);
								idx++;
							}
						}
						jTabbedPane.setSelectedIndex(2);
						initTop();
					}else if(count<ice_cream.getMax_ice_cream()){
						ice_cream.initIce();
						JOptionPane.showMessageDialog(null, "กรุณาเลือกให้ครบ");
					}else{
						ice_cream.initIce();
						JOptionPane.showMessageDialog(null, "คุณเลือกเกินจากกำหนด");
					}
				}
			});
		}
		return jButIceNext;
	}
	
	

	/**
	 * This method initializes jButTopNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButTopNext() {
		if (jButTopNext == null) {
			jButTopNext = new JButton();
			jButTopNext.setBounds(new Rectangle(801, 300, 70, 35));
			jButTopNext.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsubmit.png"));
			jButTopNext.setText("");
			jButTopNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//add Topping
					int count = 0;
					ice_cream.initTop();
					for(int i = 0; i< getCount(sqlTop);i++){
						if(topCheck[i]){
							count++;
						}
					}
					if(count==ice_cream.getMax_topping()){
						int idx = 0;
						for(int i = 0; i< getCount(sqlTop);i++){
							if(topCheck[i]){
								ice_cream.setTop(topHidden[i], idx);
								idx++;
							}
						}
						//เก็บข้อมูลลงใน 
						productList.add(ice_cream);
						//เปลี่ยน Tab
						jTabbedPane.setSelectedIndex(3);
						//เรียก initOrder ขึ้นมาสรุปรายการ
						initOrder();
					}else if(count<ice_cream.getMax_ice_cream()){
						ice_cream.initIce();
						JOptionPane.showMessageDialog(null, "กรุณาเลือกให้ครบ");
					}else{
						ice_cream.initIce();
						JOptionPane.showMessageDialog(null, "คุณเลือกเกินจากกำหนด");
					}
					
				}
			});
		}
		return jButTopNext;
	}
	private void initOrder(){
		//productList.
		String order[] = {"ลำดับที่","รายการสินค้า","ราคา"};
		int sumPrice = 0;
		int sumDiscount = 0;
		int i = 1;
		orderModel = new DefaultTableModel(order,0) {
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		 Iterator<iceCream> it=productList.iterator();
	        while(it.hasNext()){
	        	iceCream cup =it.next();
	        	System.out.println("----------------------------");
	            System.out.println("Size "+cup.getSize_name());
	            System.out.println("----------------------------");
	            String tmp[] = {String.valueOf(i),"ไอศครีมขนาด "+cup.getSize_name(),String.valueOf(cup.getPrice())};
	            //jTableOrder.
	            sumPrice = sumPrice + cup.getPrice();
	            if(cup.getPro_id()!=0){
	            	sumDiscount = sumDiscount + cup.getDiscount();
	            }
	            orderModel.addRow(tmp);
	            i++;
	        }
	        jTableOrder.setModel(orderModel);
	        //jTableOrder.co
	        jLabelTotalPrice.setText(String.valueOf(sumPrice));
	        jLabelTotalDiscount.setText(String.valueOf(sumDiscount));
	        jLabelTotalNet.setText(String.valueOf(sumPrice-sumDiscount));
	}
	 

	/**
	 * This method initializes jPanelTopList	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTopList() {
		if (jPanelTopList == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			gridLayout.setColumns(3);
			jPanelTopList = new JPanel();
			jPanelTopList.setLayout(gridLayout);
			jPanelIceList.setOpaque(false);
		}
		return jPanelTopList;
	}

	/**
	 * This method initializes jPanelOrderSummary	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelOrderSummary() {
		if (jPanelOrderSummary == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(780, 265, 38, 16));
			jLabel6.setText("บาท");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(780, 240, 38, 16));
			jLabel5.setText("บาท");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(780, 215, 38, 16));
			jLabel4.setText("บาท");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(490, 265, 139, 16));
			jLabel3.setText("เงินที่จ่าย");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(490, 240, 153, 16));
			jLabel2.setText("ราคาลด");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(490, 215, 157, 16));
			jLabel1.setText("ราคารวม");
			jLabelTotalNet = new JLabel();
			jLabelTotalNet.setBounds(new Rectangle(700, 265, 38, 16));
			jLabelTotalNet.setText("0");
			jLabelTotalDiscount = new JLabel();
			jLabelTotalDiscount.setBounds(new Rectangle(700, 240, 38, 16));
			jLabelTotalDiscount.setText("0");
			jLabelTotalPrice = new JLabel();
			jLabelTotalPrice.setBounds(new Rectangle(700, 215, 38, 16));
			jLabelTotalPrice.setText("0");
			jPanelOrderSummary = new JPanel();
			jPanelOrderSummary.setLayout(null);
			jPanelOrderSummary.setBackground(Color.white);
			jPanelOrderSummary.add(getJPanel(), null);
			jPanelOrderSummary.add(getJButOrderAgain(), null);
			jPanelOrderSummary.add(getJButOderProceed(), null);
			jPanelOrderSummary.add(jLabelTotalPrice, null);
			jPanelOrderSummary.add(jLabelTotalDiscount, null);
			jPanelOrderSummary.add(jLabelTotalNet, null);
			jPanelOrderSummary.add(getJButtonOrderDel(), null);
			jPanelOrderSummary.add(jLabel1, null);
			jPanelOrderSummary.add(jLabel2, null);
			jPanelOrderSummary.add(jLabel3, null);
			jPanelOrderSummary.add(jLabel4, null);
			jPanelOrderSummary.add(jLabel5, null);
			jPanelOrderSummary.add(jLabel6, null);
		}
		return jPanelOrderSummary;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBounds(new Rectangle(14, 90, 842, 107));
			jPanel.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableOrder());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTableOrder	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTableOrder() {
		if (jTableOrder == null) {
			jTableOrder = new JTable();
			jTableOrder.setShowGrid(false);
			jTableOrder.setShowVerticalLines(true);
			jTableOrder.setShowHorizontalLines(true);
			jTableOrder.setEnabled(true);
		}
		return jTableOrder;
	}

	/**
	 * This method initializes jButOrderAgain	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButOrderAgain() {
		if (jButOrderAgain == null) {
			jButOrderAgain = new JButton();
			jButOrderAgain.setBounds(new Rectangle(520, 290, 70, 35));
			jButOrderAgain.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/badd.png"));
			jButOrderAgain.setText("");
			jButOrderAgain.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					jTabbedPane.setSelectedIndex(0);
					initSize();
				}
			});
		}
		return jButOrderAgain;
	}

	/**
	 * This method initializes jButOderProceed	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButOderProceed() {
		if (jButOderProceed == null) {
			jButOderProceed = new JButton();
			jButOderProceed.setBounds(new Rectangle(740, 290, 70, 35));
			jButOderProceed.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bsubmit.png"));
			jButOderProceed.setText("");
			jButOderProceed.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//ไล่ดึงข้อมูลจาก Arraylist
					//เรื่ม transaction
					String sql = null;
					Savepoint savep = null;
					boolean fail = false;
					try{
						conn.setAutoCommit(false);
						savep = conn.setSavepoint();
						//save order head
						String empId = jLabelLogOnID.getText();
						String total = jLabelTotalNet.getText();
						sql = "INSERT INTO ORDER_HEADS (ORDER_ID, EMP_ID, ORDER_DATE, TOTAL) "+
							"VALUES (ORDER_HEADS_SEQ.NEXTVAL, " +
							"'"+empId+"'," +
							"SYSDATE," +
							"'"+total+"')";
							System.out.println(sql);
						//insert
						save(sql);
						//สร้าง Product
						//ดึงข้อมูลออกจาก ArrayList
						int k = 0;
						Iterator<iceCream> it=productList.iterator();
				        while(it.hasNext()){
				        	iceCream cup =it.next();
				        	System.out.println("----------------------------");
				            System.out.println("Size "+cup.getSize_name());
				            System.out.println("----------------------------");
				            
				            sql = "INSERT INTO PRODUCTS (PRODUCT_ID, SIZE_ID, COUNT, PRODUCT_NAME) "+
								"VALUES (PRODUCTS_SEQ.NEXTVAL, " +
								"'"+cup.getSize_id()+"'," +
								"'"+cup.getPro_id()+"'," +
								"'noname')";
							System.out.println(sql);
							save(sql);
							//ดึง ID ตัวสุดท้ายออกมา
							sql = "SELECT PRODUCT_ID FROM PRODUCTS";
							int lastP = getLastId(sql);
							//วน loop insert ice-cream
							for(int i=0;i<cup.getMax_ice_cream();i++){
								int stockid = cup.getIce(i);
								sql = "INSERT INTO CONSISTS (PRODUCT_ID, STOCK_ID) "+
								"VALUES ('"+lastP+"', " +
								""+stockid+")";
								save(sql);
							}
							//วน loop insert topping
							for(int i=0;i<cup.getMax_topping();i++){
								int stockid = cup.getTop(i);
								sql = "INSERT INTO CONSISTS (PRODUCT_ID, STOCK_ID) "+
								"VALUES ('"+lastP+"', " +
								"'"+stockid+"')";
								System.out.println(sql);
								save(sql);
							}
							
							//ดึง Order Head ล่าสุดออกมา
							sql = "SELECT ORDER_ID FROM ORDER_HEADS";
							int lastO = getLastId(sql);
							//ยัดลงใน Order Detail
							sql = "INSERT INTO ORDER_DETAILS (ORDER_ID, PRODUCT_ID, QUANTY) "+
								"VALUES ('"+lastO+"', " +
								"'"+lastP+"', " +
								"'0')";
							System.out.println(sql);
							save(sql);
							k++;
					    }
				        for(int a = k-1 ;a>=0;a--){
				        	productList.remove(a);
				        }
				        conn.commit();
				        jTabbedPane.setSelectedIndex(0);
						initSize();
					}catch(Exception e1){
						fail = true;
						e1.printStackTrace();
					}
					
					//ถ้า error ก็ roll back
					if(fail){
						try{
							conn.rollback(savep);
							conn.commit();
						}catch(Exception e1){
							
							e1.printStackTrace();
						}
					}
					
					}
			});
		}
		return jButOderProceed;
	}
	private void save(String sql) throws Exception{
		Connection conn = connect.getConn(); 
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt.executeUpdate(sql);
		stmt.close();
		conn.close();
	}
	private int getLastId(String sql)throws Exception{
		Connection conn = connect.getConn(); 
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs= stmt.executeQuery(sql);
		rs.last();
		int lastId = rs.getInt(1);
		rs.close();
		stmt.close();
		conn.close();
		return lastId;
	}

	/**
	 * This method initializes jButtonOrderDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOrderDel() {
		if (jButtonOrderDel == null) {
			jButtonOrderDel = new JButton();
			jButtonOrderDel.setBounds(new Rectangle(630, 290, 70, 35));
			jButtonOrderDel.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/bdelete1.png"));
			jButtonOrderDel.setText("");
			jButtonOrderDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					int id = jTableOrder.getSelectedRow();
					System.out.println(id);
					productList.remove(id);
					initOrder();
					/*if(id!=null){
						String sql = "DELETE FROM PROMOTIONS WHERE PROMOTION_ID="+"'"+id+"'";
						delete(sql);
						initPromotion();
					}else{
						JOptionPane.showMessageDialog(null, "เลือก id");
					}*/
				}
			});
		}
		return jButtonOrderDel;
	}

	/**
	 * This method initializes jButtonLogOut	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonLogOut() {
		if (jButtonLogOut == null) {
			jButtonLogOut = new JButton();
			jButtonLogOut.setBounds(new Rectangle(871, 55, 30, 30));
			jButtonLogOut.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/exit1.png"));
			jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					try {
						//stmt.close();
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
	private void initFrontEnd(int emp_id){
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

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frontend thisClass = new frontend();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}*/

	/**
	 * This is the default constructor
	 */
	public frontend(int emp_id) {
		super();
		initialize(emp_id);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(int emp_id) {
		this.setContentPane(getJContentPane());
		this.setTitle("SWUSENS ระบบหน้าร้าน");
		this.setBounds(new Rectangle(0, 0, 926, 479));
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
		vt = new Validation();
		initFrontEnd(emp_id);
		initSize();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelbg = new JLabel();
			jLabelbg.setBounds(new Rectangle(0, 0, 930, 480));
			jLabelbg.setIcon(new ImageIcon(System.getProperty("user.dir")+"/image/BGmain3.png"));
			jLabelbg.setText("JLabel");
			jLabelLogOn = new JLabel();
			jLabelLogOn.setBounds(new Rectangle(718, 70, 147, 16));
			jLabelLogOn.setForeground(Color.white);
			jLabelLogOn.setText("12345678901112131415");
			jLabelLogOnID = new JLabel();
			jLabelLogOnID.setBounds(new Rectangle(682, 70, 32, 16));
			jLabelLogOnID.setForeground(Color.white);
			jLabelLogOnID.setText("101");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(590, 70, 85, 16));
			jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel.setBackground(Color.white);
			jLabel.setForeground(Color.white);
			jLabel.setText("รหัสพนักงาน:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabelLogOnID, null);
			jContentPane.add(jLabelLogOn, null);
			jContentPane.add(getJButtonLogOut(), null);
			jContentPane.add(jLabelbg, null);
		}
		return jContentPane;
	}

}

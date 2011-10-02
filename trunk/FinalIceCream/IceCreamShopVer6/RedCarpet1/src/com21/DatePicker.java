package com21;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class DatePicker extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JComboBox jComboDate = null;
	private JLabel jLabel1 = null;
	private JComboBox jComboMonth = null;
	private JLabel jLabel2 = null;
	private JComboBox jComboYear = null;
	private JButton jButOK = null;
	private int mode = 0;
	/**
	 * This is the default constructor
	 */
	public DatePicker(int mode) {
		super();
		initialize(mode);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(int mode) {
		this.setSize(489, 216);
		this.setEnabled(true);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.mode = mode;
		setDate();
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
	}
	private void setDate(){
		Calendar d = Calendar.getInstance();
		int day = d.get(d.DAY_OF_MONTH);
		System.out.println("D"+day);
		int month = d.get(d.MONTH);
		System.out.println("M"+month);
		int year = d.get(d.YEAR);
		System.out.println("Y"+year);
		jComboDate.setSelectedIndex(day-1);
		jComboMonth.setSelectedIndex(month);
		jComboYear.setSelectedItem(year);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 128, 56, 22));
			jLabel2.setText("เลือกปี");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 76, 81, 16));
			jLabel1.setText("เลือกเดือน");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(19, 23, 63, 16));
			jLabel.setText("เลือกวันที่");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJComboDate(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJComboMonth(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJComboYear(), null);
			jContentPane.add(getJButOK(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboDate	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboDate() {
		if (jComboDate == null) {
			String day[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
					"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
					"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
			jComboDate = new JComboBox();
			jComboDate.setBounds(new Rectangle(121, 20, 151, 22));
			for(int i=0;i<day.length;i++){
				jComboDate.addItem(day[i]);
			}
		}
		return jComboDate;
	}

	/**
	 * This method initializes jComboMonth	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboMonth() {
		if (jComboMonth == null) {
			String month[] = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฏาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
			jComboMonth = new JComboBox();
			jComboMonth.setBounds(new Rectangle(121, 69, 151, 25));
			jComboMonth.setEnabled(true);
			for(int i=0;i<month.length;i++){
				jComboMonth.addItem(month[i]);
			}
		}
		return jComboMonth;
	}

	/**
	 * This method initializes jComboYear	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboYear() {
		if (jComboYear == null) {
			String year[] = {"2552", "2553", "2554", "2555", "2556"};
			jComboYear = new JComboBox();
			jComboYear.setBounds(new Rectangle(120, 128, 146, 25));
			for(int i=2530;i<2568;i++){
				jComboYear.addItem(i);
			}
			
		}
		return jComboYear;
	}

	/**
	 * This method initializes jButOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButOK() {
		if (jButOK == null) {
			jButOK = new JButton();
			jButOK.setBounds(new Rectangle(340, 32, 85, 47));
			jButOK.setText("OK");
			jButOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					String day = jComboDate.getSelectedItem().toString();
					String month = jComboMonth.getSelectedItem().toString();
					String year = jComboYear.getSelectedItem().toString();
					String date = day+"-"+month+"-"+year;
					System.out.println(date);
					backend.date = date;
					if(mode==1){
						backend.jTextFieldStartPro.setText(date);
					}else if(mode==2){
						backend.jTextFieldEndPro.setText(date);
					}else if(mode==3){
						backend.jTextDOB.setText(date);
					}else if(mode==4){
						backend.jTextFieldStart.setText(date);
					}else if(mode==5){
						backend.jTextFieldEnd.setText(date);
					}
					close();
					
				}

				
			});

		}
		return jButOK;
	}
	private void close() {
		// TODO Auto-generated method stub
		this.dispose();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

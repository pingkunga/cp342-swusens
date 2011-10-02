package com21;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

public class test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton11 = null;
	private JButton jButton111 = null;
	private JButton jButton1111 = null;
	private JButton jButton11111 = null;
	private JButton jButton111111 = null;
	private JButton jButton1111111 = null;

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(0, 17, 852, 118));
			jPanel.setBackground(new Color(255, 102, 102));
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton11(), null);
			jPanel.add(getJButton111(), null);
			jPanel.add(getJButton1111(), null);
			jPanel.add(getJButton11111(), null);
			jPanel.add(getJButton111111(), null);
			jPanel.add(getJButton1111111(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(43, 17, 34, 27));
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(45, 66, 34, 25));
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton11	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setBounds(new Rectangle(127, 11, 115, 91));
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton111	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton111() {
		if (jButton111 == null) {
			jButton111 = new JButton();
			jButton111.setBounds(new Rectangle(285, 20, 136, 26));
		}
		return jButton111;
	}

	/**
	 * This method initializes jButton1111	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1111() {
		if (jButton1111 == null) {
			jButton1111 = new JButton();
			jButton1111.setBounds(new Rectangle(287, 62, 139, 25));
		}
		return jButton1111;
	}

	/**
	 * This method initializes jButton11111	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton11111() {
		if (jButton11111 == null) {
			jButton11111 = new JButton();
			jButton11111.setBounds(new Rectangle(478, 61, 156, 27));
		}
		return jButton11111;
	}

	/**
	 * This method initializes jButton111111	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton111111() {
		if (jButton111111 == null) {
			jButton111111 = new JButton();
			jButton111111.setBounds(new Rectangle(701, 21, 60, 26));
		}
		return jButton111111;
	}

	/**
	 * This method initializes jButton1111111	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1111111() {
		if (jButton1111111 == null) {
			jButton1111111 = new JButton();
			jButton1111111.setBounds(new Rectangle(704, 60, 56, 25));
		}
		return jButton1111111;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				test thisClass = new test();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public test() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(860, 279);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

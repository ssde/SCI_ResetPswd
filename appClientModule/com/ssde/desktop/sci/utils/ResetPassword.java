package com.ssde.desktop.sci.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class ResetPassword extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4923383663169857442L;
	private Font defaultFont = new Font("Verdana",	Font.PLAIN,	14);
	private Color defaultColor = Color.BLACK; //Black

	private JPanel 	contentPane;
	private JPanel 	passChange;
	private JLabel	lbl_message;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPassword frame = new ResetPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ResetPassword() {
	    setTitle("SCI - SSDE, Cambio de Contraseña");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setFont(defaultFont);
		setContentPane(contentPane);

		passChange = new JPanel();
		passChange.setBorder(new EmptyBorder(5, 5, 5, 5));
		passChange.setFont(this.defaultFont);
		passChange.setForeground(this.defaultColor);
		passChange.setLayout(null);
	    this.contentPane.add(passChange);
	    
//	    String message = "<span style='width:450px; text-align:center;'>"+changePassword()+"</span>";
	    String message = changePassword();
	    
	    lbl_message = new JLabel(message);
	    lbl_message.setHorizontalAlignment(SwingConstants.CENTER);
	    lbl_message.setBounds(10, 55, 470, 50);
	    passChange.add(lbl_message);
	    
	}
	
	public static String changePassword() {
	    Connection conn = null;
	    Statement stmt = null;
	    String sql = "";
	    String result = "";
	    SimpleDateFormat sdf = new SimpleDateFormat("HHmmssddMMyyyy");
	    String pass = sdf.format(new Date());
	    
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	conn = DriverManager.getConnection("jdbc:sqlite:inventario.db");
	    	conn.setAutoCommit(true);
	    	//System.out.println("Opened database successfully");
	    	result = "La nueva contraseña es: "+pass;

            try {
                sql = "update contrasena set pass='"+pass+"' where id=1;";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
             	System.out.println("La contrasena ha sido actualizada");
            } catch (Exception e) {
            	result = "Ocurrió un error, la contraseña no fue cambiada;";
            	System.err.println(e.getClass().getName()+": "+e.getMessage());
            }
	    	stmt.close();
	    } catch ( Exception e ) {
	    } finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				System.err.println(e.getClass().getName()+": "+e.getMessage());
			}
	    }
	    
	    return result;
	    //System.out.println("Table created successfully");
	}
}

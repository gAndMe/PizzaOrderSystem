package edu.mum.mscs.fpp.project.pos.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InventoryUI extends JFrame {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/world";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn = null;
	Statement stmt = null;
	private JPanel contentPane;

	public InventoryUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				program pr = new program();
				
			}
		});
		setTitle("Inventory");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();
		
		try {
			// Connect to an Access Database
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;

			// Read data from a table

			sql = "Select * from inventory";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			// Get column names

			for (int i = 1; i <= columns; i++) {
				String s = "";
				if (md.getColumnName(i).equals("prdId")) {
					s = "ID";
				} else if (md.getColumnName(i).equals("prdName")) {
					s = "Name";
				}else if (md.getColumnName(i).equals("groupId")) {
					s = "Group ID";
				}else if (md.getColumnName(i).equals("quantity")) {
					s = "Quantity";
				}else if (md.getColumnName(i).equals("DisplayName")) {
					s = "Display Name";
				}
				columnNames.addElement(s);
			}

			// Get row data

			while (rs.next()) {
				Vector<Object> row = new Vector<Object>(columns);

				for (int i = 1; i <= columns; i++) {
					row.addElement(rs.getObject(i));
				}

				data.addElement(row);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		// Create table with database data

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object o = getValueAt(row, column);

					if (o != null) {
						return o.getClass();
					}
				}

				return Object.class;
			}
		};
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("CheckBox.border"));
		panel.setBounds(10, 11, 867, 269);
		contentPane.add(panel);
		panel.setLayout(null);
						
								JTable table = new JTable(model);
								table.setEnabled(false);
								JScrollPane scrollPane = new JScrollPane(table);
								scrollPane.setBounds(10, 11, 847, 247);
								panel.add(scrollPane);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryUI frame = new InventoryUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

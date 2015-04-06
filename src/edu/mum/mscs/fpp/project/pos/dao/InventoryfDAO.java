package edu.mum.mscs.fpp.project.pos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.mum.mscs.fpp.project.pos.model.Inventory;

public class InventoryfDAO {
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/world";

		// Database credentials
		static final String USER = "root";
		static final String PASS = "admin";
	
	public Inventory[] getAllInventory(){
		Inventory[]  inv = new Inventory[15];
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * from world.inventory";
			ResultSet rs = stmt.executeQuery(sql);

			int i = 0;
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("prdId");
				String prdName = rs.getString("prdName");
				int groupId = rs.getInt("groupId");
				int quantity = rs.getInt("quantity");
				String displayName = rs.getString("DisplayName");

				inv[0] = new Inventory(id,prdName,groupId,quantity,displayName);
				i++;
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		return inv;
	}

}



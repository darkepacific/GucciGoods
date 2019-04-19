package com.revature.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.driver.OracleDriver;

import com.revature.util.ConnectionUtil;

public class main {
	
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	public static void main(String[] args) {
		String sql = "select * from test";

		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

package com.ezypay.main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;

import io.javalin.Handler;

public class Subscription extends Tools {

	
	public static Handler insert = ctx -> {

		String body = ctx.body();

		Connection conn = connect();
		PreparedStatement stmt = null;
		int status = 0;
		try {

			stmt = conn.prepareStatement(
					"insert into subscriptions(amount,type,days,start,end,invoice_dt) "
					+ "values (?,?,?,?,?,?)");
			
			Gson g = new Gson();
			Input input = g.fromJson(body, Input.class);
			
			
			
			stmt.setInt(1, input.getAmount());
			stmt.setString(2, input.getType());
			stmt.setString(3, input.getDays());
			stmt.setString(4, input.getStart());
			stmt.setString(5, input.getEnd());
			stmt.setString(6, getInvoiceDt(input.getType(),input.getDays(),input.getStart(),input.getEnd()));


			status = stmt.executeUpdate();
			
		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		System.out.println(body);
		if (status>0) {
			ctx.html("success");
		} else {
			ctx.html("failed");
		}
		

	};
	
	public static Handler display = ctx -> {
		
		Connection conn = connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder rtn = new StringBuilder();
		
		rtn.append("<table>");
		try {

			stmt = conn.prepareStatement("select * from subscriptions");
			rs = stmt.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			
			rtn.append("<tr>");
			for (int i=1; i<=meta.getColumnCount(); i++) {
				rtn.append("<td>");
				rtn.append(meta.getColumnName(i));
				rtn.append("</td>");
			}
			rtn.append("</tr>");
			
			while (rs.next()) {
				rtn.append("<tr>");
				for (int i=1; i<=meta.getColumnCount(); i++) {
					rtn.append("<td>");
					rtn.append(rs.getString(i));
					rtn.append("</td>");
				}
				rtn.append("</tr>");
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		
		rtn.append("</table>");
		System.out.println(rtn.toString());
		ctx.html(rtn.toString());
		
	};
	
	public static Handler clear = ctx -> {

		Connection conn = connect();
		Statement stmt = null;
		int status = 0;
		try {

			stmt = conn.createStatement();
			
			status = stmt.executeUpdate("delete from subscriptions");
			
		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		if (status>0) {
			ctx.html("success");
		} else {
			ctx.html("failed or table empty");
		}
		

	};

	public static Connection connect() {
		Connection conn = null;

		File file = new File("default/sqlite.db");
		String path = file.getAbsolutePath();

		System.out.println(path);
		try {
			// db parameters
			String url = "jdbc:sqlite:" + path;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}

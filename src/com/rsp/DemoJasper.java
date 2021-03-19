package com.rsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DemoJasper {

	public static void main(String[] args) throws JRException, ClassNotFoundException, SQLException {
		List<User> users = getAllUsers();
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh:mm:ss"); 
		String strDate = dateFormat.format(date);
		String path="C:/Users/Rahul/Desktop/User_Report";
		String sourceFileName="C:/Users/Rahul/workspace/DemoJasper/src/jasperDemo.jrxml";
		JasperReport jasperReport = JasperCompileManager.compileReport(sourceFileName);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
		Map<String,Object> parameters= new HashMap<String,Object>();
		parameters.put("createdBy","rspatil");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
		JasperExportManager.exportReportToPdfFile(jasperPrint, path);
	}
	public static List<User> getAllUsers() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
		String query = "select * from user";
		Statement stmt  = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		List<User> users=new ArrayList<User>();
		while(rs.next())
		{
			User usr = new User();
			usr.setFirstname(rs.getString("firstname"));
			usr.setLastname(rs.getString("lastname"));
			usr.setEmail(rs.getString("email"));
			usr.setPassword(rs.getString("password"));
			//System.out.println(cmpl.getMessage());
			users.add(usr);
		}
		rs.close();
		stmt.close();
		con.close();
		return users;
	}
}



package pt.hermanoportes.diary.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/diary?useTimezone=true&serverTimezone=UTC",
					"root",
					"root");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

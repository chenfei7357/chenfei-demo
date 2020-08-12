package chenfeidemo.elasticjobdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Demo {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?autoReconnect=true" +
							"&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8",
					"root", "root");
			System.out.println("数据库连接成功"+connection);
			PreparedStatement pstm = connection.prepareStatement("select * from aaa where id=?");
			pstm.setLong(1,1);
			ResultSet resultSet = pstm.executeQuery();
			if(resultSet.next()){
				Object name = resultSet.getObject("name");
				System.out.println("name:"+name);
			}

		}catch (Exception e){
			e.printStackTrace();
		}

	}
}

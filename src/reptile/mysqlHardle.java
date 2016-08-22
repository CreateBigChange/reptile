package reptile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlHardle {
    // ����������
    String driver = "com.mysql.jdbc.Driver";

    // URLָ��Ҫ���ʵ����ݿ���scutcs
    String url = "jdbc:mysql://127.0.0.1:3306/reptile?";

    // MySQL����ʱ���û���
    String user = "root"; 

    // MySQL����ʱ������
    String password = "123456";
    
    //MYSQLִ�����statement
    Statement statement =null;
	public mysqlHardle() {        
        try {
        	// ������������
			Class.forName(driver);
			
	        // �������ݿ�
	        Connection conn = DriverManager.getConnection(url+"user="+user+"&password="+password+"&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");

	        if(!conn.isClosed()) 
	         System.out.println("Succeeded connecting to the Database!");

	        // statement����ִ��SQL���
	         statement = conn.createStatement();
	  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

    public void add(String table,String picUrl,String saleprice,String detail,String title,String saleNum,String commitNum){
        // Ҫִ�е�SQL���
        String sql = "insert into "+table+"(picUrl,saleprice,detailUrl,title,saleNum,commitNum)values(\""
        +picUrl+"\",\""
        +saleprice+"\",\""
        +detail+"\",\""
        +title+"\",\""
        +saleNum+"\",\""
        +commitNum+"\")";
        int rs =0;
        try {
        //	System.out.println(sql);
        	rs = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    public void addErrorInfo(String table,String url,String name,String store,String currpage,String totleNum,String reason){
        
        // Ҫִ�е�SQL���
        String sql = "insert into "+table+"(url,store,currpage,totleNum,reason,name)values(\""
        +url+"\",\""
        +store+"\",\""
        +currpage+"\",\""
        +totleNum+"\",\""
        +reason+"\",\""
        +name+"\")";
        
        int rs=0;
        System.out.println(sql);
        try {
        	rs= statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}

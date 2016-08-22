package reptile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysqlHardle {
    // 驱动程序名
    String driver = "com.mysql.jdbc.Driver";

    // URL指向要访问的数据库名scutcs
    String url = "jdbc:mysql://127.0.0.1:3306/reptile?";

    // MySQL配置时的用户名
    String user = "root"; 

    // MySQL配置时的密码
    String password = "123456";
    
    //MYSQL执行语句statement
    Statement statement =null;
	public mysqlHardle() {        
        try {
        	// 加载驱动程序
			Class.forName(driver);
			
	        // 连续数据库
	        Connection conn = DriverManager.getConnection(url+"user="+user+"&password="+password+"&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");

	        if(!conn.isClosed()) 
	         System.out.println("Succeeded connecting to the Database!");

	        // statement用来执行SQL语句
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
        // 要执行的SQL语句
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
        
        // 要执行的SQL语句
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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private static String protocol = "jdbc:derby:";
    Connection conn;
    Statement stat;
    private String create = "CREATE TABLE image (name varchar(20),"
    		+ "path varchar(100))";

    public Database () {
    	try {
    		//Find the driver for database
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			File file = new File("D:/Java/derby/db-derby/bin");
			//Check if the user use this project at first time.
			if (!file.exists()) {
				file.mkdirs();
				//Get connection with database
	            conn = DriverManager.getConnection(protocol + file+"/mydb.db"
	                    + ";create=true");
	            //Create statement
	            stat = conn.createStatement();
				create();
			} else {
	            conn = DriverManager.getConnection(protocol + file+"/mydb.db"
	                    + ";create=false");
	            stat = conn.createStatement();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Create table.
    public void create() {
    	try {
			stat.execute(create);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //Insert a new image information.
    public int insertImage(String[] detail) {
    	try {
			return stat.executeUpdate("INSERT INTO image VALUES('"+detail[0]+"','"+detail[1]+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }
    
    //Search a image with a string.
    public ArrayList<String[]> queryImage(String name) {
    	try {
			ResultSet result = stat.executeQuery("SELECT * FROM image");
			ArrayList<String[]> real = new ArrayList<String[]>();
			while(result.next()) {
				//check if the image contains the string
				if(result.getString("name").contains(name)) {
					String[] str = new String[2];
					str[0] = result.getString("name");
					str[1] = result.getString("path");
					real.add(str);
				}
			}
			return real;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
}



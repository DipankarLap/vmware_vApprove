package scripts.com.framework;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccessDBMethods {

	public String projectName;

	public AccessDBMethods(String projectName) {
		this.projectName = projectName;
	}

	public Connection getConnectionObj() {
		Connection conn = null;
		try {

			String networkdrive = System.getenv("Automation_Path");
			String dbName = networkdrive + "Data\\" + projectName+ "\\TestSets.accdb";
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};Dbq="+ dbName + ";";
			String version = System.getProperty("java.version");
			if (version.startsWith("1.8") || version.startsWith("8")) {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				conn = DriverManager.getConnection("jdbc:ucanaccess://"+ dbName);
			} else {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				conn = DriverManager.getConnection(database, "", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public String getSauceuserAccessKey(String sauceuser){
		try{
			String sauceUserAccessKey="";
			Connection conn=getConnectionObj();		
			Statement smt = conn.createStatement();	
			String sSQL="select SauceUserAccessKey from InterfaceData where SauceUser='"+sauceuser+"'";
			smt.execute(sSQL);
			ResultSet rs = smt.getResultSet();
			while(rs.next())
			{
				sauceUserAccessKey=rs.getString(1);		
			}	
			return sauceUserAccessKey;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getTestCases(String sSQL) throws ClassNotFoundException, SQLException{	
		Connection conn=getConnectionObj();		
		Statement smt = conn.createStatement();			
		smt.execute(sSQL);
		ResultSet rs = smt.getResultSet();

		int i=0;
		List<String> testcases = new ArrayList<String>();	
		while(rs.next())
		{
			testcases.add(rs.getString(1));
			i++;			
		}		
		smt.close();
		conn.close();		
		return testcases;			
	}
	
	public String[] getModules(String dbName, String sSQL) throws ClassNotFoundException, SQLException{

		Connection conn=getConnectionObj();
		Statement smt = conn.createStatement();
		smt.execute(sSQL);
		ResultSet rs = smt.getResultSet();

		String[] ColumnName= new String[rs.getMetaData().getColumnCount()];
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.next();			

		for (int i=1; i<=rsmd.getColumnCount(); i++)
		{
			String sColName = rsmd.getColumnName(i);
			ColumnName[i-1]=sColName;
		}

		smt.close();
		conn.close();		
		return ColumnName;			
	}
	
	public List<String> getModuleNames() throws ClassNotFoundException, SQLException{
		
		Connection conn=getConnectionObj();		
		List<String> tableNames= new ArrayList<String>();
		DatabaseMetaData md = conn.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		String temp;
		while (rs.next()) {
			temp = rs.getString(3);
			if(!temp.contains("MSys")) {
				if(!temp.contains("Test")){
					tableNames.add(temp);
				}		         
			}
		}		
		conn.close();	
		return tableNames;				
	}
	
	public String[] getTestSets(String sSQL) throws ClassNotFoundException, SQLException{


		Connection conn=getConnectionObj();
		Statement smt = conn.createStatement();
		smt.execute(sSQL);
		ResultSet rs = smt.getResultSet();
		String[] ColumnName= new String[rs.getMetaData().getColumnCount()];
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.next();			
		
		for (int i=1; i<=rsmd.getColumnCount(); i++){				
			String sColName = rsmd.getColumnName(i);
			ColumnName[i-1]=sColName;
		}

		smt.close();
		conn.close();		
		return ColumnName;			
	}
	
	public String[] getInstanceName(String sSQL) throws ClassNotFoundException, SQLException{
		Connection conn=getConnectionObj();
		Statement smt = conn.createStatement();
		smt.execute(sSQL);
		ResultSet rs = smt.getResultSet();
		String[] ColumnName= new String[rs.getMetaData().getColumnCount()];
		ResultSetMetaData rsmd = rs.getMetaData();
		rs.next();			
		
		for (int i=1; i<=rsmd.getColumnCount(); i++){				
			String sColName = rsmd.getColumnName(i);
			ColumnName[i-1]=sColName;
		}

		smt.close();
		conn.close();		
		return ColumnName;			
	}
	
	public void insertDataIntoAccessDB(String sSQL) throws ClassNotFoundException, SQLException{
		Connection conn=getConnectionObj();
		Statement smt = conn.createStatement();

		smt.execute(sSQL);			
		smt.close();
		conn.close();
	}
}

package myApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public enum HouseDAO {
	
instance;

    private Connection con = null;
    private Statement stmt = null;
    
    private HouseDAO(){
        try{
        	Class.forName("org.hsqldb.jdbcDriver");
        	con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "SA", "Passw0rd");
            stmt = con.createStatement();
        	
        }catch (SQLException ex){
            System.err.println("\nSQLException");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.out.println("Error Gettiing Database Driver");
			e.printStackTrace();
		}
    }
    
    
    public House getHouse(String houseID){
        
    	House house = null;
        try{
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM HOUSES where hid = ?");
            pstmt.setString(1, houseID);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (!rs.next()){
                return null;
            }
            
            int id = rs.getInt("hid");
            String hname = rs.getString("hName");
            String seat = rs.getString("seat");
            String words = rs.getString("words");

            house = new House(id, hname, seat, words);
                
        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
        
        System.out.println(house);
        return house;
    }
    
    
    public List<House> getHouses(){
        
        List<House> houses = new ArrayList<House>();
        try{
        	ResultSet rs = stmt.executeQuery("SELECT * FROM HOUSES");
            
            while(rs.next()){
                int id = rs.getInt(1);
                String hname = rs.getString(2);
                String seat = rs.getString(3);
                String words = rs.getString(4);
                
                houses.add(new House(id, hname, seat, words));
            }
        } catch (SQLException ex) {
            System.err.println("Error Getting Houses");
            ex.printStackTrace();
        }
        
        System.out.println(houses);
        
        return houses;
    }
    
    
    public void create(House house) {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO HOUSES VALUES(?,?,?,?)");
            pstmt.setInt(1, house.getId());
            pstmt.setString(2, house.gethName());
            pstmt.setString(3, house.getSeat());
            pstmt.setString(4, house.getWords());

            int count = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }

    
    public void delete(int id) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM HOUSES WHERE hid = ?");
            pstmt.setInt(1, id);
            

            int count = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }
    
    public void deleteAll() {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM HOUSES");
            
            int count = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }

    public int getNextId() {

        int maxId = 0;

        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT max(hid) FROM HOUSES");

            ResultSet rs = pstmt.executeQuery();

            rs.next();

            maxId = rs.getInt(1);

        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }

        return maxId + 1;
    }

    public void update(House house) {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE HOUSES SET hname = ?, seat = ?, words = ? WHERE hid = ?");
            pstmt.setInt(4, house.getId());
            pstmt.setString(1, house.gethName());
            pstmt.setString(2, house.getSeat());
            pstmt.setString(3, house.getWords());

            int count = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("\nSQLException");
            ex.printStackTrace();
        }
    }
    
    
}

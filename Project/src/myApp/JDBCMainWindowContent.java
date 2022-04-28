package myApp;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener
{	
	String cmd = null;

	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private ResultSet rs2 = null;
	private ResultSet rs3 = null;
	private PreparedStatement ps = null;

	private static final DecimalFormat df = new DecimalFormat("0.00");

	private Container content;

	private JPanel charactersPanel;
	private JPanel exportButtonPanel;
	private JPanel extraButtonPanel;
	//private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;
	
	String op[] = {"HOUSES", "CHARACTERS"};
	String table = "HOUSES";
	private JComboBox options = new JComboBox(op);

	private JLabel head1 =new JLabel("HOUSES");
	private JLabel head2 =new JLabel(" ");
	private JLabel IDLabel=new JLabel("HouseID:");
	private JLabel secondLabel=new JLabel("House Name:");
	private JLabel thirdLabel=new JLabel("Seat:");
	private JLabel fourthLabel=new JLabel("Words:");

	private JTextField IDTF= new JTextField(10);
	private JTextField secondTF= new JTextField(10);
	private JTextField thirdTF=new JTextField(10);
	private JTextField fourthTF=new JTextField(10);


	private static QueryTableModel TableModel = new QueryTableModel();
	//Add the models to JTabels
	private JTable TableofDBContents=new JTable(TableModel);
	//Buttons for inserting, and updating members
	//also a clear button to clear characters panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");
	private JButton optionButton  = new JButton("Show");
	private JButton getSingleButton  = new JButton("Get Single");
	private JTextField getSingleTF  = new JTextField(12);

	//Not Implemented Yet
	private JButton  CharRoles = new JButton("Character Roles:");
	private JTextField CharRolesTF  = new JTextField(12);
	private JButton ListChap  = new JButton("List Chapters from Story");
	private JTextField ListChapTF  = new JTextField(12);
	private JButton ListAllChap  = new JButton("List All Chapters");
	private JButton ListAllChar  = new JButton("List All Characters");
	private JButton ListCharParts  = new JButton("List Char Parts");
	private JButton CharView  = new JButton("Character View");



	public JDBCMainWindowContent( String aTitle)
	{	
		//setting up the GUI
		super(aTitle, false,false,false,false);
		setEnabled(true);
		//add the 'main' panel to the Internal Frame
		content=getContentPane();
		content.setLayout(null);
		content.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		//setup characters panel and add the components to it
		charactersPanel=new JPanel();
		charactersPanel.setLayout(new GridLayout(11,2));
		charactersPanel.setBackground(Color.lightGray);
		charactersPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Actions"));

		charactersPanel.add(head1);
		charactersPanel.add(head2);
		charactersPanel.add(IDLabel);			
		charactersPanel.add(IDTF);
		charactersPanel.add(secondLabel);			
		charactersPanel.add(secondTF);
		charactersPanel.add(thirdLabel);		
		charactersPanel.add(thirdTF);
		charactersPanel.add(fourthLabel);		
		charactersPanel.add(fourthTF);
		charactersPanel.add(options);
		charactersPanel.add(optionButton);

		//setup characters panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(4,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(CharRoles);
		exportButtonPanel.add(CharRolesTF);
		exportButtonPanel.add(ListChap);
		exportButtonPanel.add(ListChapTF);
		exportButtonPanel.add(ListAllChap);
		exportButtonPanel.add(ListAllChar);
		exportButtonPanel.add(ListCharParts);
		exportButtonPanel.add(CharView);

		extraButtonPanel=new JPanel();
		extraButtonPanel.setLayout(new GridLayout(1,1));
		extraButtonPanel.setBackground(Color.lightGray);
		extraButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Extra Data"));
		extraButtonPanel.add(getSingleTF);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize (100, 30);
		deleteButton.setSize (100, 30);
		clearButton.setSize (100, 30);
		getSingleButton.setSize (100, 30);

		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		exportButton.setLocation (370, 160);
		deleteButton.setLocation (370, 60);
		clearButton.setLocation (370, 210);
		getSingleButton.setLocation (370, 260);

		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		optionButton.addActionListener(this);
		getSingleButton.addActionListener(this);

		this.ListAllChap.addActionListener(this);
		this.ListAllChar.addActionListener(this);
		this.ListCharParts.addActionListener(this);
		this.CharRoles.addActionListener(this);
		this.ListChap.addActionListener(this);
		this.CharView.addActionListener(this);


		content.add(insertButton);
		content.add(updateButton);
		content.add(exportButton);
		content.add(deleteButton);
		content.add(clearButton);
		content.add(getSingleButton);


		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel=new JScrollPane(TableofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder,"Database Content"));

		charactersPanel.setSize(360, 300);
		charactersPanel.setLocation(3,0);
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		extraButtonPanel.setSize(600, 200);
		extraButtonPanel.setLocation(510, 300);
		
		content.add(exportButtonPanel);
		content.add(charactersPanel);
		content.add(dbContentsPanel);
		content.add(extraButtonPanel);

		setSize(982,645);
		setVisible(true);

		TableModel.refreshFromDB(stmt, table);
	}
	
	public void clear() {

		IDTF.setText("");
		secondTF.setText("");
		thirdTF.setText("");
		fourthTF.setText("");
	}

	//event handling 
	public void actionPerformed(ActionEvent a)
	{
		Object target=a.getSource();
		
		if (target == getSingleButton)
		{		 
					
			if(table == "HOUSES") {
				CloseableHttpResponse httpResponse;
		    	CloseableHttpClient httpClient;
		    	String line = "";
		    	try {
		    		URI uri = new URIBuilder()
		    				.setScheme("http")
		    				.setHost("localhost")
		    				.setPort(8080)
		    				.setPath("/Systems/rest/houses/1")
		    				.build();
		    		
		    		HttpGet getRequest = new HttpGet(uri);
		    		getRequest.setHeader("Accept", "application/json");
		    		
		    		httpClient = HttpClients.createDefault();
		    		httpResponse = httpClient.execute(getRequest);
		    		
		    		HttpEntity entity = httpResponse.getEntity();
		    		Scanner in = new Scanner(entity.getContent());
		    		
		    		
		    		while(in.hasNextLine()) {
		    			line = in.nextLine();
		    		}

		            System.out.println("GetHouse");
		    		System.out.println(line);
		    		getSingleTF.setText(line);
		    		
		    		in.close();
		    	} catch (Exception e) {
		    		System.err.println(e.getMessage());
		    		e.printStackTrace();
		    	}
				
			}else if(table == "CHARACTERS") {
				
			}
				
			TableModel.refreshFromDB(stmt, table);
		}
		
		
		if (target == optionButton)
		{
			table = options.getSelectedItem().toString();
			
			if(table == "HOUSES") {
				IDLabel.setText("House ID:");
				secondLabel.setText("House Name:");
				thirdLabel.setText("Seat:");
				fourthLabel.setText("Words:");
				
				clear();
				
			}else if(table == "CHARACTERS") {
				IDLabel.setText("Character ID:");
				secondLabel.setText("House ID:");
				thirdLabel.setText("Character Name");
				fourthLabel.setText("ChapterName:");
				
				clear();
			}
			
			head1.setText(table);
			
			TableModel.refreshFromDB(stmt, table);

		}
		
		
		if (target == clearButton)
		{
			clear();
		}

		
		if (target == insertButton)
		{		 
			String id = IDTF.getText();
			String second = IDTF.getText();
			String third = IDTF.getText();
			String fourth = IDTF.getText();
			if(table == "HOUSES") {
				
		    	String line = "";
		    	try {
		    		URI uri = new URIBuilder()
		    				.setScheme("http")
		    				.setHost("localhost")
		    				.setPort(8080)
		    				.setPath("/Systems/rest/houses")
		    				.build();
		    		
		    		HttpPost postRequest = new HttpPost(uri);
		    		postRequest.setHeader("Accept", "text/html");
		    		
		    		CloseableHttpClient client = HttpClients.createDefault();
		    		
		    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePairs>(1);
		    		
		    		
		    	} catch (Exception e) {
		    		System.err.println(e.getMessage());
		    		e.printStackTrace();
		    	}
				
			}else if(table == "CHARACTERS") {
				
			}
				
			TableModel.refreshFromDB(stmt, table);
		}
		
		
		if (target == deleteButton)
		{

			if(table == "HOUSES") {
				
			}else if(table == "CHARACTERS") {
				
			}
			
			TableModel.refreshFromDB(stmt, table);
		}
		
		
		if (target == updateButton)
		{	 	
			if(table == "HOUSES") {
				
			}else if(table == "CHARACTERS") {
				
			}
			
			TableModel.refreshFromDB(stmt, table);
		}
		
		
		if(target == exportButton){
			cmd = "select * from  "+table;

			System.out.println(cmd);
			try{					
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){
				e1.printStackTrace();
			}

		} 
	}
		
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == options) {
			
			table = options.getSelectedItem() +"";
			System.out.print("here");
			
			TableModel.refreshFromDB(stmt, table);
			
		}
	}
	
	private void writeToFile(ResultSet rs){
		try{
			System.out.println("In output.csv");
			FileWriter outputFile = new FileWriter("output.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for(int i=0;i<numColumns;i++){
				printWriter.print(rsmd.getColumnLabel(i+1)+",");
			}
			printWriter.print("\n");
			while(rs.next()){
				for(int i=0;i<numColumns;i++){
					printWriter.print(rs.getString(i+1)+",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		}
		catch(Exception e){e.printStackTrace();}
	}
}

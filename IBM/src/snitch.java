import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class snitch extends JFrame implements ActionListener  {
	
	/* Labels, TextFields, Buttons and Panels of Company */
	JLabel c = new JLabel("COMPANY");
	JLabel c_name = new JLabel("Company Name");
	JLabel c_username = new JLabel("Company Username");
	JLabel c_pass = new JLabel("Company Password");
	JLabel c_county = new JLabel("Company County");
	JLabel c_phone = new JLabel("Company Phone");
	JLabel c_bos = new JLabel("");
	JLabel c_bos2 = new JLabel("");
	JLabel c_bos3 = new JLabel("");
	JLabel c_bos4 = new JLabel("               COMPANIES =>");
	JLabel c_bos5 = new JLabel("");
	
	static JTextField t_name = new JTextField();
	static JTextField t_username = new JTextField();
	static JTextField t_pass = new JTextField();
	static JTextField t_county = new JTextField();
	static JTextField t_phone = new JTextField();
	
	JButton btn_saveCompany = new JButton("Save Company");
	JPanel pnl_Company = new JPanel();
	
	/* Labels, TextFields,ComboBox, Buttons and Panels of Product */
	
	DefaultListModel LLL = new DefaultListModel();  
	JList list = new JList(LLL);
	JButton btn_refresh = new JButton("Refresh");
	JButton btn_delete = new JButton("Delete");
	
	JPanel pnl_List = new JPanel();
	JPanel pnl_eyca = new JPanel(); // Panel to divide company and listview parts
	
	private static java.sql.Connection baglanti;
	ArrayList<CompanyModel> companies;
	static Connection connection = null;
	static Statement st = null;
	static ResultSet rs = null;
	
	public snitch() {
		
		
		super("Enter Company or Product");
		companies = new ArrayList<>();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
		
		
		//COMPANY 
		
		pnl_Company.setLayout(new GridLayout(7,2));
		pnl_Company.add(c);
		pnl_Company.add(c_bos);
		pnl_Company.add(c_name);
		pnl_Company.add(t_name);
		pnl_Company.add(c_username);
		pnl_Company.add(t_username);
		pnl_Company.add(c_pass);
		pnl_Company.add(t_pass);
		pnl_Company.add(c_county);
		pnl_Company.add(t_county);
		pnl_Company.add(c_phone);
		pnl_Company.add(t_phone);
		pnl_Company.add(c_bos2);
		btn_saveCompany.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(!t_name.getText().equals("") && !t_username.equals("") && !t_pass.equals("")  && !t_county.equals("") && !t_phone.equals(""))
				{
					insertCompany(t_name.getText(),t_username.getText(),t_pass.getText(),t_county.getText(),t_phone.getText());
					updateList();
					clearTextBoxes();
					
					
				}
				
				
			}
		});
pnl_Company.add(btn_saveCompany);
		
		pnl_List.setLayout(new GridLayout(2,10));
		pnl_List.add(c_bos4);
		pnl_List.add(list);
		pnl_List.add(btn_delete);
		pnl_List.add(btn_refresh);
		list.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				
				CompanyModel c = (CompanyModel) list.getSelectedValue();
				t_county.setText(c.COUNTY);
				t_name.setText(c.NAME);
				t_pass.setText(c.PASSWORD);
				t_phone.setText(c.PHONE);
				t_username.setText(c.USERNAME);
				
			}
		});
		
		
	
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				CompanyModel c =  (CompanyModel)list.getSelectedValue();
				deleteCompany(c);
				updateList();
				
			}

			
		});
		btn_refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				updateList();
				
			}
		});
		
		pnl_eyca.setLayout(new GridLayout(1,3));
		pnl_eyca.add(pnl_Company);
		pnl_eyca.add(pnl_List);
		add(pnl_eyca);
		
		setSize(600,400);
		setVisible(true);
	
		updateList();
			
	}
	
	
	
	public static void main(String[] args) {
		new snitch();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Save Company"))
		{
			//ÖRNEK 
			String query = "INSERT INTO COMPANY (EMPLOYEEID, FNAME, LNAME, HIREDATE, SALARY) VALUES(?,?,?,?,?)";
			
			try 
			{
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1,t_name.getText());
				ps.setString(2, t_username.getText());
				ps.setString(3, t_pass.getText());
				ps.setString(4, t_county.getText());
				ps.setInt(5, Integer.parseInt(t_phone.getText()));					
				ps.executeUpdate();
				ps.close();
				JOptionPane.showMessageDialog(null, "Succesfully Inserted");
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
		private void insertCompany(String text, String text2, String text3,
			String text4, String text5) {

			try {	
					//Database=cdb_d972cb709a;Data Source=us-cdbr-azure-east-a.cloudapp.net;User Id=ba27b5b5a4c387;Password=c96ae58f
				baglanti=null;
				baglanti=DriverManager.getConnection("jdbc:mysql://us-cdbr-azure-east-a.cloudapp.net/cdb_d972cb709a","ba27b5b5a4c387","c96ae58f");
				System.out.println("");
			}
			
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			
		try{
		Statement statement=baglanti.createStatement();
		statement.executeUpdate("INSERT INTO `cdb_d972cb709a`.`company_master` (`NAME`, `USERNAME`, `PASSWORD`, `COUNTY`, `PHONE`) VALUES ('"+text+"', '"+text2+"', '"+text3+"', '"+text4+"', '"+text5+"');");
		baglanti.close();
		}catch(Exception ex)
		{
			
			ex.printStackTrace();
		}
	}
	private void updateList()
	{
	list.setListData(new Vector<>());
	    
	    
	  companies = new ArrayList<>();  
	  getAllCompanies();
	  list.setListData(companies.toArray());
	  System.out.println(companies);
			
	  
	  
		
		
		
	}
	
	
	private void getAllCompanies() {
		// TODO Auto-generated method stub
		try
		{

			try {	
					//Database=cdb_d972cb709a;Data Source=us-cdbr-azure-east-a.cloudapp.net;User Id=ba27b5b5a4c387;Password=c96ae58f
				baglanti=null;
				baglanti=DriverManager.getConnection("jdbc:mysql://us-cdbr-azure-east-a.cloudapp.net/cdb_d972cb709a","ba27b5b5a4c387","c96ae58f");
				System.out.println("");
			}
			
			
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		Statement statement=baglanti.createStatement();
		ResultSet result=statement.executeQuery("SELECT * FROM company_master");
		
		while(result.next())
		{
			CompanyModel model = new CompanyModel();
			model.NAME = result.getString("NAME");
			model.USERNAME = result.getString("USERNAME");
			model.PASSWORD = result.getString("PASSWORD");
			model.PHONE = result.getString("PHONE");
			model.COUNTY = result.getString("COUNTY");
            model.ID  = result.getInt("ID");
			
			companies.add(model);
		}
		
		
		
		baglanti.close();
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		
		
		
		
	
	}
	private void clearTextBoxes()
	{
		t_county.setText("");
		t_name.setText("");
		t_pass.setText("");
		t_phone.setText("");
		t_username.setText("");
		
		
	}
	private void deleteCompany(CompanyModel c) {
		// TODO Auto-generated method stub
		
		try {	
			//Database=cdb_d972cb709a;Data Source=us-cdbr-azure-east-a.cloudapp.net;User Id=ba27b5b5a4c387;Password=c96ae58f
		baglanti=null;
		baglanti=DriverManager.getConnection("jdbc:mysql://us-cdbr-azure-east-a.cloudapp.net/cdb_d972cb709a","ba27b5b5a4c387","c96ae58f");
		System.out.println("");
	
		
		
		Statement statement=baglanti.createStatement();
		statement.executeUpdate("DELETE FROM `cdb_d972cb709a`.`company_master` WHERE `ID`='"+c.ID+"'");
		
		
		
		}
	
	
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		
		
		
		
		
		
		
	
		
		
	try {
		baglanti.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}


	class CompanyModel
	{
		public int ID;
		public String NAME;
		public String USERNAME;
		public String PASSWORD;
		public String COUNTY;
		public String PHONE;
		
		
		
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return NAME;
		
	}
	}

}

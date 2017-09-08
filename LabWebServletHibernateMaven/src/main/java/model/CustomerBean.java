package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Entity
@Table(name="CUSTOMER")
public class CustomerBean {
	@Id
	private String custid;
	private byte[] password;
	private String email;
	private java.util.Date birth;
	
	
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("beans.config.xml");
		try {
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();
			Session session = sessionFactory.getCurrentSession();

//insert
//			CustomerBean insert = new CustomerBean();
//			insert.setCustid("xxx");
//			insert.setPassword("xxx".getBytes());
//			insert.setEmail("xxx@iii.org.tw");
//			insert.setBirth(new java.util.Date());
//			session.save(insert);
		
//select
			CustomerBean select = session.get(CustomerBean.class, "xxx");
			System.out.println("select="+select);
			
			sessionFactory.getCurrentSession().getTransaction().commit();
			
//			DataSource dataSource = (DataSource) context.getBean("dataSource");
//			Connection conn = dataSource.getConnection();
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery("select * from dept");
//			while (rset.next()) {
//				String col1 = rset.getString(1);
//				String col2 = rset.getString(2);
//				System.out.println(col1 + ":" + col2);
//			}
//			rset.close();
//			stmt.close();
//			conn.close();
		
		} finally {
			((ConfigurableApplicationContext) context).close();
		}
	}
	
	@Override
	public String toString() {
		return "model.CustomerBean ["+ custid+ ","+ email+ ","+ birth+ "]";
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getBirth() {
		return birth;
	}
	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}
}

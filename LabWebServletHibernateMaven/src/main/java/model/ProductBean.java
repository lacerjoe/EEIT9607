package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import model.spring.SpringJavaConfiguration;

@Entity
@Table(name="PRODUCT")
public class ProductBean {
	@Id
	private int id;
	private String name;
	private double price;
	private java.util.Date make;
	private int expire;
	public static void main(String[] args) throws Exception {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(SpringJavaConfiguration.class);
		try {
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();
			Session session = sessionFactory.getCurrentSession();
			
//Insert
//			ProductBean insert = new ProductBean();
//			insert.setId(100);
//			insert.setName("demo product");
//			insert.setPrice(111);
//			insert.setMake(new java.util.Date());
//			insert.setExpire(222);
//			session.save(insert);
	
//select
			ProductBean select = session.get(ProductBean.class, 100);
			System.out.println("select="+select);
////update
//			select.setName("oooo");
//			select.setPrice(222);
//			select.setMake(new java.util.Date(0));
//			select.setExpire(333);
	
//delete
//			session.delete(select);
			
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
			
			sessionFactory.getCurrentSession().getTransaction().commit();
		} finally {
			((ConfigurableApplicationContext) context).close();
		}
	}
	@Override
	public String toString() {
		return "model.ProductBean ["+ id+ ","+ name+ ","+ price+ ","+ make+ ","+ expire+ "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public java.util.Date getMake() {
		return make;
	}
	public void setMake(java.util.Date make) {
		this.make = make;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
}

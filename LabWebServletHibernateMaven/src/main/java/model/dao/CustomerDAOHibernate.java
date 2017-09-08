package model.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import model.CustomerBean;
import model.CustomerDAO;
@Repository
public class CustomerDAOHibernate implements CustomerDAO {
	public static void main(String[] args) throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("beans.config.xml");
		try {
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();

			CustomerDAO customerDao = (CustomerDAO) context.getBean("customerDAOHibernate");
			
			CustomerBean select = customerDao.select("Ellen");
			System.out.println("select="+select);
			
			boolean update = customerDao.update(
					"EEE".getBytes(), "ellen@iii.org.tw", new java.util.Date(), "Ellen");
			System.out.println("update="+update);
			
			sessionFactory.getCurrentSession().getTransaction().commit();
		} finally {
			((ConfigurableApplicationContext) context).close();
		}
	}

	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	@Override
	public CustomerBean select(String custid) {
		return this.getSession().get(CustomerBean.class, custid);
	}
	@Override
	public boolean update(byte[] password, String email, Date birth, String custid) {
		CustomerBean bean = this.getSession().get(CustomerBean.class, custid);
		if(bean!=null) {
			bean.setPassword(password);
			bean.setEmail(email);
			bean.setBirth(birth);
			return true;
		}
		return false;
	}
}

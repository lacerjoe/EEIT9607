package model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import model.ProductBean;
import model.ProductDAO;
import model.spring.SpringJavaConfiguration;
@Repository
public class ProductDAOHibernate implements ProductDAO {
	public static void main(String[] args) {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(SpringJavaConfiguration.class);
		try {
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();

			ProductDAO productDao = (ProductDAO) context.getBean("productDAOHibernate");

//Select
			ProductBean select = productDao.select(10);
			System.out.println("select="+select);

			List<ProductBean> selects = productDao.select();
			System.out.println("selects="+selects);

//Insert
//			ProductBean insert = new ProductBean();
//			insert.setId(100);
//			insert.setName("demo product");
//			insert.setPrice(111);
//			insert.setMake(new java.util.Date());
//			insert.setExpire(222);
//			productDao.insert(insert);
			
//Update
//			ProductBean update = productDao.update("ooo", 222, new java.util.Date(0), 333, 100);
//Delete
//			boolean delete = productDao.delete(100);
			
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
	public ProductBean select(int id) {
		return this.getSession().get(ProductBean.class, id);
	}

	@Override
	public List<ProductBean> select() {
		return this.getSession().createQuery("FROM ProductBean", ProductBean.class).list();
	}

	@Override
	public ProductBean insert(ProductBean bean) {
		if(bean!=null) {
			ProductBean select = this.select(bean.getId());
			if(select==null) {
				this.getSession().save(bean);
				return bean;
			}
		}
		return null;
	}

	@Override
	public ProductBean update(String name, double price, Date make, int expire, int id) {
		ProductBean bean = this.select(id);
		if(bean!=null) {
			bean.setName(name);
			bean.setPrice(price);
			bean.setMake(make);
			bean.setExpire(expire);
			return bean;
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		ProductBean bean = this.select(id);
		if(bean!=null) {
			this.getSession().delete(bean);
			return true;	
		}
		return false;
	}
}

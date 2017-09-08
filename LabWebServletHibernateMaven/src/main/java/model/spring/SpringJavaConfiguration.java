package model.spring;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import model.CustomerBean;
import model.ProductBean;

@Configuration
@ComponentScan(
		basePackages={"model"}
)
public class SpringJavaConfiguration {
	@Bean
	public DataSource dataSource() {
		try {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup("java:comp/env/jdbc/xxx");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	@Bean
//	public SessionFactory sessionFactory() {
//		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
//		builder.configure("hibernate.cfg.xml");
//		return builder.buildSessionFactory();
//	}
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		props.setProperty("hibernate.current_session_context_class", "thread");
		props.setProperty("hibernate.show_sql", "true");
		builder.addProperties(props);
		
		builder.addAnnotatedClasses(CustomerBean.class, ProductBean.class);
		return builder.buildSessionFactory();
	}
}

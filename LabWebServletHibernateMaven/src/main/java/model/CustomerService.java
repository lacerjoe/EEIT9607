package model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	private CustomerDAO customerDao;
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if (newPassword!=null && newPassword.length()!=0) {
				byte[] temp = newPassword.getBytes();	//user input
				return customerDao.update(
						temp, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if (password!=null && password.length()!=0) {
				byte[] temp = password.getBytes();	//user input
				byte[] pass = bean.getPassword();	//database
				if (Arrays.equals(pass, temp)) {
					return bean;
				} 
			}
		}
		return null;
	}
}

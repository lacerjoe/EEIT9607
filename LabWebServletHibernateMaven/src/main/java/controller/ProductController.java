package controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.ProductBean;
import model.ProductService;
import model.spring.PrimitiveNumberEditor;

@Controller
@RequestMapping("/pages/product.controller")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(int.class,
				new PrimitiveNumberEditor(java.lang.Integer.class, true));
		webDataBinder.registerCustomEditor(double.class, "price",
				new PrimitiveNumberEditor(java.lang.Double.class, true));
		webDataBinder.registerCustomEditor(java.util.Date.class, "make",
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
	public String method(ProductBean bean, BindingResult bindingResult, Model model,
			@RequestParam("prodaction") String prodaction) {
		
		System.out.println("bean="+bean);
		System.out.println("bindingResult="+bindingResult);
//接收資料
//轉換資料
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);
		
		if(bindingResult!=null && bindingResult.hasErrors()) {
			if(bindingResult.getFieldError("id")!=null) {
				errors.put("xxx1", "Id必須是整數(MVC)");
			}
			if(bindingResult.getFieldError("price")!=null) {
				errors.put("xxx2", "Price必須是數字(MVC)");
			}
			if(bindingResult.getFieldError("make")!=null) {
				errors.put("xxx3", "Make必須是符合YYYY-MM-DD格式的日期(MVC)");
			}
			if(bindingResult.getFieldError("expire")!=null) {
				errors.put("xxx4", "Expire必須是整數(MVC)");
			}
		}
		
//驗證資料
		if("Insert".equals(prodaction) || "Update".equals(prodaction) || "Delete".equals(prodaction)) {
			if(bean.getId()==0) {
				errors.put("xxx1", "請輸入Id以便執行"+prodaction);
			}
		}
		
		if(errors!=null && !errors.isEmpty()) {
			return "product.error";
		}
		
//呼叫Model, 根據Model執行結果，呼叫View
		if("Select".equals(prodaction)) {
			List<ProductBean> result = productService.select(bean);
			model.addAttribute("select", result);
			return "product.select";
		} else if(prodaction!=null && prodaction.equals("Insert")) {
			ProductBean result = productService.insert(bean);
			if(result==null) {
				errors.put("action", "Insert fail");
			} else {
				model.addAttribute("insert", result);
			}
			return "product.error";
		} else if(prodaction!=null && prodaction.equals("Update")) {
			ProductBean result = productService.update(bean);
			if(result==null) {
				errors.put("action", "Update fail");
			} else {
				model.addAttribute("update", result);
			}
			return "product.error";
		} else if(prodaction!=null && prodaction.equals("Delete")) {
			boolean result = productService.delete(bean);
			if(!result) {
				model.addAttribute("delete", 0);
			} else {
				model.addAttribute("delete", 1);
			}
			return "product.error";
		} else  {
			errors.put("action", "Unknown Action:"+prodaction);
			return "product.error";
		}
	}
}

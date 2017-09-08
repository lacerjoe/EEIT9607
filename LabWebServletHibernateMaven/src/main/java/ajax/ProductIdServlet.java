package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import model.ProductBean;
import model.ProductService;

@WebServlet(
		urlPatterns={"/pages/product.view"}
)
public class ProductIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	@Override
	public void init() throws ServletException {
		ApplicationContext context = (ApplicationContext) 
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		productService = (ProductService) context.getBean("productService");}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getMethod();
		System.out.println("method="+method+", "+request.getRequestURI());
		
		String temp = request.getParameter("id");
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder output = new StringBuilder();
		int id = 0;
		if(temp==null || temp.trim().length()==0) {
			output.append("ID是必要欄位");
		} else {
			try {
				id = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				output.append("ID必需是數字");
			}
		}

		if(output!=null && output.length()!=0) {
			JSONObject obj = new JSONObject();
			obj.put("text", output.toString());
			obj.put("hasMoreData", false);
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(obj);
			out.write(jsonArray.toString());
			out.close();
			return;
		}
		
		ProductBean bean = new ProductBean();
		bean.setId(id);
		List<ProductBean> result = productService.select(bean);
		
		JSONArray jsonArray = new JSONArray();
		if(result==null || result.isEmpty()) {
			JSONObject obj = new JSONObject();
			obj.put("text", "ID不存在");
			obj.put("hasMoreData", false);
			jsonArray.put(obj);
		} else {
			JSONObject obj1 = new JSONObject();
			obj1.put("text", "ID存在");
			obj1.put("hasMoreData", true);
			jsonArray.put(obj1);
			
			ProductBean data = result.get(0);
			JSONObject obj2 = new JSONObject();
			obj2.put("id", data.getId());
			obj2.put("name", data.getName());
			obj2.put("price", data.getPrice());
			obj2.put("make", data.getMake().toString());
			obj2.put("expire", data.getExpire());
			jsonArray.put(obj2);
		}
		out.write(jsonArray.toString());
		out.close();
		return;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}

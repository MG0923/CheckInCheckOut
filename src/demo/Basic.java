package demo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Basic extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		HashMap<String, String> userDetails=new HashMap<String, String>();
		boolean userFlag=false;
		userDetails.put("MohitGupta", "mohit123");
		userDetails.put("ArpitShukla", "arpit123");
		userDetails.put("ArifKhan", "arif123");
		userDetails.put("ArihantJain", "arihant123");
		userDetails.put("Piyush", "piyush123");
		userDetails.put("ReetikaSingh", "reetika123");
		userDetails.put("GaneshTiwary", "ganesh123");
		userDetails.put("KapilRana", "kapil123");
		userDetails.put("HarshChauhan", "harsh123");
		userDetails.put("Admin","admin123");
		userDAO details=new userDAO();
		details.setUserDetail(userDetails);
		PrintWriter out = response.getWriter();
		String name = request.getParameter("userName");
		String pass = request.getParameter("userPass");
		RequestDispatcher rd = request.getRequestDispatcher("servlet1");
		if(!name.equals("") || !pass.equals(""))
		{
			userFlag=details.checkUser(name, pass);

		}
		System.out.println("Name " + name + " Password " + pass);
		if (userFlag) {
			HttpSession session=request.getSession();
			session.setAttribute("username",name);
			rd.forward(request, response);
		} else {
			/*out.println("<script type=\"text/javascript\">");
			out.println("alert('User or Password incorrect');");
			out.println("location='login.html';");
			out.println("</script>");*/
			response.sendRedirect("login.html");
		}

	}
	

}

package demo;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

public class Basic extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		HashMap<String, String> userDetails = new HashMap<String, String>();
		boolean userFlag = false;
		HttpSession session = request.getSession();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance(); // fetch date and time
		PrintWriter out = response.getWriter();

		userDetails.put("TestUser", "TestUser123");  //for testuser login
		userDetails.put("MohitGupta", "mohit123");
		userDetails.put("ArpitShukla", "arpit123");
		userDetails.put("ArifKhan", "arif123");
		userDetails.put("ArihantJain", "arihant123");
		userDetails.put("Piyush", "piyush123");
		userDetails.put("ReetikaSingh", "reetika123");
		userDetails.put("GaneshTiwary", "ganesh123");
		userDetails.put("KapilRana", "kapil123");
		userDetails.put("HarshChauhan", "harsh123");
		userDetails.put("Admin", "admin123");

		userDAO details = new userDAO();
		details.setUserDetail(userDetails);

		String name = request.getParameter("userName");
		String pass = request.getParameter("userPass");
		File file = new File(Paths.get("").toAbsolutePath().toString() + "/lock.txt");
		if (!name.equals("") || !pass.equals("")) {
			userFlag = details.checkUser(name, pass);

		}
		if (userFlag) {
			session.setAttribute("username", name);
			// Create the file
			try {
				if (file.createNewFile()) {
					System.out.println("Basic Servlet: File is created!");
					FileWriter writer = new FileWriter(file);
					writer.write(sdf.format(cal.getTime())); // Login time for Lock file
					writer.close();
					RequestDispatcher rd = request.getRequestDispatcher("servlet1");
					rd.forward(request, response);
					return;
				} else {
					System.out.println("Basic Servlet: File already exists.");
					BufferedReader br = new BufferedReader(
							new FileReader(Paths.get("").toAbsolutePath().toString() + "/lock.txt"));
					String sCurrentLine, sLastLine = null;
					while ((sCurrentLine = br.readLine()) != null) {
						System.out.println("LockFileText " + sCurrentLine);
						sLastLine = sCurrentLine;
					}
					br.close();
					String[] status = sLastLine.split("\\s+"); // regex expression for multiple spaces
					LocalDateTime dateTime1 = LocalDateTime.parse(sdf.format(cal.getTime()), formatter);
					LocalDateTime dateTime2 = LocalDateTime.parse(status[0] + " " + status[1], formatter);
					long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
					System.out.println("Time in Seconds " + Math.abs(diffInSeconds));
					if (Math.abs(diffInSeconds) > 300) {
						if (file.delete() && file.createNewFile()) {
							FileWriter writer = new FileWriter(file);
							writer.write(sdf.format(cal.getTime())); // Login Time for Lock file
							writer.close();
							RequestDispatcher rd = request.getRequestDispatcher("servlet1");
							rd.forward(request, response);
							return;
						}

					} else {
						// Website in use alert
						out.println("<script src='Sweet_JS/sweetalert2.js'></script>");
						out.println("<script src='vendor/jquery/jquery-3.2.1.min.js'></script>");
						out.println("<script>");
						out.println("$(document).ready(function(){");
						out.println("swal ( {title:'Website is in Use' , text: 'Please try again later. Thanks!' , type: 'info',");
						out.println("allowOutsideClick: false,allowEscapeKey: false })");
						out.println(".then((result) => {");
						out.println("window.location = 'login.html';");
						out.println("});");
						out.println("});");
						out.println("</script>");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else {
			// Invalid username/Password Alert
			out.println("<script src='Sweet_JS/sweetalert2.js'></script>");
			out.println("<script src='vendor/jquery/jquery-3.2.1.min.js'></script>");
			out.println("<script>");
			out.println("$(document).ready(function(){");
			out.println("swal ( {title: 'Invalid Username/Password!' ,text: ' Please try again ' , type: 'error', ");
			out.println("allowOutsideClick: false, allowEscapeKey: false})");
			out.println(".then((result) => {");
			out.println("window.location = 'login.html';");
			out.println("});");
			out.println("});");
			out.println("</script>");
		}

	}

}

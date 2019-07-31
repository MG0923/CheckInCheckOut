package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogicServlet extends HttpServlet {
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String sCurrentLine, lastLine = null;
		String checkOutButton = request.getParameter("checkout");
		String checkInButton = request.getParameter("checkin");
		String logoutButton = request.getParameter("logout");
		String deleteFileButton = request.getParameter("deleteFile");
		if (logoutButton == null) {
			logoutButton = " ";
		}
		if (deleteFileButton == null)
			deleteFileButton = " ";

		File file = new File(Paths.get("").toAbsolutePath().toString() + "/log.txt");
		HttpSession session = request.getSession();

		Calendar cal = Calendar.getInstance(); // fetch date and time
		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}
		if (checkOutButton == null) {
			if (checkInButton == null) {
			} else if (checkInButton.equals("Check-In")) {
				// Write Content
				FileWriter writer = new FileWriter(file, true);
				writer.write(session.getAttribute("username") + "\t" + "Checked-in" + "\t" + sdf.format(cal.getTime())
						+ "\n");
				writer.close();
				// reading a line on each check-in
				BufferedReader br = new BufferedReader(
						new FileReader(Paths.get("").toAbsolutePath().toString() + "/log.txt"));
				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
				}
				br.close();
				RequestDispatcher rd = request.getRequestDispatcher("servlet1");
				rd.forward(request, response);
			}
		} else if (checkOutButton.equals("Check-Out")) {
			// Write Content
			FileWriter writer = new FileWriter(file, true);
			writer.write(
					session.getAttribute("username") + "\t" + "Checked-out" + "\t" + sdf.format(cal.getTime()) + "\n");
			writer.close();
			RequestDispatcher rd = request.getRequestDispatcher("servlet1");
			rd.forward(request, response);
		}
		System.out.println("Logout button name" + logoutButton);

		try {
			if (logoutButton.equals("Logout")) {
				System.out.println("Inside logout");
				session.invalidate();
				File lockFile = new File(Paths.get("").toAbsolutePath().toString() + "/lock.txt");
				lockFile.delete();
				out.println("<script type=\"text/javascript\">");
				out.println("location='login.html';");
				out.println("</script>");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (deleteFileButton.equals("DeleteFile")) {
			try {
				if (file.length() == 0) {
					if (file.delete()) {
						session.invalidate();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('File successfully Deleted');");
						out.println("location='login.html';");
						out.println("</script>");
						System.out.println(file.getName() + " is deleted!");
					} else {
						session.invalidate();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Error in File deletion. Please try again.');");
						out.println("location='login.html';");
						out.println("</script>");
						System.out.println("Delete operation is failed.");
					}
				} else {
					BufferedReader br = new BufferedReader(
							new FileReader(Paths.get("").toAbsolutePath().toString() + "/log.txt"));
					while ((sCurrentLine = br.readLine()) != null) {
						lastLine = sCurrentLine;
					}
					br.close();
					System.out.println(lastLine);
					String[] status = lastLine.split("\\s+"); // regex expression for multiple spaces
					System.out.println(status[1]);
					if (status[1].equals("Checked-out")) {
						out.println("<script type=\"text/javascript\">");
						out.println("alert('File Cannot Be Deleted');");
						out.println("location='login.html';");
						out.println("</script>");
					} else {
						if (file.delete()) {
							out.println("<script type=\"text/javascript\">");
							out.println("alert('File successfully Deleted');");
							out.println("location='login.html';");
							out.println("</script>");
							System.out.println(file.getName() + " is deleted!");
						} else {
							out.println("<script type=\"text/javascript\">");
							out.println("alert('Error in File deletion. Please try again.');");
							out.println("location='login.html';");
							out.println("</script>");
							System.out.println("Delete operation is failed.");
						}
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		out.close();

	}
}

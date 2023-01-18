package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/czytelnik2")
public class Czytelnik2Serwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/czytelnik2.jsp");
		dispatcher.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();

		String id_czytelnik=req.getParameter("id_czytelnik");
		String email=req.getParameter("email");
		out.println(id_czytelnik);
		out.println(email);
		req.setAttribute("id_czytelnik", "id_czytelnik");
		req.setAttribute("email", "email");
		req.getRequestDispatcher("edytujCzytelnika.jsp").forward(req, res);

	}

}

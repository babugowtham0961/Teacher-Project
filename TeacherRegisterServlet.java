import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TeacherRegisterServlet")
public class TeacherRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect DB
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school", "root", "root");

            // Insert Query
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO teacher (email, password) VALUES (?, ?)");

            ps.setString(1, email);
            ps.setString(2, password);

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h2>Registration Successful ✅</h2>");
            } else {
                out.println("<h2>Registration Failed ❌</h2>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Error Occurred</h2>");
        }
    }
}

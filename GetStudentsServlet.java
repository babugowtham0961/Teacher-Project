import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/GetStudentsServlet")
public class GetStudentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String classNo = request.getParameter("class");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school", "root", "password");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM student WHERE class=?");

            ps.setInt(1, Integer.parseInt(classNo));

            ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder("[");
            while (rs.next()) {
                json.append("{")
                    .append("\"id\":").append(rs.getInt("id")).append(",")
                    .append("\"name\":\"").append(rs.getString("name")).append("\",")
                    .append("\"class\":").append(rs.getInt("class"))
                    .append("},");
            }

            if (json.charAt(json.length() - 1) == ',') {
                json.deleteCharAt(json.length() - 1);
            }

            json.append("]");
            out.print(json.toString());

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/SaveAttendanceServlet")
public class SaveAttendanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school", "root", "root");

            // Get student class
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT class FROM student WHERE id=?");
            ps1.setInt(1, studentId);
            ResultSet rs = ps1.executeQuery();

            int classNo = 0;
            if (rs.next()) {
                classNo = rs.getInt("class");
            }

            // Insert attendance
            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO attendance (student_id, class, status, date) VALUES (?,?,?,?)");

            ps2.setInt(1, studentId);
            ps2.setInt(2, classNo);
            ps2.setString(3, status);
            ps2.setDate(4, Date.valueOf(LocalDate.now()));

            ps2.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

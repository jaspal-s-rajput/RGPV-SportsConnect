package controller;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamReg extends HttpServlet {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rgpv";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2003";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String eventSelection = request.getParameter("eventSelection");
        String college = request.getParameter("college");
        String registrationType = request.getParameter("registrationType");
        String studentName = request.getParameter("studentName");
        String collegeId = request.getParameter("collegeId");
        String teamName = request.getParameter("teamName");
        String teamID = request.getParameter("teamID");
        String teamMembers = request.getParameter("teamMembers");
        
    
//        Part filePart = request.getPart("documentVerification");
//        String fileName = extractFileName(filePart);
//        String uploadDir = getServletContext().getRealPath("/") + "uploads";
//        
//        File fileUploadDir = new File(uploadDir);
//        if (!fileUploadDir.exists()) {
//            fileUploadDir.mkdir();
//        }
//
//        String filePath = uploadDir + File.separator + fileName;
//        filePart.write(filePath);
     
        try  {
               Class.forName("com.mysql.jdbc.Driver");
              Connection conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            String query = "";
            if ("single".equals(registrationType)) {
                query = "INSERT INTO registrations (event, college, registration_type, student_name, college_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, eventSelection);
                    stmt.setString(2, college);
                    stmt.setString(3, registrationType);
                    stmt.setString(4, studentName);
                    stmt.setString(5, collegeId);
                    //stmt.setString(6, filePath);  
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) 
                    {
                        out.println("<h3>Single Player Registration Successful!</h3>");
                    } else 
                    {
                        out.println("<h3>Error in registration. Please try again.</h3>");
                    }
                }
            } else if ("team".equals(registrationType)) {
                query = "INSERT INTO team_registrations (event, college, registration_type, team_name, team_id, team_members) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, eventSelection);
                    stmt.setString(2, college);
                    stmt.setString(3, registrationType);
                    stmt.setString(4, teamName);
                    stmt.setString(5, teamID);
                    stmt.setString(6, teamMembers);
                   // stmt.setString(7, filePath);  
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        out.println("<h3>Team Registration Successful!</h3>");
                    } else {
                        out.println("<h3>Error in registration. Please try again.</h3>");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error in database connection!</h3>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TeamReg.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

    // Helper function to extract file name from the uploaded file
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

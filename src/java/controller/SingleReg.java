/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author JaspalSR
 */
public class SingleReg extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            String eventSelection=request.getParameter("eventSelection");
            String college=request.getParameter("college");
            String registrationType=request.getParameter("registrationType");
              String singleSport=request.getParameter("singleSport");
            
            
            
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rgpv","root","2003");
            String query = "";
            PreparedStatement ps=null;
             if ("single".equals(registrationType)) 
             {
                String name=request.getParameter("name");
                String collegeId=request.getParameter("college_id");
                String aadharCard=request.getParameter("aadharcard");
                query="INSERT INTO registrations (event,college,sports,student_name,adhar_card,college_id) VALUES (?, ?, ?, ?, ?,?)";
                ps=con.prepareStatement(query);
                ps.setString(1, eventSelection);
                ps.setString(2, college);
                ps.setString(3, singleSport);
                ps.setString(4, name);
                ps.setString(5, aadharCard);
                ps.setString(6, collegeId);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0)
                {
                response.sendRedirect("RegistrationSuccess.html");
                }
                else{
                response.sendRedirect("login.html");
                }
             }
             else
             {
                 out.println(eventSelection);
                    out.println(college);
                String teamSport=request.getParameter("teamSport");
                out.println(teamSport);
                String teamId = request.getParameter("teamId");
                out.println(teamId);
                String noOfPlayers = request.getParameter("noOfPlayers");
                out.println(noOfPlayers);
                String coachName = request.getParameter("coachName");
                out.println(coachName);
                StringBuilder teamMembers = new StringBuilder("[");
                for (int i = 1; i <= Integer.parseInt(noOfPlayers); i++) {
                    String playerName = request.getParameter("namePlayer" + i);
                  out.println(playerName);
                    String aadhaarPlayer = request.getParameter("aadhaarPlayer" + i);
                   out.println(aadhaarPlayer);
                    String collegeIdPlayer = request.getParameter("collegeIdPlayer" + i);
                    out.println(collegeIdPlayer);
                    teamMembers.append("{\"name\":\"").append(playerName)
                            .append("\",\"aadhaar\":\"").append(aadhaarPlayer)
                            .append("\",\"collegeId\":\"").append(collegeIdPlayer)
                            .append("\"},");
                }
                teamMembers.setLength(teamMembers.length() - 1);
                teamMembers.append("]");
                query="insert into teams (event_name, college_name, sport, coach_name, no_of_players) VALUES (?, ?, ?, ?, ?)";
                ps=con.prepareStatement(query);
                ps.setString(1, eventSelection);
                ps.setString(2, college);
                ps.setString(3, teamSport);
                ps.setString(4, coachName);
                ps.setString(5, teamMembers.toString());
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0)
                {
                response.sendRedirect("RegistrationSuccess.html");
                }
                else{
                response.sendRedirect("login.html");
                }
               
             }
            
            
    
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        finally{
        out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

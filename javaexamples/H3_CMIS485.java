/**
 * This is a demonstration JDBC servlet.
 * It displays some simple standard output from the PRODUCTS table in the
 * Oracle Database on Nova
 *
 * @author Reggie
 */
package CMIS;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "H3_CMIS485", urlPatterns = {"/H3_CMIS485"})
public class H3_ReggieHaseltine extends HttpServlet
{
   public static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
   public static final String URL = "jdbc:odbc:CMIS";
   public static final String username = "yourVMUserID";
   public static final String password = "yourVMPWD";

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();

         Connection con = null;
         try
         {

            // Load the driver
            Class.forName(DRIVER);

            //Connect to the database
            con = DriverManager.getConnection(URL,username,password);

            // Query the database for the list of products
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery ("SELECT product_id," +
                                              "       product_name," +
                                              "       product_category" +
                                              "  FROM products" +
                                              " ORDER BY product_id");

            // Format the results as a table
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product Listing</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Products</h2>");
            out.println("<table border='1'>");
            out.println("<b><th><tr><td>Product ID</td>" +
                                   "<td>Product Name</td>" +
                                   "<td>Category</td></tr></th></b>");

            while (rs.next()) {

               // Get the columns from the current row of the result set
               String productID = rs.getString(1);
               String productName = rs.getString(2);
               String productCategory = rs.getString(3);

               out.println("<tr><td>" + productID + "</td>" +
                               "<td>" + productName + "</td>" +
                               "<td>" + productCategory + "</td></tr>");
            }

            out.println("</table>");

         }
         catch (Exception e)
         {
            e.printStackTrace (new PrintWriter(out));
         }
         finally
         {

            // Always close the connection
            if (con != null)
            {
               try
               {
                  con.close();
               }
               catch (SQLException ignore) {}
            }
         }
      }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    /* Handles a GET request */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      /* Handles a POST request */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
      //  processRequest(request, response);
            doGet (request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}

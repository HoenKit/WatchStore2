/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.products;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.database.DatabaseConnector;
import model.entity.Category;
import model.entity.Product;

/**
 *
 * @author ADMIN
 */
public class CategoryServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
           Connection connection = DatabaseConnector.getConnection();
           ProductDAO productDAO = new ProductDAO(connection);
            // Get categoryId parameter from the request
            String categoryIdParam = request.getParameter("categoryID");

            if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
               
               try {
                   int categoryId = Integer.parseInt(categoryIdParam);
                   
                   // Retrieve products by category
                   List<Product> products = productDAO.getProductsByCategory(connection, categoryId);
                   CategoryDAO categoryDAO = new CategoryDAO(connection);
                   // Retrieve the list of categories
                   List<Category> categoryList = categoryDAO.getAllCategories();
                   // Set the categoryList as an attribute in the request
                   request.setAttribute("categoryList", categoryList);
                   // Set the products as an attribute in the request
                   request.setAttribute("productList", products);

                   // Forward the request to the "productsByCategory.jsp" page (create this JSP)
                   request.getRequestDispatcher("/listProduct.jsp").forward(request, response);
               } catch (SQLException ex) {
                   Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
        
        
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

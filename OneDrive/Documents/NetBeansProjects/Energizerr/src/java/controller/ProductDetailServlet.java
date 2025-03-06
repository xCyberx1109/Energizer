package controller;

import dal.DAO;
import model.Products;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/productDetail"})
public class ProductDetailServlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String id = request.getParameter("id");
    System.out.println("Received ID: " + id); // Debug: Kiểm tra ID nhận được

    if (id != null && !id.isEmpty()) {
        DAO dao = new DAO();
        Products product = dao.getProductById(Integer.parseInt(id));
        System.out.println("Product found: " + (product != null ? product.getProductName() : "null")); // Debug

        if (product != null) {
            request.setAttribute("product", product);
            request.getRequestDispatcher("productDetail.jsp").forward(request, response);
        } else {
            response.getWriter().println("Product not found.");
        }
    } else {
        response.getWriter().println("Invalid Product ID.");
    }
}

}
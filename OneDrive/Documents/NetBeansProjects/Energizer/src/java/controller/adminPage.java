package controller;

import dal.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Products;

@WebServlet(name = "adminPage", urlPatterns = {"/admin"})
public class adminPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        DAO d = new DAO();
        List<Products> products = d.getAllProducts(); // Get all products
        request.setAttribute("adminPage", products);
        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Error fetching products");
        request.getRequestDispatcher("adminPage.jsp").forward(request, response);
    }
}

    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id_raw = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price_raw = request.getParameter("price");
        String quantity_raw = request.getParameter("quantity");
        String images = request.getParameter("images");
        String describe = request.getParameter("describe");

        try {
            int id = Integer.parseInt(id_raw);
            double price = Double.parseDouble(price_raw);
            int quantity = Integer.parseInt(quantity_raw);

            Products p = new Products(name, category, images, describe, price, quantity, id);
            DAO d = new DAO();
            d.update(p);

            response.sendRedirect("admin"); 

        } catch (NumberFormatException e) {
            e.printStackTrace(); 
            request.setAttribute("error", "Invalid price or quantity format");
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles admin product updates";
    }
}

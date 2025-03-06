package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RemoveCartServlet", urlPatterns = {"/removefromcart"})
public class RemoveCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("id");
        if (productId == null) {
            response.sendRedirect("cart.jsp");
            return;
        }

        Cookie[] cookies = request.getCookies();
        String cartData = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    cartData = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    break;
                }
            }
        }

        // Debug: In ra giá trị cartData trước khi xóa
        System.out.println("Cart before removal: " + cartData);

        // Xử lý xóa sản phẩm
        List<String> newCartItems = new ArrayList<>();
        if (!cartData.isEmpty()) {
            String[] items = cartData.split(",");
            for (String item : items) {
                if (!item.isEmpty()) {
                    String[] parts = item.split("-");
                    if (parts.length == 2 && !parts[0].equals(productId)) {
                        newCartItems.add(item);  // Giữ lại sản phẩm không bị xóa
                    }
                }
            }
        }

        // Cập nhật lại cookie giỏ hàng
        String updatedCartData = String.join(",", newCartItems);
        
        // Nếu giỏ hàng trống, xóa cookie bằng cách set MaxAge = 0
        Cookie updatedCart;
        if (updatedCartData.isEmpty()) {
            updatedCart = new Cookie("cart", "");
            updatedCart.setMaxAge(0); // Xóa cookie
        } else {
            updatedCart = new Cookie("cart", URLEncoder.encode(updatedCartData, "UTF-8"));
            updatedCart.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        }
        
        updatedCart.setPath("/");
        response.addCookie(updatedCart);

        // Debug: In ra giá trị cartData sau khi xóa
        System.out.println("Cart after removal: " + updatedCartData);

        response.sendRedirect("cart.jsp");
    }
}

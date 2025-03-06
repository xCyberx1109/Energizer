package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.net.URLDecoder;

@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/updateCart"})
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String productId = request.getParameter("id");
        String action = request.getParameter("action");

        if (productId != null && action != null) {
            Cookie[] cookies = request.getCookies();
            String cartData = "";

            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    cartData = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    break;
                }
            }

            String[] items = cartData.split(",");
            StringBuilder newCartData = new StringBuilder();
            boolean productExists = false;

            for (String item : items) {
                if (!item.isEmpty()) {
                    String[] parts = item.split("-");
                    int id = Integer.parseInt(parts[0]);
                    int quantity = Integer.parseInt(parts[1]);

                    if (id == Integer.parseInt(productId)) {
                        if ("increase".equals(action)) {
                            quantity++;
                        } else if ("decrease".equals(action) && quantity > 1) {
                            quantity--;
                        } else if ("remove".equals(action)) {
                            continue; // Bỏ qua sản phẩm này để xóa khỏi giỏ hàng
                        }
                        productExists = true;
                    }
                    newCartData.append(id).append("-").append(quantity).append(",");
                }
            }

            if (!productExists && "increase".equals(action)) {
                newCartData.append(productId).append("-1,");
            }

            // Xóa cookie cũ
            Cookie oldCart = new Cookie("cart", "");
            oldCart.setMaxAge(0);
            oldCart.setPath("/");
            response.addCookie(oldCart);

            // Ghi cookie mới
            Cookie updatedCart = new Cookie("cart", URLEncoder.encode(newCartData.toString(), "UTF-8"));
            updatedCart.setMaxAge(60 * 60 * 24 * 7);
            updatedCart.setPath("/");
            response.addCookie(updatedCart);

            System.out.println("Updated Cart Data: " + newCartData.toString()); // Debug
        }

        response.sendRedirect("cart.jsp");
    }
}


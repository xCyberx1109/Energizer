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

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String quantity = request.getParameter("quantity");

        if (id != null && quantity != null) {
            // Đọc Cookie hiện tại
            String cartData = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart".equals(cookie.getName())) {
                        cookie.setPath("/");
                        cartData = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        break;
                    }
                }
            }

            // Thêm sản phẩm vào giỏ hàng
            String newCartData = updateCartData(cartData, id, quantity);

            // Lưu lại vào Cookie
            Cookie cartCookie = new Cookie("cart", URLEncoder.encode(newCartData, "UTF-8"));
            cartCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
            cartCookie.setPath("/");
            response.addCookie(cartCookie);
        }

        response.sendRedirect("cart.jsp");
    }

    private String updateCartData(String cartData, String id, String quantity) {
        StringBuilder newCart = new StringBuilder();
        boolean found = false;

        String[] items = cartData.split(",");
        for (String item : items) {
            if (!item.isEmpty()) {
                String[] parts = item.split("-");
                if (parts[0].equals(id)) {
                    int newQuantity = Integer.parseInt(parts[1]) + Integer.parseInt(quantity);
                    newCart.append(id).append("-").append(newQuantity).append(",");
                    found = true;
                } else {
                    newCart.append(item).append(",");
                }
            }
        }

        if (!found) {
            newCart.append(id).append("-").append(quantity).append(",");
        }

        return newCart.toString();
    }
}

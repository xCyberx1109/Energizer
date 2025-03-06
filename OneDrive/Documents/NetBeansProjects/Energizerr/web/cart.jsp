<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<%@page import="java.net.URLDecoder, java.net.URLEncoder"%>
<%@page import="java.util.ArrayList, java.util.List"%>
<%@page import="dal.DAO"%>
<%@page import="model.Products"%>
<html>
    <head>
        <title>Shopping Cart</title>
        <link rel="stylesheet" href="asset/css/bootstrap.min.css"/>
    </head>
    <body>
        <div class="container mt-5">
            <h2>Your Shopping Cart</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        DAO dao = new DAO();
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

                        double totalPrice = 0;
                        List<String> cartItems = new ArrayList<>();
                        if (!cartData.isEmpty()) {
                            String[] items = cartData.split(",");
                            for (String item : items) {
                                if (!item.isEmpty()) {
                                    String[] parts = item.split("-");
                                    int productId = Integer.parseInt(parts[0]);
                                    int quantity = Integer.parseInt(parts[1]);

                                    Products p = dao.getProductById(productId);
                                    if (p != null) {
                                        cartItems.add(productId + "-" + quantity);
                    %>
                    <tr>
                        <td><%= p.getProductName() %></td>
                        <td><%= p.getPrice() %> $</td>
                        <td>
                            <form action="updateCart" method="post" class="d-inline">
                                <input type="hidden" name="id" value="<%= productId %>">
                                <button type="submit" name="action" value="decrease" class="btn btn-sm btn-secondary">-</button>
                                <span id="qty-<%= productId %>"><%= quantity %></span>
                                <button type="submit" name="action" value="increase" class="btn btn-sm btn-secondary">+</button>
                            </form>
                        </td>
                        <td><%= p.getPrice() * quantity %> $</td>
                        <td>
                            <a href="removefromcart?id=<%= productId %>" class="btn btn-danger btn-sm">Remove</a>
                        </td>
                    </tr>
                    <% 
                                        totalPrice += p.getPrice() * quantity;
                                    }
                                }
                            }
                        }
                        String updatedCartData = String.join(",", cartItems);
                        Cookie updatedCart = new Cookie("cart", URLEncoder.encode(updatedCartData, "UTF-8"));
                        updatedCart.setMaxAge(60 * 60 * 24 * 7);
                        updatedCart.setPath("/");
                        response.addCookie(updatedCart);
                    %>
                </tbody>
            </table>
            <h4>Total: <%= totalPrice %> $</h4>
            <a href="checkout.jsp" class="btn btn-primary">Proceed to Checkout</a>
        </div>
    </body>
</html>
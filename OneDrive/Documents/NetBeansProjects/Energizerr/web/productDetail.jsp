<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Product Detail</title>
        <link rel="stylesheet" href="asset/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="asset/css/styles.css"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a href="${pageContext.request.contextPath}/">
                <img style="width: 100px;height: 80px" class="navbar-brand" src="asset/images/logo.jpg">
            </a>
        </nav>

        <div class="container mt-5">
            <div class="row">
                <div class="col-md-6">
                    <img src="${product.images}" class="img-fluid"/>
                </div>
                <div class="col-md-6">
                    <h2>${product.productName}</h2>
                    <h4>Category: ${product.category}</h4>
                    <h5>Price: ${product.price}$</h5>
                    <p>${product.description}</p>
                    <p>Stock Quantity: ${product.stockQuantity}</p>
                    
                    <!-- Form gửi request đến CartServlet để lưu vào Cookie -->
                    <form action="cart" method="post">
                        <input type="hidden" name="id" value="${product.productID}">
                        <input type="hidden" name="name" value="${product.productName}">
                        <input type="hidden" name="price" value="${product.price}">
                        <label>Quantity:</label>
                        <input type="number" name="quantity" value="1" min="1" class="form-control w-25 my-2">
                        <button type="submit" class="btn btn-success">Add to Cart</button>
                    </form>

                    <!-- Nút xem giỏ hàng -->
                    <a href="cart.jsp" class="btn btn-primary mt-3">View Cart</a>
                </div>
            </div>
        </div>

    </body>
</html>

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
                    <a href="cart.jsp?id=${product.productID}" class="btn btn-success">Add to Cart</a>
                </div>
            </div>
        </div>

    </body>
</html>
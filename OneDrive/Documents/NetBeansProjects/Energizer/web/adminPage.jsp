

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <title>Energiz3r</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="asset/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="asset/css/styles.css"/>
        <script src="asset/script/jquery-3.7.1.min.js"></script>
        <link rel="icon" type="image/x-icon" href="asset/images/favicon.ico">
    </head>
    <body>

        <h1>Update Category</h1>
        <h4><a class="btn btn-primary" href="add.jsp">Add new</a></h4>
        <c:forEach items="${requestScope.adminPage != null ? requestScope.adminPage : dataP}" var="p">
            <div style="display: flex;margin: 50px; border: solid black 5px">
                <div>
                    <img style="height: 450px; width: 450px"class="card-img-top" src="${p.images}"/>
                </div>
                <div style="margin: 50px; display: flex; flex-direction: column; gap: 10px; max-width: 400px;">
                    <c:set var="c" value="${requestScope.dataP}"/>
                    <form action="admin" method="post">
                        <input type="hidden" value="${p.productID}" name="id"/> 
                        <label>Enter Name:</label> 
                        <input type="text" value="${p.productName}" name="name"/><br/>
                        <label>Enter Category:</label> 
                        <input type="text" value="${p.category}" name="category"/><br/>
                        <label>Enter Price:</label> 
                        <input type="number" value="${p.price}" name="price"/><br/>
                        <label>Enter Quantity:</label> 
                        <input type="number" value="${p.stockQuantity}" name="quantity"/><br/>
                        <label>Enter Images:</label> 
                        <input type="text" value="${p.images}" name="images"/><br/>
                        <label>Enter Description:</label> 
                        <textarea name="describe" rows="5" cols="50">${p.description}</textarea><br/>
                        <input type="submit" value="Update" style="width: 100px; align-self: flex-start;"/>
                        <a onclick="doDelete('${p.productName}')" class="btn btn-danger"  href="#">Delete</a>
                    </form>
                </div>
            </div>
        </c:forEach>
        <script type="text/javascript">
            function doDelete(name) {
                if (confirm('are you sure to delete: ' + name)) {
                    window.location = 'delete?name=' + encodeURIComponent(name);
                }
            }
        </script>

    </body> 
</html>

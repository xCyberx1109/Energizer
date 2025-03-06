<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Add a new Category</h1>
        <h3 style="color: red">${requestScope.error}</h3>
        <form action="add">
            enter name:<input type="text" name="name"/><br/>
            enter category<input type="text" name="category"/><br/>
            enter price<input type="number" name="price"/><br/>
            enter quantity<input type="number" name="stockQuantity"/><br/>
            enter images<input type="text" name="images"/><br/>
            enter describe:<textarea name="describe"></textarea><br/>
            <input type="submit" value="ADD"/>
        </form>
    </body>
</html>

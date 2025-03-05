<%-- 
    Document   : home
    Created on : Feb 19, 2025, 10:21:11 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <div class="row">
            <div class="col-2"></div>
            <div class="col-10" >
                <div class="row">
                    <c:forEach items="${dataP}" var="p">
                        <div class="card col-3 m-3" style="width: 18rem">
                            <img class="card-img-top" src="asset/${p.image}"/>
                            <div class="card-body">

                                <h5 class="card-title">${p.name}</h5>
                                <p class="card-text">${p.describe}</p>
                                <h5>Price: ${p.price}.0 VND</h5>
                                <a href="#" class="btn btn-primary" >Detail</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>

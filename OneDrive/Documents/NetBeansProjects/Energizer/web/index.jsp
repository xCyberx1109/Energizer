
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a href="${pageContext.request.contextPath}/">
                <img style="width: 100px;height: 80px"class="navbar-brand" src="asset/images/logo.jpg">
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">

                    <div class="dropdown">
                        <button class="dropdown-btn" onclick="toggleDropdown()">Categories ▼</button>
                        <div class="dropdown-content" id="dropdownMenu">
                            <a href="${pageContext.request.contextPath}/category?category=Alkaline">Alkaline</a>
                            <a href="${pageContext.request.contextPath}/category?category=Lithium">Lithium</a>
                            <a href="${pageContext.request.contextPath}/category?category=Rechargeable">Rechargeable</a>
                            <a href="${pageContext.request.contextPath}/category?category=Charger">Charger</a>
                            <a href="${pageContext.request.contextPath}/category?category=Flashlight">Flashlight</a>
                            <a href="${pageContext.request.contextPath}/category?category=Power+Bank">Power Bank</a>
                        </div>
                    </div>
                </ul>

                <form style="margin: 0px 100px 0px -10%;width: 1000px" method="get" action="search">
                    <input class="form-control" name="key" type="search" placeholder="Search" aria-label="Search">
                </form>


                <ul class="navbar-nav mr-auto">
                    <c:if test="${sessionScope.account==null}">
                        <a class="btn btn-secondary" href="login">login</a>
                    </c:if>
                    <c:if test="${sessionScope.account!=null}">

                        <a href="logout">LOGOUT</a>

                    </c:if>
                </ul>
            </div>
        </nav>


        <h1 style="font-size: 1000%; text-align: center;background-color: gray; margin: 10px 0px 10px 0px">
            CAMPAIGN
        </h1>



        <div class="row">
            <div class="col-2"></div>
            <div class="col-10" >
                <div class="row">
                    <c:forEach items="${requestScope.ProductByCategory != null ? requestScope.ProductByCategory : dataP}" var="p">
                        <div class="card col-3 m-3" style="width: 18rem">
                            <img style="height: 50%; width: 100%"class="card-img-top" src="${p.images}"/>
                            <div class="card-body">
                                <h5 class="card-title">${p.productName}</h5>
                                <h5>Price: ${p.price}$</h5>
                                <a href="${pageContext.request.contextPath}/productDetail?id=${p.productID}" class="btn btn-primary">Detail</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>


        <div class="loader-wrapper">        
            <div class="spinner-1 "></div>           
        </div>




        <script>
            //Drop down
            function toggleDropdown() {
                var dropdown = document.getElementById("dropdownMenu");
                if (dropdown.style.display === "block") {
                    dropdown.style.display = "none";
                } else {
                    dropdown.style.display = "block";
                }
            }
            window.onclick = function (event) {
                if (!event.target.matches('.dropdown-btn')) {
                    var dropdown = document.getElementById("dropdownMenu");
                    dropdown.style.display = "none";
                }
            };

            //loading animation
            $(document).ready(function () {
                // Check if the loader has already been shown in this session
                if (!sessionStorage.getItem('loaderShown')) {
                    $(".loader-wrapper").show(); // Ensure loader is visible

                    $(window).on("load", function () {
                        $(".loader-wrapper").fadeOut(3000, function () {
                            // Mark loader as shown
                            sessionStorage.setItem('loaderShown', 'true');
                        });
                    });
                } else {
                    // If the loader has already been shown, hide it immediately
                    $(".loader-wrapper").hide();
                }
            });

        </script>

    </body>
</html>

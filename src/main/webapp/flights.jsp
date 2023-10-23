<%@ page import="com.example.airafrica.Entity.Traveller" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.airafrica.Entity.Airport" %>
<%@ page import="com.example.airafrica.Entity.Flight" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Objects" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>AIRAFRICA - Flight Booking Website</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Flight Booking" name="keywords">
    <meta content="Flight Booking" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
</head>
<%
    Traveller traveller = (Traveller) request.getSession().getAttribute("Traveller");
    List<Airport> airports = (List<Airport>) request.getAttribute("airports");
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    Flight selectedOptions = (Flight) request.getAttribute("options_selected");
    // Get the selected departure and arrival airports if available
    Airport selectedDepartureAirport = (selectedOptions != null) ? selectedOptions.getDepartureAirport() : null;
    Airport selectedArrivalAirport = (selectedOptions != null) ? selectedOptions.getArrivalAirport() : null;

    // Get the selected departure and arrival dates if available
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String selectedDepartureDate = (selectedOptions != null && selectedOptions.getDepartureDate() != null) ? dateFormat.format(selectedOptions.getDepartureDate()) : "";
    String selectedArrivalDate = (selectedOptions != null && selectedOptions.getArrivalDate() != null) ? dateFormat.format(selectedOptions.getArrivalDate()) : "";
%>
<body>
<div class="container-fluid bg-light pt-3 d-none d-lg-block">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 text-center text-lg-left mb-2 mb-lg-0">
                <div class="d-inline-flex align-items-center">
                    <p><i class="fa fa-envelope mr-2"></i>Contact@airafrica.com</p>
                    <p class="text-body px-3">|</p>
                    <p><i class="fa fa-phone-alt mr-2"></i>+012 345 6789</p>
                </div>
            </div>
            <div class="col-lg-6 text-center text-lg-right">
                <div class="d-inline-flex align-items-center">
                    <a class="text-primary px-3" href="">
                        <i class="fab fa-facebook-f"></i>
                    </a>
                    <a class="text-primary px-3" href="">
                        <i class="fab fa-twitter"></i>
                    </a>
                    <a class="text-primary px-3" href="">
                        <i class="fab fa-linkedin-in"></i>
                    </a>
                    <a class="text-primary px-3" href="">
                        <i class="fab fa-instagram"></i>
                    </a>
                    <a class="text-primary pl-3" href="">
                        <i class="fab fa-youtube"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid position-relative nav-bar p-0">
    <div class="container-lg position-relative p-0 px-lg-3" style="z-index: 9;">
        <nav class="navbar navbar-expand-lg bg-light navbar-light shadow-lg py-3 py-lg-0 pl-3 pl-lg-5">
            <a href="" class="navbar-brand">
                <h1 class="m-0 text-primary"><span class="text-dark">AIR</span>AFRICA</h1>
            </a>
            <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between px-3" id="navbarCollapse">
                <div class="navbar-nav ml-auto py-0">
                    <a href="/home" class="nav-item nav-link active">Home</a>
                    <a href="/about" class="nav-item nav-link">About</a>
                    <a href="/contact" class="nav-item nav-link">Contact</a>
                    <% if (traveller != null) { %>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown"><%= traveller.getUsername() %></a>
                        <div class="dropdown-menu border-0 rounded-0 m-0">
                            <form action="/login" method="post">
                                <input name="choice" value="logout" style="display: none">
                                <button type="submit" class="dropdown-item">Logout</button>
                            </form>
                        </div>
                    </div>
                    <% } else { %>
                    <a href="/login" class="nav-item nav-link">Guest</a>
                    <% } %>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- Navbar End -->
    <!-- Header Start -->
    <div class="container-fluid page-header">
        <div class="container">
            <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 400px">
                <h3 class="display-4 text-white text-uppercase">Flights</h3>
                <div class="d-inline-flex text-white">
                    <p class="m-0 text-uppercase"><a class="text-white" href="/home">Home</a></p>
                    <i class="fa fa-angle-double-right pt-1 px-3"></i>
                    <p class="m-0 text-uppercase">Flights</p>
                </div>
            </div>
        </div>
    </div>
    <!-- Header End -->


    <!-- Booking Start -->
    <div class="container-fluid booking mt-5 pb-5">
        <div class="container pb-5">
            <div class="bg-light shadow" style="padding: 30px;">
                <form action="flights" method="post" class="row align-items-center" style="min-height: 60px;">
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3 mb-md-0">
                                    <select class="custom-select px-4" name="departureAirportId" style="height: 47px">
                                        <option value="">Starting Location</option>
                                        <%
                                            for (Airport airport : airports) {
                                        %>
                                        <option value="<%= airport.getId() %>"
                                                <% if (selectedDepartureAirport != null && Objects.equals(selectedDepartureAirport.getId(), airport.getId())) { %>
                                                selected
                                                <% } %>
                                        ><%= airport.getCity() %></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3 mb-md-0">
                                    <select class="custom-select px-4" name="arrivalAirportId" style="height: 47px">
                                        <option value="">Destination</option>
                                        <%
                                            for (Airport airport : airports) {
                                        %>
                                        <option value="<%= airport.getId() %>"
                                                <% if (selectedArrivalAirport != null && Objects.equals(selectedArrivalAirport.getId(), airport.getId())) { %>
                                                selected
                                                <% } %>
                                        ><%= airport.getCity() %></option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary btn-block" type="submit" style="height: 47px; margin-top: -2px;">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Booking End -->
    

    <!-- Destination Start -->
    <div class="container-fluid py-5">
        <div class="container pt-5 pb-3">
            <div class="text-center mb-3 pb-3">
                <h6 class="text-primary text-uppercase" style="letter-spacing: 5px;">Flights</h6>
                <h1>Available Flights</h1>
            </div>
            <div class="row">
                <%
                    if (flights.isEmpty()){
                %>
                <div class="row justify-content-center w-100">
                    <h3>No Flights</h3>
                </div>

                <%

                }else{
                    for (Flight flight : flights) { %>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card1">
                        <div class="card_container">
                            <div class="cloud front">
                                <span class="left-front"></span>
                                <span class="right-front"></span>
                            </div>
                            <span class="sun sunshine"></span>
                            <span class="sun"></span>
                            <div class="cloud back">
                                <span class="left-back"></span>
                                <span class="right-back"></span>
                            </div>
                        </div>
                        <div class="card1-header">
                            <span>Depart City: <%= flight.getDepartureAirport().getCity() %></span>
                            <span>Depart Date: <%= (flight.getDepartureDate() != null) ? new SimpleDateFormat("yyyy-MM-dd").format(flight.getDepartureDate()) : "" %></span>
                        </div>
                        <span class="Distination">Destination:<br>
                            <span> > > <%= flight.getArrivalAirport().getCity() %></span>
                        </span>
                        <div class="book">
                            <% if (traveller != null) { %>
                                <a href="/flights?id=<%= flight.getId() %>">Book Flight</a>
                            <% } else {%>
                                <a href="/login" >Login To Book</a>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } }%>
            </div>
        </div>
    </div>
    <!-- Destination Start -->


    <!-- Footer Start -->
    <div class="container-fluid bg-dark text-white-50 py-5 px-sm-3 px-lg-5" style="margin-top: 90px;">
        <div class="row pt-5">
            <div class="col-lg-3 col-md-6 mb-5">
                <a href="" class="navbar-brand">
                    <h1 class="text-primary"><span class="text-white">AIR</span>AFRICA</h1>
                </a>
                <p>Sed ipsum clita tempor ipsum ipsum amet sit ipsum lorem amet labore rebum lorem ipsum dolor. No sed vero lorem dolor dolor</p>
                <h6 class="text-white text-uppercase mt-4 mb-3" style="letter-spacing: 5px;">Follow Us</h6>
                <div class="d-flex justify-content-start">
                    <a class="btn btn-outline-primary btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                    <a class="btn btn-outline-primary btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                    <a class="btn btn-outline-primary btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                    <a class="btn btn-outline-primary btn-square" href="#"><i class="fab fa-instagram"></i></a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-5">
                <h5 class="text-white text-uppercase mb-4" style="letter-spacing: 5px;">Our Services</h5>
                <div class="d-flex flex-column justify-content-start">
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>About</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Destination</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Services</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Packages</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Guides</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Testimonial</a>
                    <a class="text-white-50" href="#"><i class="fa fa-angle-right mr-2"></i>Blog</a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-5">
                <h5 class="text-white text-uppercase mb-4" style="letter-spacing: 5px;">Usefull Links</h5>
                <div class="d-flex flex-column justify-content-start">
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>About</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Destination</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Services</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Packages</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Guides</a>
                    <a class="text-white-50 mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>Testimonial</a>
                    <a class="text-white-50" href="#"><i class="fa fa-angle-right mr-2"></i>Blog</a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-5">
                <h5 class="text-white text-uppercase mb-4" style="letter-spacing: 5px;">Contact Us</h5>
                <p><i class="fa fa-map-marker-alt mr-2"></i>123 Street, New York, USA</p>
                <p><i class="fa fa-phone-alt mr-2"></i>+012 345 67890</p>
                <p><i class="fa fa-envelope mr-2"></i>info@example.com</p>
                <h6 class="text-white text-uppercase mt-4 mb-3" style="letter-spacing: 5px;">Newsletter</h6>
                <div class="w-100">
                    <div class="input-group">
                        <input type="text" class="form-control border-light" style="padding: 25px;" placeholder="Your Email">
                        <div class="input-group-append">
                            <button class="btn btn-primary px-3">Sign Up</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid bg-dark text-white border-top py-4 px-sm-3 px-md-5" style="border-color: rgba(256, 256, 256, .1) !important;">
        <div class="row">
            <div class="col-lg-6 text-center text-md-left mb-3 mb-md-0">
                <p class="m-0 text-white-50">Copyright &copy; <a href="#">Domain</a>. All Rights Reserved.</a>
                </p>
            </div>
            <div class="col-lg-6 text-center text-md-right">
                <p class="m-0 text-white-50">Designed by <a href="https://htmlcodex.com">HTML Codex</a>
                </p>
            </div>
        </div>
    </div>
    <!-- Footer End -->


    <!-- Back to Top -->
    <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>


    <!-- JavaScript Libraries -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="lib/tempusdominus/js/moment.min.js"></script>
    <script src="lib/tempusdominus/js/moment-timezone.min.js"></script>
    <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

    <!-- Contact Javascript File -->
    <script src="mail/jqBootstrapValidation.min.js"></script>
    <script src="mail/contact.js"></script>

    <!-- Template Javascript -->
    <script src="js/main.js"></script>
</body>

</html>
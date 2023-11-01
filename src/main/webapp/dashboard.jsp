<%@ page import="com.example.airafrica.Entity.Flight" %>
<%@ page import="com.example.airafrica.Entity.Admin" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.airafrica.Entity.Airport" %>
<%@ page import="com.example.airafrica.Entity.Airline" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>AIRAFRICA - Dashboard</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

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
//    Admin admin = (Admin) request.getSession().getAttribute("admin");
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    List<Airport> airports = (List<Airport>) request.getAttribute("airports");
    List<Airline> airlines = (List<Airline>) request.getAttribute("airlines");
%>
<body>
<div class="container">
    <h1>Flight Dashboard</h1>

    <button class="btn btn-primary rounded-lg m-2" data-toggle="modal" data-target="#createFlightModal">Create Flight</button>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Flight ID</th>
            <th>Departure Airport</th>
            <th>Arrival Airport</th>
            <th>Departure Date</th>
            <th>Arrival Date</th>
            <th>Capacity</th>
            <th>Airline</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Flight flight : flights) { %>
        <tr>
            <td><%= flight.getId() %></td>
            <td><%= flight.getDepartureAirport().getName() %></td>
            <td><%= flight.getArrivalAirport().getName() %></td>
            <td><%= flight.getDepartureDate() %></td>
            <td><%= flight.getArrivalDate() %></td>
            <td><%= flight.getCapacity() %></td>
            <td><%= flight.getAirline().getName() %></td>
            <td>
                <button class="btn btn-info rounded-lg m-1" data-toggle="modal" data-target="#updateFlightModal"
                        data-flight-id="<%= flight.getId() %>" data-departure-date="<%= flight.getDepartureDate() %>"
                        data-arrival-date="<%= flight.getArrivalDate() %>" data-capacity="<%= flight.getCapacity() %>">
                    Update
                </button>
                <form action="<%= request.getContextPath() %>/dashboard?action=delete" method="post">
                    <input type="hidden" name="flightId" value="<%= flight.getId() %>">
                    <button type="submit" class="btn btn-danger rounded-lg m-1" >
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<div class="modal fade" id="createFlightModal" tabindex="-1" role="dialog" aria-labelledby="createFlightModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createFlightModalLabel">Create Flight</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="<%= request.getContextPath() %>/dashboard?action=create" method="POST">
                    <div class="form-group">
                        <label for="departureAirport">Departure Airport</label>
                        <select class="form-control" id="departureAirport" name="departureAirport">
                            <% for (Airport airport : airports) { %>
                            <option value="<%= airport.getId() %>"><%= airport.getName() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="arrivalAirport">Arrival Airport</label>
                        <select class="form-control" id="arrivalAirport" name="arrivalAirport">
                            <% for (Airport airport : airports) { %>
                            <option value="<%= airport.getId() %>"><%= airport.getName() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="departureDate">Departure Date</label>
                        <input type="datetime-local" class="form-control" id="departureDate" name="departureDate" placeholder="YYYY-MM-DD HH:MM:SS">
                    </div>
                    <div class="form-group">
                        <label for="arrivalDate">Arrival Date</label>
                        <input type="datetime-local" class="form-control" id="arrivalDate" name="arrivalDate" placeholder="YYYY-MM-DD HH:MM:SS">
                    </div>
                    <div class="form-group">
                        <label for="capacity">Capacity</label>
                        <input type="number" class="form-control" id="capacity" name="capacity">
                    </div>
                    <div class="form-group">
                        <label for="airline_id">Airline</label>
                        <select class="form-control" id="airline_id" name="airline_id">
                            <% for (Airline airline : airlines) { %>
                            <option value="<%= airline.getId() %>"><%= airline.getName() %></option>
                            <% } %>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary rounded-lg m-1">Create</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updateFlightModal" tabindex="-1" role="dialog" aria-labelledby="updateFlightModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateFlightModalLabel">Update Flight</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="<%= request.getContextPath() %>/dashboard?action=update" method="POST">
                    <input type="hidden" id="flightId" name="flightId" value="">
                    <div class="form-group">
                        <label for="departureDate">Departure Date</label>
                        <input type="datetime-local" class="form-control" id="updatedDepartureDate" name="departureDate" placeholder="YYYY-MM-DD HH:MM:SS" value="">
                    </div>
                    <div class="form-group">
                        <label for="arrivalDate">Arrival Date</label>
                        <input type="datetime-local" class="form-control" id="updatedArrivalDate" name="arrivalDate" placeholder="YYYY-MM-DD HH:MM:SS" value="">
                    </div>
                    <div class="form-group">
                        <label for="capacity">Capacity</label>
                        <input type="number" class="form-control" id="updatedCapacity" name="capacity" value="">
                    </div>
                    <button type="submit" class="btn btn-primary rounded-lg m-1">Update</button>
                </form>
            </div>
        </div>
    </div>
</div>

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
<script>
    $(document).ready(function() {
        // Listen for the "Update" button click
        $('.btn-info').on('click', function() {
            var flightId = $(this).data('flight-id');
            var departureDate = $(this).data('departure-date');
            var arrivalDate = $(this).data('arrival-date');
            var capacity = $(this).data('capacity');

            // Set the values in the "Update Flight" modal form fields
            $('#flightId').val(flightId);
            $('#updatedDepartureDate').val(departureDate);
            $('#updatedArrivalDate').val(arrivalDate);
            $('#updatedCapacity').val(capacity);
        });
    });
</script>
</body>
</html>

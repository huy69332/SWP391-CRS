<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.crs.model.Booking" %>
<%@ page import="java.util.List" %>
<%!
    private String escapeHtml(Object value) {
        if (value == null) {
            return "-";
        }
        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<%
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    String bookingLoadError = (String) request.getAttribute("bookingLoadError");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bookings</title>
</head>
<body>
    <table>
        <caption>Bookings</caption>
        <thead>
            <tr>
                <th scope="col">Booking ID</th>
                <th scope="col">Code</th>
                <th scope="col">Customer ID</th>
                <th scope="col">Car ID</th>
                <th scope="col">Rental Type ID</th>
                <th scope="col">Pickup Location ID</th>
                <th scope="col">Return Location ID</th>
                <th scope="col">Start</th>
                <th scope="col">End</th>
                <th scope="col">Reservation Fee</th>
                <th scope="col">Deposit Fee</th>
                <th scope="col">Price per Day</th>
                <th scope="col">Total Rental Amount</th>
                <th scope="col">Status</th>
                <th scope="col">Created</th>
                <th scope="col">Updated</th>
            </tr>
        </thead>
        <tbody>
            <% if (bookingLoadError != null) { %>
                <tr>
                    <td colspan="16"><%= escapeHtml(bookingLoadError) %></td>
                </tr>
            <% } else if (bookings.isEmpty()) { %>
                <tr>
                    <td colspan="16">No bookings found.</td>
                </tr>
            <% } else {
                for (Booking booking : bookings) { %>
                    <tr>
                        <td><%= escapeHtml(booking.getBookingId()) %></td>
                        <td><%= escapeHtml(booking.getBookingCode()) %></td>
                        <td><%= escapeHtml(booking.getCustomerId()) %></td>
                        <td><%= escapeHtml(booking.getCarId()) %></td>
                        <td><%= escapeHtml(booking.getRentalTypeId()) %></td>
                        <td><%= escapeHtml(booking.getPickupLocationId()) %></td>
                        <td><%= escapeHtml(booking.getReturnLocationId()) %></td>
                        <td><%= escapeHtml(booking.getStartDatetime()) %></td>
                        <td><%= escapeHtml(booking.getEndDatetime()) %></td>
                        <td><%= escapeHtml(booking.getReservationFee()) %></td>
                        <td><%= escapeHtml(booking.getDepositFee()) %></td>
                        <td><%= escapeHtml(booking.getRentalPricePerDay()) %></td>
                        <td><%= escapeHtml(booking.getTotalRentalAmount()) %></td>
                        <td><%= escapeHtml(booking.getBookingStatus()) %></td>
                        <td><%= escapeHtml(booking.getCreateAt()) %></td>
                        <td><%= escapeHtml(booking.getUpdateAt()) %></td>
                    </tr>
                <% }
            } %>
        </tbody>
    </table>
</body>
</html>

package com.crs.controller;

import com.crs.dal.bookingDAO;
import com.crs.model.Booking;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/bookings")
public class BookingListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Booking> bookings = Collections.emptyList();
        String bookingLoadError = null;
        bookingDAO dao = null;

        try {
            dao = new bookingDAO();
            bookings = dao.getAllBookings();
        } catch (IllegalStateException e) {
            getServletContext().log("Unable to display bookings.", e);
            bookingLoadError = e.getMessage();
        } finally {
            if (dao != null) {
                dao.closeConnection();
            }
        }

        request.setAttribute("bookings", bookings);
        request.setAttribute("bookingLoadError", bookingLoadError);
        request.getRequestDispatcher("/view/common/booking.jsp").forward(request, response);
    }
}

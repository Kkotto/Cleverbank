package com.kkotto.cleverbank.controller;

import com.kkotto.cleverbank.config.DatabaseConfiguration;
import com.kkotto.cleverbank.config.TablesConfiguration;
import com.kkotto.cleverbank.exception.DatabaseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "homeController", value = "/home")
public class HomeController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HomeController.class);
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");

        DatabaseConfiguration configuration = DatabaseConfiguration.getInstance();
        try (Connection connection = configuration.getConnection()) {
            logger.info("connected!");
            TablesConfiguration tablesConfiguration = TablesConfiguration.getInstance(connection);
            tablesConfiguration.createTables();
            logger.info("generated!");
            out.println("<h1>" + "Database's created and tables are generated!" + "</h1>");
        } catch (DatabaseException | SQLException e) {
            logger.error("failed: " + e.getMessage());
            out.println("<h1>" + "Everything's failed: " + e.getMessage() + "</h1>");
        } finally {
            out.println("</body></html>");
        }
    }
}

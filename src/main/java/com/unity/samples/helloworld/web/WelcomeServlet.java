package com.unity.samples.helloworld.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int responseCode;
        String responseString;

        try {
            responseString = getWelcomeMessageFromDatabase();
            responseCode = 200;
        } catch (Exception e) {
            e.printStackTrace();
            responseString = "ERR: " + e.getMessage();
            responseCode = 500;
        }

        response.setStatus(responseCode);
        response.setContentType("text/html");
        response.getWriter().write(
                "<html>\n" +
                "<body>\n" +
                "<h2>" + responseString + "</h2>\n" +
                "</body>\n" +
                "</html>");
    }

    private String getWelcomeMessageFromDatabase() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/helloworld");
            con = ds.getConnection();

            // Create a Statement object
            Statement stmt = con.createStatement();

            // Execute an SQL query, get a ResultSet
            ResultSet rs = stmt.executeQuery("SELECT TEXT_MSG FROM WELCOME_MESSAGE");

            return rs.next() ? rs.getString(1) : "No message specified in database";
        } finally {
            try {
                if (con != null) con.close();
            }
            catch (SQLException ignored) { }
        }
    }
}
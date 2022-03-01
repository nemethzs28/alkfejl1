package hu.alkfejl.controller;

import hu.alkfejl.dao.MultiPlayerDAOImpl;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.SinglePlayer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/DeleteToplistServlet")
public class WebDeleteController extends HttpServlet{
    public static SinglePlayerDAOImpl dao = new SinglePlayerDAOImpl();
    public static MultiPlayerDAOImpl dao2 = new MultiPlayerDAOImpl();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameterMap().containsKey("id")) {
            int id = Integer.parseInt(request.getParameterMap().get("id")[0]);
            dao.delete(dao.find(id));
        } else {
            int multiId = Integer.parseInt(request.getParameterMap().get("multiId")[0]);
            dao2.delete(dao2.find(multiId));
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }


}

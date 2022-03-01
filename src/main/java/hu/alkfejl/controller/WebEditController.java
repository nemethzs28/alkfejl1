package hu.alkfejl.controller;


import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hu.alkfejl.dao.MultiPlayerDAOImpl;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.MultiPlayer;
import hu.alkfejl.model.SinglePlayer;

@WebServlet("/EditToplistServlet")
public class WebEditController extends HttpServlet {

    public static SinglePlayerDAOImpl dao = new SinglePlayerDAOImpl();
    public static MultiPlayerDAOImpl dao2 = new MultiPlayerDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParameterMap().containsKey("id")) {
            String paramName = request.getParameterMap().get("name")[0];
            String paramScore = request.getParameterMap().get("score")[0];
            String paramId = request.getParameterMap().get("id")[0];
            String name;
            int score;
            SinglePlayer player = dao.find(Integer.parseInt(paramId));
            if (paramName.equals("")) {
                name = player.getName();
            } else {
                name = paramName;
            }
            try {
                score = Integer.parseInt(paramScore);
            } catch (NumberFormatException e) {
                score = player.getScore();
            }
            dao.save(new SinglePlayer(player.getId(), name, score));
        } else {
            String paramName1 = request.getParameterMap().get("name1")[0];
            String paramScore1 = request.getParameterMap().get("score1")[0];
            String paramScore2 = request.getParameterMap().get("score2")[0];
            String paramName2 = request.getParameterMap().get("name2")[0];
            String paramId = request.getParameterMap().get("multiId")[0];
            String name1;
            int score1;
            int score2;
            String name2;
            MultiPlayer match = dao2.find(Integer.parseInt(paramId));
            if (paramName1.equals("")) {
                name1 = match.getName1();
            } else {
                name1 = paramName1;
            }
            try {
                score1 = Integer.parseInt(paramScore1);
            } catch (NumberFormatException e) {
                score1 = match.getScore1();
            }try {
                score2 = Integer.parseInt(paramScore2);
            } catch (NumberFormatException e) {
                score2 = match.getScore2();
            }
            if (paramName2.equals("")) {
                name2 = match.getName2();
            } else {
                name2 = paramName2;
            }
            dao2.save(new MultiPlayer(match.getId(), name1, score1, score2, name2));
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

}
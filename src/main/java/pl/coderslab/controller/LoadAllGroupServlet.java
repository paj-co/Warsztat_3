package pl.coderslab.controller;

import pl.coderslab.model.UserGroup;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadAllGroupServlet", urlPatterns = {"/viewGroups"})
public class LoadAllGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<UserGroup> userGroups =  UserGroup.loadAll();

        request.setAttribute("userGroup", userGroups);

        getServletContext().getRequestDispatcher("/WEB-INF/usergroup.jsp").forward(request, response);

    }
}

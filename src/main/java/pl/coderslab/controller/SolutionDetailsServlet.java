package pl.coderslab.controller;

import pl.coderslab.model.Solution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SolutionDetailsServlet", urlPatterns = {"/viewSolution"})
public class SolutionDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //no try catch
        Integer solutionId = Integer.parseInt(request.getParameter("id"));

        Solution solution = Solution.loadByID(solutionId);

        request.setAttribute("solution", solution);

        getServletContext().getRequestDispatcher("/WEB-INF/solution.jsp").forward(request, response);
    }
}

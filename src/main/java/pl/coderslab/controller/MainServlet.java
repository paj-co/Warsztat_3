package pl.coderslab.controller;

import pl.coderslab.model.Solution;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = {"/"}/*, initParams = {@WebInitParam(name="number-solutions", value = "5")}*/)
public class MainServlet extends HttpServlet {
    private Integer numberOfSolutions = 5;   //na wszeli wypadek, gdyby nie dostało 5

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Solution> solutions = Solution.loadAll(numberOfSolutions);

        request.setAttribute("solutions", solutions);

        getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

/*    @Override
    public void init(ServletConfig config) throws ServletException {
        this.numberOfSolutions = Integer.parseInt(config.getInitParameter("number-solutions"));
        //jeśli będzie podany parametr inicjalizujący, to pobierze ten paramet a jak nie to będzie 1
    }*/
}

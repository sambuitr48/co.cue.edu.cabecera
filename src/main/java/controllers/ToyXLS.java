package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapping.dto.ToyDTO;
import services.impl.toy.ToyServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet({"/toys.xls", "/toys.html", "/toys"})
public class ToyXLS extends HttpServlet {
    public ToyServiceImpl service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ToyDTO> toy = service.listToys();
        resp.setContentType("text/html;charset=UTF-8");
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment;filename=students.xls");
        }
        try (PrintWriter out = resp.getWriter()) {
            if(!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Listado de juguetes</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Listado de juguetes!</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/toys.xls" + "\">exportar a  xls</a></p>");

            }
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            out.println("<th>precio</th>");
            out.println("</tr>");
            toy.forEach(t ->{
                out.println("<tr>");
                out.println("<td>" + t.toy_name() + "</td>");
                out.println("<td>" + t.toy_category() + "</td>");
                out.println("<td>" + t.toy_price() + "</td>");
                out.println("<td>" + t.toy_stock() + "</td>");
                out.println("</tr>");
            });
            out.println("</table>");
            if(!esXls) {
                out.println(" </body>");
                out.println("</html>");
            }
        }
    }
}


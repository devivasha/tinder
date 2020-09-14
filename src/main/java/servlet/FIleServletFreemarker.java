package servlet;

import utl.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class FileServletFreemarker extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        TemplateEngine engine = TemplateEngine.folder("assets");
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "Jim");
        engine.render("like-page.html", data, resp);

    }
}

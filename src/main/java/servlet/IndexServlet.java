package servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServletOutputStream os = resp.getOutputStream()) {
            URI uri = this.getClass().getClassLoader().getResource("index.html").toURI();
            Path path = Paths.get(uri);
            Files.copy(path, os);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}

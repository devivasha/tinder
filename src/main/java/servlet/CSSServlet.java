package servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSSServlet extends HttpServlet  {
    private final String ASSETS_ROOT = "./assets";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try (ServletOutputStream os = resp.getOutputStream()){
       String rqName = req.getRequestURI();
       Path fileName = Paths.get(ASSETS_ROOT, rqName);
//        System.out.printf("FILE NAME: %S\n", rqName);
//        System.out.println(fileName);
        Files.copy(fileName, os); }
    }
}

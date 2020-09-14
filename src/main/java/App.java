import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.CSSServlet;
import servlet.IndexServlet;
import servlet.RedirectServlet;



public class App {
    public static void main(String[] args) throws Exception {
//        Storage storage = new SQLStorage();
        Server server = new Server(8095);
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(IndexServlet.class, "/users");
        handler.addServlet(CSSServlet.class, "/css/bootstrap.min.css");
        handler.addServlet(CSSServlet.class, "/css/style.css");
        handler.addServlet(RedirectServlet.class, "/*");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

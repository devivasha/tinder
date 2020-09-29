import db.DBConnection;
import filters.LoginFilter;
import filters.LoginStatusFilter;
import filters.RegistrationFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.EmailService;
import servlet.*;
import storage.Storage;
import utl.ResourceHandlerGenerator;
import org.eclipse.jetty.server.Handler;
import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.util.EnumSet;


public class App {
    public static void main(String[] args) throws Exception {
        Connection connection = new DBConnection().connection();
        Storage storage = new Storage();
        EmailService emailService = new EmailService(storage);

        String wPort = System.getenv("PORT");

        if(wPort == null || wPort.isEmpty()) {
            wPort = "8080";
        }

        ServletContextHandler handler = new ServletContextHandler();

        ResourceHandlerGenerator rhg = new ResourceHandlerGenerator();

        ContextHandler jsHandler = rhg.generateResourceHandler("src/main/resources/templates/js", "/js");

        handler.addServlet(new ServletHolder(new MainServlet()),"/");
        handler.addServlet(new ServletHolder(new LikesServlet(connection)), "/liked");
        handler.addServlet(new ServletHolder(new MessagesServlet(connection)), "/message");

        handler.addServlet(new ServletHolder(new LoginServlet(connection)),"/login/*");
        handler.addServlet(new ServletHolder(new RegistrationServlet(emailService)),"/reg/*");
        handler.addServlet(new ServletHolder(new VerificationServlet(connection,storage)), "/verification/*");
        handler.addServlet(new ServletHolder(new UsersServlet(connection)),"/users/*");
        handler.addServlet(new ServletHolder(new LogoutServlet()),"/logout/*");

        handler.addFilter(LoginStatusFilter.class,"/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        HandlerCollection handlerCollection = new HandlerCollection();

        handler.addFilter(new FilterHolder(new RegistrationFilter(connection)),"/reg/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new LoginFilter(connection)),"/login/*", EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        handlerCollection.setHandlers(new Handler[] {jsHandler, handler});

        Server server = new Server(Integer.parseInt(wPort));

        server.setHandler(handlerCollection);

        server.start();
        server.join();

    }
}

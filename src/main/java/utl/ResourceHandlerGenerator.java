package utl;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
public class ResourceHandlerGenerator {
    public ContextHandler generateResourceHandler(String resourceBase, String contextPath) {
        ResourceHandler authRes = new ResourceHandler();
        authRes.setResourceBase(resourceBase);
        authRes.setDirectoriesListed(true);
        ContextHandler authResHandler = new ContextHandler(contextPath);
        authResHandler.setHandler(authRes);
        return authResHandler;
    }
}

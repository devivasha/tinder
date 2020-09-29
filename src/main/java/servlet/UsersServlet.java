package servlet;

import dao.DaoLikesSql;
import dao.DaoUsersSql;
import domain.Like;
import domain.User;
import services.CookiesService;
import services.LikesService;
import utl.Freemarker;
import utl.ParameterFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

public class UsersServlet extends HttpServlet {
    private LikesService likesService;
    private CookiesService cookiesService;
    private final Freemarker f = new Freemarker();
    private final Connection connection;

    public UsersServlet(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookiesService = new CookiesService(req, resp);
        int activeUserId = Integer.parseInt(cookiesService.getCookie().getValue());
        likesService = new LikesService(new DaoLikesSql(activeUserId,connection),new DaoUsersSql(connection));

        User user = likesService.getUserToShow(activeUserId);

        HashMap<String, Object> data = new HashMap<>();

        data.put("user", user);
        f.render("like-page.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(likesService == null){
            cookiesService = new CookiesService(req, resp);
            int activeUserId = Integer.parseInt(cookiesService.getCookie().getValue());
            likesService = new LikesService(new DaoLikesSql(activeUserId,connection),new DaoUsersSql(connection));
        }

        ParameterFromRequest pfr = new ParameterFromRequest(req);
        Like like = new Like(pfr.getInt("userId"));
        if (req.getParameter("action").equals("like")) {
            likesService.addLike(like);
            likesService.addCheckedStatus(pfr.getInt("userId"));
        } else if (req.getParameter("action").equals("dislike")) {
            likesService.removeLike(like);
            likesService.addCheckedStatus(pfr.getInt("userId"));
        }
        doGet(req, resp);
    }
}

package dao;

import domain.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoLikesSql implements Dao<Like> {
    private int userId;
    private Connection connection;


    public DaoLikesSql(int userId, Connection connection) {
        this.userId = userId;
        this.connection = connection;
    }

    public void clearCheckedTable(){
        try {
            String sql = "DELETE FROM tinder_checked WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCheckedStatus(int checkedUserId){
        try {
            String sql = "INSERT INTO tinder_checked(id,checked) VALUES (?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, checkedUserId);
            stm.execute();
        } catch (SQLException e) {
            System.out.println("duplicate entry");
        }
    }

    public void add(Like item) {
        String sql = "INSERT INTO tinder_likes(user_id,liked) VALUES (?,?)";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, item.getLikedUserId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        String sql = "DELETE FROM tinder_likes WHERE user_id = ? AND liked = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Like get(int id) {
        Like like = null;
        String sql = "SELECT * FROM tinder_likes WHERE user_id = ? AND liked = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,userId);
            stm.setInt(2,id);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                int userId = rSet.getInt("user_id");
                int likedUserId = rSet.getInt("liked");
                like = new Like(userId,likedUserId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return like;
    }

    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String sql = "SELECT * FROM tinder_likes WHERE user_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,userId);
            ResultSet rSet = stm.executeQuery();
            while (rSet.next()) {
                int userId = rSet.getInt("user_id");
                int likedUserId = rSet.getInt("liked");
                Like like = new Like(userId,likedUserId);
                likes.add(like);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }
}

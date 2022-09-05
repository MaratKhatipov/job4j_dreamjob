package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {
    private static final Logger LOG_P_DB_STORE = LoggerFactory.getLogger(PostDBStore.class.getName());

    private final BasicDataSource pool;

    private final static String FIND_ALL = """
                                           SELECT  * FROM  post
                                           """;
    private final static String INSERT = """
                                         INSERT INTO post(name, description, created, visible, city_id) 
                                         VALUES (?, ?, ?, ?, ?)
                                         """;
    private static final String FIND_BY_ID = """
                                             SELECT * FROM post WHERE id = ?
                                             """;
    private final static String UPDATE = """
                                         UPDATE post
                                         SET name = ?,  description = ?,  created = ?, visible = ?, city_id = ?  
                                         where id = ?
                                         """;
    private final static String DELETE = """
                                         DELETE FROM post
                                         """;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                            rs.getTimestamp("created").toLocalDateTime(),
                             new CityStorage().findById(rs.getInt("city_id")));
                    post.setVisible(rs.getBoolean("visible"));
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            LOG_P_DB_STORE.error("findAll, SQLException - ", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(INSERT,
                PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            LocalDateTime now = LocalDateTime.now();
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(now));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
        }
    } catch (SQLException e) {
            LOG_P_DB_STORE.error("add, SQLException - ", e);
        }
        return post;
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Post post = new Post(it.getInt("id"), it.getString("name"));
                    post.setDescription(it.getString("description"));
                    post.setCreated(it.getTimestamp("created").toLocalDateTime());
                    post.setVisible(it.getBoolean("visible"));
                    post.setCity(new CityStorage().findById(it.getInt("city_id")));
                    return post;
                }
            }
        } catch (SQLException e) {
            LOG_P_DB_STORE.error("findById, SQLException - ", e);
        }
        return null;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.setInt(6, post.getId());
            ps.execute();
            System.out.println(post);
        } catch (SQLException e) {
            LOG_P_DB_STORE.error("update, SQLException - ", e);
        }
    }

     public void reset() {
         try (Connection cn = pool.getConnection();
              PreparedStatement ps =  cn.prepareStatement(DELETE)
         ) {
             ps.execute();
         } catch (Exception e) {
             LOG_P_DB_STORE.error("Error:", e);
         }
     }

}

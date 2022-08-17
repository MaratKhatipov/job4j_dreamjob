package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {
    private static final Logger LOG_C_DB_STORE = LoggerFactory.getLogger(CandidateDBStore.class.getName());
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement("select * from candidate")
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Candidate candidate = new Candidate(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getTimestamp("created").toLocalDateTime(),
                            rs.getBytes("photo")
                    );
                    candidates.add(candidate);
                }
            }
        } catch (SQLException e) {
            LOG_C_DB_STORE.error("findAll, SQLException", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "insert into candidate(name, description, created, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(1);
                }
            }
        } catch (SQLException e) {
            LOG_C_DB_STORE.error("add, SQLException", e);
        }
        return candidate;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {

                if (it.next()) {
                    Candidate candidate = new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBytes("photo")
                    );
                    return candidate;
                }
            }
        } catch (SQLException e) {
            LOG_C_DB_STORE.error("findById, SQLException", e);
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidate SET "
                             + "name = ?, "
                             + "description = ?, "
                             + "created = ?, "
                             + "photo = ? "
                             + "where id = ?"
             )) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
            System.out.println(candidate);
        } catch (Exception e) {
            LOG_C_DB_STORE.error("update, SQLException", e);
        }
    }
}

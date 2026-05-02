package by.psu.db;

import by.psu.model.Excursion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper {

    // CREATE
    public void create(Excursion excursion) {
        String sql = """
                INSERT INTO excursion (name, price, from_date, to_date, guide_name, excursion_type, lunch_included)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, excursion.getName());
            ps.setBigDecimal(2, excursion.getPrice());
            ps.setDate(3, Date.valueOf(excursion.getFrom()));
            ps.setDate(4, Date.valueOf(excursion.getTo()));
            ps.setString(5, excursion.getGuideName());
            ps.setString(6, excursion.getExcursionType());
            ps.setBoolean(7, excursion.isLunchIncluded());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    excursion.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating excursion", e);
        }
    }

    // READ ALL
    public List<Excursion> getAll() {
        List<Excursion> list = new ArrayList<>();

        String sql = "SELECT * FROM excursion";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading excursions", e);
        }

        return list;
    }

    // FIND BY ID
    public Excursion getById(int id) {
        String sql = "SELECT * FROM excursion WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading excursion", e);
        }

        return null;
    }

    // UPDATE
    public void update(Excursion excursion) {
        String sql = """
            UPDATE excursion
            SET name=?, price=?, from_date=?, to_date=?, guide_name=?, excursion_type=?, lunch_included=?
            WHERE id=?
            """;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, excursion.getName());
            ps.setBigDecimal(2, excursion.getPrice());
            ps.setDate(3, Date.valueOf(excursion.getFrom()));
            ps.setDate(4, Date.valueOf(excursion.getTo()));
            ps.setString(5, excursion.getGuideName());
            ps.setString(6, excursion.getExcursionType());
            ps.setBoolean(7, excursion.isLunchIncluded());
            ps.setInt(8, excursion.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating excursion", e);
        }
    }

    // DELETE
    public void delete(int id) {
        String sql = "DELETE FROM excursion WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting excursion", e);
        }
    }

    // Helper to map ResultSet → Excursion
    private Excursion mapRow(ResultSet rs) throws SQLException {
        return new Excursion(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date").toLocalDate(),
                rs.getString("guide_name"),
                rs.getString("excursion_type"),
                rs.getBoolean("lunch_included")
        );
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Consulta;
import model.Paciente;
import model.Medico;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void save(Consulta c) {
        String sql = "INSERT INTO consulta (paciente_id, medico_id, data_hora, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, c.getPaciente().getId());
            stmt.setInt(2, c.getMedico().getId());
            stmt.setString(3, c.getDataHora().format(fmt));
            stmt.setString(4, c.getDescricao());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Consulta> findAll() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.id, c.data_hora, c.descricao, " +
                     "p.id as pid, p.nome as pnome, " +
                     "m.id as mid, m.nome as mnome " +
                     "FROM consulta c " +
                     "JOIN paciente p ON c.paciente_id = p.id " +
                     "JOIN medico m ON c.medico_id = m.id";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente(rs.getInt("pid"), rs.getString("pnome"));
                Medico m = new Medico(rs.getInt("mid"), rs.getString("mnome"));
                LocalDateTime dh = LocalDateTime.parse(rs.getString("data_hora"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                consultas.add(new Consulta(rs.getInt("id"), p, m, dh, rs.getString("descricao")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }

    public boolean existsByMedicoAndDateTime(int medicoId, LocalDateTime dataHora) {
        String sql = "SELECT COUNT(*) FROM consulta WHERE medico_id = ? AND data_hora = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicoId);
            stmt.setString(2, dataHora.format(fmt));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

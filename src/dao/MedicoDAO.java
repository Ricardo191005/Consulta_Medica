/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Salva um médico no banco
    public void save(Medico m) {
        String sql = "INSERT INTO medico (nome) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, m.getNome());
            stmt.executeUpdate();

            // Atualiza o ID do objeto com o gerado pelo banco
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna todos os médicos do banco
    public List<Medico> findAll() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medico m = new Medico(rs.getInt("id"), rs.getString("nome"));
                medicos.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }
}



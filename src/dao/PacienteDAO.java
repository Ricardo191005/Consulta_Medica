/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Salva um paciente no banco
    public void save(Paciente p) {
        String sql = "INSERT INTO paciente (nome) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNome());
            stmt.executeUpdate();

            // Atualiza o ID do objeto com o gerado pelo banco
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna todos os pacientes do banco
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente(rs.getInt("id"), rs.getString("nome"));
                pacientes.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }
}

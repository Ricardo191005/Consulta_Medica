/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import javax.swing.*;
import dao.*;
import model.*;

public class MainFrame extends JFrame {

    public PacienteDAO pacienteDAO = new PacienteDAO();
    public MedicoDAO medicoDAO = new MedicoDAO();
    public ConsultaDAO consultaDAO = new ConsultaDAO();

    public MainFrame() {
        setTitle("Sistema de Consultas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnConsulta = new JButton("Agendar Consulta");
        btnConsulta.addActionListener(e -> new ConsultaForm(this).setVisible(true));

        JButton btnPaciente = new JButton("Cadastrar Paciente");
        btnPaciente.addActionListener(e -> new PacienteForm(this).setVisible(true));

        JButton btnMedico = new JButton("Cadastrar Médico");
        btnMedico.addActionListener(e -> new MedicoForm(this).setVisible(true));

        JPanel panel = new JPanel();
        panel.add(btnConsulta);
        panel.add(btnPaciente);
        panel.add(btnMedico);

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import dao.ConsultaDAO;
import dao.PacienteDAO;
import dao.MedicoDAO;
import model.Consulta;
import model.Paciente;
import model.Medico;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaForm extends JDialog {
    private JComboBox<Paciente> cbPaciente;
    private JComboBox<Medico> cbMedico;
    private JTextField tfDataHora, tfDescricao;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();

    public ConsultaForm(JFrame owner) {
        super(owner, "Agendamento de Consulta", true);
        setSize(600, 340);
        setLocationRelativeTo(owner);
        initComponents();
    }

    private void initComponents() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;

        cbPaciente = new JComboBox<>();
        List<Paciente> pacientes = pacienteDAO.findAll();
        for (Paciente pa : pacientes) cbPaciente.addItem(pa);

        cbMedico = new JComboBox<>();
        List<Medico> medicos = medicoDAO.findAll();
        for (Medico me : medicos) cbMedico.addItem(me);

        tfDataHora = new JTextField();
        tfDescricao = new JTextField();

        c.gridx=0; c.gridy=0; p.add(new JLabel("Paciente:"),c);
        c.gridx=1; c.gridy=0; p.add(cbPaciente,c);
        c.gridx=0; c.gridy=1; p.add(new JLabel("Médico:"),c);
        c.gridx=1; c.gridy=1; p.add(cbMedico,c);
        c.gridx=0; c.gridy=2; p.add(new JLabel("Data e hora (yyyy-MM-dd HH:mm):"),c);
        c.gridx=1; c.gridy=2; p.add(tfDataHora,c);
        c.gridx=0; c.gridy=3; p.add(new JLabel("Descrição:"),c);
        c.gridx=1; c.gridy=3; p.add(tfDescricao,c);

        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(e -> onAgendar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnAgendar);
        bottom.add(btnCancelar);

        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void onAgendar() {
        Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
        Medico medico = (Medico) cbMedico.getSelectedItem();
        String dh = tfDataHora.getText().trim();
        String desc = tfDescricao.getText().trim();

        if (paciente == null || medico == null || dh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha paciente, médico e data/hora.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime dataHora;
        try {
            dataHora = LocalDateTime.parse(dh, fmt);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Formato de data/hora inválido. Use yyyy-MM-dd HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica conflito no banco
        if (consultaDAO.existsByMedicoAndDateTime(medico.getId(), dataHora)) {
            JOptionPane.showMessageDialog(this, "Já existe uma consulta para este médico nessa data/hora.", "Conflito", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Consulta c = new Consulta(0, paciente, medico, dataHora, desc);
        consultaDAO.save(c);
        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
        dispose();
    }
}

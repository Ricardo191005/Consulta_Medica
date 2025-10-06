/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import dao.MedicoDAO;
import model.Medico;
import javax.swing.*;
import java.awt.*;

public class MedicoForm extends JDialog {
    private JTextField tfNome;
    private final MedicoDAO medicoDAO = new MedicoDAO();

    public MedicoForm(JFrame owner) {
        super(owner, "Cadastro de Médico", true);
        setSize(400, 200);
        setLocationRelativeTo(owner);
        initComponents();
    }

    private void initComponents() {
        JPanel p = new JPanel(new GridLayout(2,2,10,10));
        tfNome = new JTextField();
        p.add(new JLabel("Nome:"));
        p.add(tfNome);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> onSalvar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel bottom = new JPanel();
        bottom.add(btnSalvar);
        bottom.add(btnCancelar);

        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void onSalvar() {
        String nome = tfNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Medico m = new Medico(0, nome);
        medicoDAO.save(m); // grava no MySQL
        JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!");
        dispose();
    }
}

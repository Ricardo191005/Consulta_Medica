/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String descricao;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora, String descricao) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {  // ← necessário
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getDescricao() {
        return descricao;
    }
}


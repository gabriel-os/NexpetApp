package com.nexbird.nexpet.adapter;

import java.util.HashMap;

/**
 * Created by Gabriel on 04/07/2016.
 */
public class Agendados {

    private String id;
    private String nomePetshop;
    private String nomeAnimal;
    private String dataAgendada;
    private String servico;
    private String precoFinal;
    private HashMap resultado;


    public Agendados(String id, String dataAgendada, String nomePetshop, String nomeAnimal, String servico, String precoFinal) {
        this.id = id;
        this.nomePetshop = nomePetshop;
        this.nomeAnimal = nomeAnimal;
        this.dataAgendada = dataAgendada;
        this.servico = servico;
        this.precoFinal = precoFinal;
    }

    public String getIdAgendado() {
        return id;
    }

    public void setIdAgendado(String id) {
        this.id = id;
    }

    public String getNomePet() {
        return nomeAnimal;
    }

    public void setNomePet(String nomePet) {
        this.nomeAnimal = nomePet;
    }

    public String getNomePetshop() {
        return nomePetshop;
    }

    public void setNomePetshop(String nomePetshop) {
        this.nomePetshop = nomePetshop;
    }

    public String getDataMarcada() {
        return dataAgendada;
    }

    public void setDataMarcada(String dataMarcada) {
        this.dataAgendada = dataMarcada;
    }

    public HashMap getHashMap() {
        return resultado;
    }

    public void setHashMap(HashMap resultado) {
        this.resultado = resultado;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public void setPrecoFinal(String precoFinal) {
        this.precoFinal = precoFinal;
    }

    public String getServico() {
        return servico;
    }

    public String getPrecoFinal() {
        return precoFinal;
    }
}

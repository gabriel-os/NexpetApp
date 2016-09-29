package com.nexbird.nexpet.adapter;

import java.util.HashMap;

/**
 * Created by Gabriel on 04/07/2016.
 */
public class Agendados {

    private String id;
    private String unique_index;
    private String nomePetshop;
    private String endereco;
    private String nomeAnimal;
    private String dataAgendada;
    private String servico;
    private String precoFinal;
    private String confirmado;
    private HashMap resultado;


    public Agendados(String id, String unique_index, String dataAgendada, String nomePetshop, String endereco, String nomeAnimal, String servico, String precoFinal, String confirmado) {
        this.id = id;
        this.unique_index = unique_index;
        this.nomePetshop = nomePetshop;
        this.endereco = endereco;
        this.nomeAnimal = nomeAnimal;
        this.dataAgendada = dataAgendada;
        this.servico = servico;
        this.precoFinal = precoFinal;
        this.confirmado = confirmado;
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

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(String precoFinal) {
        this.precoFinal = precoFinal;
    }

    public String getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(String confirmado) {
        this.confirmado = confirmado;
    }

    public String getUnique_index() {
        return unique_index;
    }

    public void setUnique_index(String unique_index) {
        this.unique_index = unique_index;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

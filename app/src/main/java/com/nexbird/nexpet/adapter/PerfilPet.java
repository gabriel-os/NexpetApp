package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */
public class PerfilPet {

    private String id;
    private String txtServico;
    private String txtPequeno;
    private String txtMedio;
    private String txtGrande;
    private String txtGigante;
    private String txtGato;
    private String duracaoCao;
    private String duracaoGato;
    private String descricao;


    public PerfilPet(String id, String txtServico, String txtPequeno, String txtMedio, String txtGrande, String txtGigante, String txtGato, String duracaoCao, String duracaoGato, String descricao) {
        this.id = id;
        this.txtServico = txtServico;
        this.txtPequeno = txtPequeno;
        this.txtMedio = txtMedio;
        this.txtGrande = txtGrande;
        this.txtGigante = txtGigante;
        this.txtGato = txtGato;
        this.duracaoCao = duracaoCao;
        this.duracaoGato = duracaoGato;
        this.descricao = descricao;
    }

    public String getTxtServico() {
        return txtServico;
    }

    public void setTxtServico(String txtServico) {
        this.txtServico = txtServico;
    }

    public String getTxtPequeno() {
        return txtPequeno;
    }

    public void setTxtPequeno(String txtPequeno) {
        this.txtPequeno = txtPequeno;
    }

    public String getTxtMedio() {
        return txtMedio;
    }

    public void setTxtMedio(String txtMedio) {
        this.txtMedio = txtMedio;
    }

    public String getTxtGrande() {
        return txtGrande;
    }

    public void setTxtGrande(String txtGrande) {
        this.txtGrande = txtGrande;
    }

    public String getTxtGigante() {
        return txtGigante;
    }

    public void setTxtGigante(String txtGigante) {
        this.txtGigante = txtGigante;
    }

    public String getTxtGato() {
        return txtGato;
    }

    public void setTxtGato(String txtGato) {
        this.txtGato = txtGato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuracaoCao() {
        return duracaoCao;
    }

    public void setDuracaoCao(String duracaoCao) {
        this.duracaoCao = duracaoCao;
    }

    public String getDuracaoGato() {
        return duracaoGato;
    }

    public void setDuracaoGato(String duracaoGato) {
        this.duracaoGato = duracaoGato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */
public class PerfilPet {

    private String txtServico;
    private String txtPequeno;
    private String txtMedio;
    private String txtGrande;
    private String txtGigante;
    private String txtGato;


    public PerfilPet(String txtServico, String txtPequeno, String txtMedio, String txtGrande, String txtGigante, String txtGato) {
        this.txtServico = txtServico;
        this.txtPequeno = txtPequeno;
        this.txtMedio = txtMedio;
        this.txtGrande = txtGrande;
        this.txtGigante = txtGigante;
        this.txtGato = txtGato;
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
}

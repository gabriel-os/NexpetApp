package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 12/10/2016.
 */
public class ServicoAdicional {

    private String nomeServico;
    private String preco;
    private String descricaoServico;
    private boolean isSelected;


    public ServicoAdicional(String nomeServico, String preco, String descricaoServico, boolean isSelected) {
        this.nomeServico = nomeServico;
        this.preco = preco;
        this.descricaoServico = descricaoServico;
        this.isSelected = isSelected;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
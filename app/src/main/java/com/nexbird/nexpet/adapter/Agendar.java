package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */
public class Agendar {

    private String unique_index;
    private String nomePetshop;
    private String logoPetshop;
    private String enderecoPetshop;
    private String responsavelPetshop;
    private String telefonePetshop;
    private String horaFunc;
    private String descricaoPetshop;
    private String email;


    public Agendar(String unique_index, String nomePetshop, String email, String enderecoPetshop, String responsavelPetshop, String telefonePetshop, String horaFunc, String descricaoServico, String logoPetshop) {
        this.unique_index = unique_index;
        this.email = email;
        this.nomePetshop = nomePetshop;
        this.logoPetshop = logoPetshop;
        this.enderecoPetshop = enderecoPetshop;
        this.responsavelPetshop = responsavelPetshop;
        this.telefonePetshop = telefonePetshop;
        this.horaFunc = horaFunc;
    }

    public String getIdPetshop() {
        return unique_index;
    }

    public void setIdPetshop(String id) {
        this.unique_index = id;
    }

    public String getNomePetshop() {
        return nomePetshop;
    }

    public void setNomePet(String nomePetshop) {
        this.nomePetshop = nomePetshop;
    }

    public String getLogoPetshop() {
        return logoPetshop;
    }

    public void setLogoPetshop(String logoPetshop) {
        this.logoPetshop = logoPetshop;
    }

    public String getEnderecoPetshop() {
        return enderecoPetshop;
    }

    public void setEnderecoPetshop(String enderecoPetshop) {
        this.enderecoPetshop = enderecoPetshop;
    }

    public String getResponsavelPetshop() {
        return responsavelPetshop;
    }

    public void setResponsavelPetshop(String responsavelPetshop) {
        this.responsavelPetshop = responsavelPetshop;
    }

    public String getTelefonePetshop() {
        return telefonePetshop;
    }

    public void setTelefonePetshop(String telefonePetshop) {
        this.telefonePetshop = telefonePetshop;
    }

    public String getDescricaoPetshop() {
        return descricaoPetshop;
    }

    public void setDescricaoPetshop(String descricaoPetshop) {
        this.descricaoPetshop = descricaoPetshop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoraFunc() {
        return horaFunc;
    }

    public void setHoraFunc(String horaFunc) {
        this.horaFunc = horaFunc;
    }
}

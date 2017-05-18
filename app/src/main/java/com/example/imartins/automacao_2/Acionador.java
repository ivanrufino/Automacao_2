package com.example.imartins.automacao_2;

/**
 * Created by imartins on 10/05/17.
 */

public class Acionador {
    private int id;
    private String nome;
    private String alias;
    private Boolean rele_on;

    public Acionador(){}
    public Acionador(int id, String nome, String alias){
        setId(id);
        setNome(nome);
        setAlias(alias);

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getRele_on() {
        return rele_on;
    }

    public void setRele_on(boolean rele_on) {
        this.rele_on = rele_on;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String toString() {
        return nome;
    }
}

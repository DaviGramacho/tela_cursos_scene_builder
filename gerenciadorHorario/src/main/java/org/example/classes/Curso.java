package org.example.classes;

import javafx.beans.property.*;

public class Curso {
    private final BooleanProperty selecionado = new SimpleBooleanProperty(false);
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty periodo = new SimpleStringProperty();

    public Curso(boolean selecionado, int id, String nome, String periodo) {
        this.selecionado.set(selecionado);
        this.id.set(id);
        this.nome.set(nome);
        this.periodo.set(periodo);
    }

    // Getters e Setters
    public BooleanProperty selecionadoProperty() {
        return selecionado;
    }

    public boolean isSelecionado() {
        return selecionado.get();
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado.set(selecionado);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty periodoProperty() {
        return periodo;
    }

    public String getPeriodo() {
        return periodo.get();
    }

    public void setPeriodo(String periodo) {
        this.periodo.set(periodo);
    }
}
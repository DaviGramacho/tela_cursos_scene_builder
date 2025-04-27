package org.example.classes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Curso {
    private final BooleanProperty selecionado;
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty periodo;

    public Curso(boolean selecionado, int id, String nome, String periodo) {
        this.selecionado = new SimpleBooleanProperty(selecionado);
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.periodo = new SimpleStringProperty(periodo);
    }

    public boolean isSelecionado() {
        return selecionado.get();
    }

    public BooleanProperty selecionadoProperty() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado.set(selecionado);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getPeriodo() {
        return periodo.get();
    }

    public StringProperty periodoProperty() {
        return periodo;
    }
}
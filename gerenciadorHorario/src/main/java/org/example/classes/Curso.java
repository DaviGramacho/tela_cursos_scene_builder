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
    private final StringProperty coordenador;

    public Curso(boolean selecionado, int id, String nome, String coordenador) {
        this.selecionado = new SimpleBooleanProperty(selecionado);
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.coordenador = new SimpleStringProperty(coordenador);
    }

    // Construtor alternativo (sem selecionado, para DAO)
    public Curso(String nome, String coordenador) {
        this(false, 0, nome, coordenador);
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

    public void setId(long id) {
        this.id.set((int) id);
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

    public String getCoordenador() {
        return coordenador.get();
    }

    public StringProperty coordenadorProperty() {
        return coordenador;
    }
}

package org.example.classes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Representa um Curso com propriedades JavaFX para binding em TableView.
 */
public class Curso {
    private final BooleanProperty selecionado;
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty coordenador;

    // Construtor completo (usado no listagem do banco)
    public Curso(boolean selecionado, long id, String nome, String coordenador) {
        this.selecionado   = new SimpleBooleanProperty(selecionado);
        this.id            = new SimpleIntegerProperty((int) id);
        this.nome          = new SimpleStringProperty(nome);
        this.coordenador   = new SimpleStringProperty(coordenador);
    }

    // Construtor para novo curso (antes de persistir)
    public Curso(String nome, String coordenador) {
        this(false, 0L, nome, coordenador);
    }

    // --- selecionado ---
    public boolean isSelecionado() {
        return selecionado.get();
    }
    public void setSelecionado(boolean sel) {
        selecionado.set(sel);
    }
    public BooleanProperty selecionadoProperty() {
        return selecionado;
    }

    // --- id ---
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public IntegerProperty idProperty() {
        return id;
    }

    // --- nome ---
    public String getNome() {
        return nome.get();
    }
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    public StringProperty nomeProperty() {
        return nome;
    }

    // --- coordenador ---
    public String getCoordenador() {
        return coordenador.get();
    }
    public void setCoordenador(String coordenador) {
        this.coordenador.set(coordenador);
    }
    public StringProperty coordenadorProperty() {
        return coordenador;
    }
}

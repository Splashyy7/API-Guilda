package br.infnet.tp1guilda.domain.aventura;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.infnet.tp1guilda.enums.Classe;


@Getter
@ToString
public class Aventureiro {
    @Setter
    private Long id;
    @NotBlank(message = "Tem que haver um nome")
    private String nome;
    @NotNull(message = "Tem que haver uma classe")
    private Classe classe;
    @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
    private int nivel;
    @NotNull
    private Boolean ativo;
    @Valid
    private Companheiro companheiro;


    public Aventureiro(String nome, Classe classe, int nivel) {
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
    }

    public void alterarNome(String nome) {
        this.nome = nome;
    }

    public void alterarClasse(Classe classe) {
        this.classe = classe;
    }

    public void alterarNivel(int nivel) {
        this.nivel = nivel;
    }

    public void encerrarVinculo() {
        this.ativo = false;
    }

    public void recrutar() {
        this.ativo = true;
    }

    public void definirCompanheiro(Companheiro companheiro) {
        this.companheiro = companheiro;
    }

    public void removerCompanheiro() {
        this.companheiro = null;
    }

}
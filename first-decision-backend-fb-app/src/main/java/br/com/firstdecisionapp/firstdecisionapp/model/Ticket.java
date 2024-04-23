package br.com.firstdecisionapp.firstdecisionapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String departamento;
    @Column
    private String tituloProblema;
    @Column
    private String descricaoProblema;
    @Column
    private String categoriaProblema;
    @Column
    private String prioridade;
    @Column
    private String statusAndamento;
    @Column
    private String dataAbertura;
    @Column
    private String dataUltimaAtualizacao;
    @Column
    private String feedback;


}

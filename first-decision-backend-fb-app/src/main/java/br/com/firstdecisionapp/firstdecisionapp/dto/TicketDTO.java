package br.com.firstdecisionapp.firstdecisionapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TicketDTO {

    private Long id;
    private String nome;
    private String email;
    private String departamento;
    private String tituloProblema;
    private String descricaoProblema;
    private String categoriaProblema;
    private String prioridade;
    private String statusAndamento;
    private String dataAbertura;
    private String dataUltimaAtualizacao;
    private String feedback;

}

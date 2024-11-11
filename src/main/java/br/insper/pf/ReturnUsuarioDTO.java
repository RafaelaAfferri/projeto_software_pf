package br.insper.pf;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnUsuarioDTO {
    private String nome;
    private String cpf;
    private String email;
    private String password;
    private String papel;
}

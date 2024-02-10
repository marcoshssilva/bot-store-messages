package br.com.marcoshssilva.botstoremessages.rabbit.models;

@lombok.Getter
@lombok.Setter
@lombok.ToString
public class RegisterContactMessageModel {
    private String name;
    private String email;
    private String message;
}

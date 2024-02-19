package br.com.marcoshssilva.botstoremessages.rabbit.models;

@lombok.Getter
@lombok.Setter
public class RegisterContactMessageModel {
    private String name;
    private String mail;
    private String message;

    @Override
    public String toString() {
        return "RegisterContactMessageModel(" + "name=" + name + ", mail=" + mail + ", message=" + message + ')';
    }
}

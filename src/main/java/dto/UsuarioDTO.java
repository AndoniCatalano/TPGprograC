package dto;

public class UsuarioDTO {
    private String username;
    private String password;

    public UsuarioDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

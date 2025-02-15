package desafio.backend.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String email;
    private String senha;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("phone")  
    private String telefone;

    private String website;

    @JsonProperty("address")
    private Endereco endereco;
    
    @JsonProperty("company")
    private Empresa empresa;

    public UserDTO() {
        
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.senha = user.getSenha();
        this.nome = user.getNome();
        this.telefone = user.getTelefone();
        this.website = user.getWebsite();
        this.endereco = user.getEndereco();
        this.empresa = user.getEmpresa();
    }
    

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", website='" + website + '\'' +
                ", endereco=" + endereco +
                ", empresa=" + empresa +
                '}';
    }

}



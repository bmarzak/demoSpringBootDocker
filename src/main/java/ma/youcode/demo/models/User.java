package ma.youcode.demo.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 717272621105789711L;

	@Id
	@GeneratedValue
	@Column(name = "id_user")
	private Long idUser;
	private String userId;

	@NotNull
	@Size(min = 3)
	private String nom;

	@NotNull
	@Size(min = 3)
	private String prenom;

	@Column(unique = true)
	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 8, max = 14)
	@Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?=.*[A-Z])(?=.*[a-z]).*$")
	private String password;
	private String encryptedPassword;
	private String emailVerificationToken;
	private Boolean emailVerificationStatus = false;

	// bi-directional many-to-many association to Profil
	@ManyToMany
    @JoinTable( name = "T_Users_Roles_Associations",
                joinColumns = @JoinColumn( name = "idUser", referencedColumnName = "id_user"),
                inverseJoinColumns = @JoinColumn( name = "idRole", referencedColumnName = "id_role" ) )
    private List<Role> roles;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String nom, String prenom, String email, String encryptedPassword, String emailVerificationToken,
			Boolean emailVerificationStatus) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.emailVerificationToken = emailVerificationToken;
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public Long getId() {
		return idUser;
	}

	public void setId(Long id) {
		this.idUser = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}

package ma.youcode.demo.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_role")
	private Long idRole;

	private String role;

	// bi-directional many-to-many association to Utilisateur
	@ManyToMany 
	@JoinTable(name="T_Users_Roles_Associations",joinColumns=
	@JoinColumn(name = "idRole", referencedColumnName = "id_role" ),
                inverseJoinColumns = @JoinColumn(name = "idUser", referencedColumnName = "id_user") )
    private List<User> users;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String role, List<User> users) {
		super();
		this.role = role;
		this.users = users;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}

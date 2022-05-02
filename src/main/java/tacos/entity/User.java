package tacos.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	private long id;
	private String username;
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<TacoOrder> orders;
}

package tacos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	private long id;
	private String username;
	private String password;
}

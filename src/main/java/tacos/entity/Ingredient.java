package tacos.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ingredient")
public class Ingredient {
	@Id
	private String id;

	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "type", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToMany
	private List<Taco> tacos;

	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}

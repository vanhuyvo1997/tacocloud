package tacos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Taco {

	@Id
	private long id;

	private String name;

	private Date createdAt;

	@ManyToMany(targetEntity = Ingredient.class)
	private List<Ingredient> ingredients;
}

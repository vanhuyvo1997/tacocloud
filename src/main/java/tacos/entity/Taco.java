package tacos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "taco")
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", length = 32, nullable = false)
	private String name;

	@Column(name = "created_at")
	private Date createdAt;

	@ManyToMany(mappedBy = "tacos")
	private List<Ingredient> ingredients;
	
	@ManyToOne(targetEntity = TacoOrder.class)
	private List<TacoOrder> orders;
}

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taco")
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 32, nullable = false)
	@NotNull
	@Length(min = 3, max = 32, message = "Name is between 3 and 32")
	private String name;

	@Column(name = "created_at")
	private Date createdAt;

	@ManyToMany(targetEntity = Ingredient.class)
	@NotEmpty(message = "Ingredient must be chosen")
	private List<Ingredient> ingredients;
	
	@JsonIgnore
	@ManyToOne(targetEntity = TacoOrder.class)
	private TacoOrder order;

	@PrePersist
	private void createdAt() {
		createdAt = new Date();
	}
}

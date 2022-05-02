package tacos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class TacoOrder {
	@Id
	private long id;
	private String deliveryName;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryState;
	private String deliveryZip;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
	private Date placedAt;
	@OneToMany(targetEntity = Taco.class)
	private List<Taco> tacos;

	@ManyToOne(targetEntity = User.class)
	private User user;
}

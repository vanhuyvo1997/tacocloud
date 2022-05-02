package tacos.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.Data;
import tacos.form.OrderForm;

@Data
@Entity
public class TacoOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@OneToMany(mappedBy = "order", targetEntity = Taco.class, cascade = CascadeType.ALL)
	private List<Taco> tacos = new ArrayList<>();

	@ManyToOne(targetEntity = User.class)
	private User user;

	public void addTaco(Taco taco) {
		tacos.add(taco);
	}

	public void read(OrderForm orderForm) {
		this.deliveryName = orderForm.getDeliveryName();
		this.deliveryStreet = orderForm.getDeliveryStreet();
		this.deliveryCity = orderForm.getDeliveryCity();
		this.deliveryState = orderForm.getDeliveryState();
		this.deliveryZip = orderForm.getDeliveryZip();
		this.ccNumber = orderForm.getCcNumber();
		this.ccExpiration = orderForm.getCcExpiration();
		this.ccCVV = orderForm.getCcCVV();
	}

	@PrePersist
	private void placedAt() {
		this.placedAt = new Date();
	}
}

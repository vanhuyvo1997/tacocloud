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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tacos.form.OrderForm;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TacoOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "This field is required")
	private String deliveryName;

	@NotBlank(message = "This field is required")
	private String deliveryStreet;

	@NotBlank(message = "This field is required")
	private String deliveryCity;

	@NotBlank(message = "This field is required")
	private String deliveryState;

	@NotBlank(message = "This field is required")
	private String deliveryZip;

	@CreditCardNumber(message = "Incorrect credit number")
	private String ccNumber;

	@NotNull
	@Pattern(regexp = "\\b(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])\\b", message = "must be MM/DD formate")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 3, message = "Invalid CVV")
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

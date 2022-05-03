package tacos.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class OrderForm {
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

	@Pattern(regexp = "\\b(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])\\b", message = "must be MM/DD formate")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 3, message = "Invalid CVV")
	private String ccCVV;

}

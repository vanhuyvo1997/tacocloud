package tacos.form;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import tacos.entity.User;

@Data
public class RegisterForm {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String confirmPassword;

	@NotBlank
	private String fullname;

	@NotBlank
	private String street;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private String zip;

	@NotBlank
	private String phoneNumber;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(null,username, passwordEncoder.encode(password),fullname,street,city,state,zip,phoneNumber,null);
	}
}

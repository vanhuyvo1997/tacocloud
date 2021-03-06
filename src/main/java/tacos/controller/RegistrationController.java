package tacos.controller;

import java.security.Principal;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.form.RegisterForm;
import tacos.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public String getRegisterForm(Model model, Principal principal) {
		if (Objects.nonNull(principal)) {
			return "redirect:/";
		}

		model.addAttribute("registerForm", new RegisterForm());
		return "register";
	}

	@PostMapping
	public String processRegister(@Valid RegisterForm registerForm, Errors errors, Model model) {
		boolean isMatching = registerForm.getPassword().equals(registerForm.getConfirmPassword());
		if (!isMatching) {
			errors.rejectValue("confirmPassword", "confirmPassword", "Confirm password not match");
		}

		var user = userRepo.findByUsername(registerForm.getUsername());
		if (Objects.nonNull(user)) {
			errors.rejectValue("username", "username", "User name is already in use");
		}
		
		if (errors.hasErrors()) {
			model.addAttribute("registerForm", registerForm);
			return "register";
		}
		;
		userRepo.save(registerForm.toUser(passwordEncoder));
		return "redirect:/tacologin";
	}
}

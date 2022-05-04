package tacos.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tacologin")
public class LoginController {
	@GetMapping
	public String getLoginPage(Principal principal) {
		if(Objects.nonNull(principal)) {
			return "redirect:/";
		}
		return "tacologin";
	}
}

package tacos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.entity.TacoOrder;
import tacos.entity.User;
import tacos.form.OrderForm;
import tacos.props.OrderProps;
import tacos.repository.OrderRepository;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {
	
	private final OrderRepository orderRepo;
	private final OrderProps orderProps;

	@Autowired
	public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
		this.orderRepo = orderRepo;
		this.orderProps = orderProps;
	}

	@GetMapping
	public String getOrder(Model model, Authentication authentication) {
		var orderForm = new OrderForm();
		orderForm.read((User) authentication.getPrincipal());
		model.addAttribute("orderForm", orderForm);
		return "order";
	}

	@PostMapping
	public String proccessOrder(@SessionAttribute TacoOrder order, @Valid OrderForm orderForm, Errors errors,
			Model model, SessionStatus sessionStatus) {
		order.read(orderForm);
		if (errors.hasErrors()) {
			model.addAttribute("orderForm", orderForm);
			return "order";
		}

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		order.setUser(user);
		this.orderRepo.save(order);
		// if remove cascade
		// for(var t: order.getTacos()) {
		// tacoRepo.save(t);
		// }
		sessionStatus.setComplete();
		return "redirect:/";
	}

	@GetMapping("/user")
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
		Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
		return "orderList";
	}
}

package tacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.entity.TacoOrder;
import tacos.form.OrderForm;
import tacos.repository.OrderRepository;
import tacos.repository.TacoRepository;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {
	
	private final OrderRepository orderRepo;
	private final TacoRepository tacoRepo;

	@Autowired
	public OrderController(OrderRepository orderRepo, TacoRepository tacoRepo) {
		this.orderRepo = orderRepo;
		this.tacoRepo = tacoRepo;
	}
	@GetMapping
	public String getOrder(Model model) {
		var orderForm  = new OrderForm();
		model.addAttribute("orderForm", orderForm);
		return "order";
	}
	
	@PostMapping
	public String proccessOrder(@SessionAttribute TacoOrder order, OrderForm orderForm) {
		order.read(orderForm);
		this.orderRepo.save(order);
		for(var t: order.getTacos()) {
			t.setOrder(order);
			tacoRepo.save(t);
		}
		return "home";
	}
}

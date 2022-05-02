package tacos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.entity.Ingredient;
import tacos.entity.Ingredient.Type;
import tacos.entity.Taco;
import tacos.entity.TacoOrder;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {

	private final IngredientRepository ingredientRepo;
	private final TacoRepository tacoRepo;

	@Autowired
	public DesignController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}

	private void loadIngredientToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(e -> ingredients.add(e));
		for (Type type : Type.values()) {
			model.addAttribute(type.name().toLowerCase(), filterIngredientByType(type, ingredients));
		}
	}

	private List<Ingredient> filterIngredientByType(Type type, List<Ingredient> ingredients) {
		return ingredients.stream().filter(e -> e.getType().equals(type)).collect(Collectors.toList());
	}

	@ModelAttribute("taco")
	private Taco taco() {
		return new Taco();
	}
	
	@ModelAttribute("order")
	private TacoOrder order() {
		return new TacoOrder();
	}

	@GetMapping
	public String getDesign(Model model) {
		loadIngredientToModel(model);
		return "design";
	}
	
	@PostMapping
	public String processDesign(Taco taco, @SessionAttribute TacoOrder order) {
		order.addTaco(taco);
		return "redirect:/order";
	}
}

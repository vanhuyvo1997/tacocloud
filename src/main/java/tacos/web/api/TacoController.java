package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.entity.Taco;
import tacos.repository.TacoRepository;

@RestController
@RequestMapping(path = "/api/tacos", produces = { "application/json" })
public class TacoController {

	private final TacoRepository tacoRepo;

	@Autowired
	public TacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@GetMapping(params = "recent")
	public Iterable<Taco> recentTaco() {
		PageRequest page = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
		return tacoRepo.findAll(page).getContent();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Taco> getTacoById(@PathVariable Long id) {
		var taco = tacoRepo.findById(id);
		if (taco.isPresent()) {
			return new ResponseEntity<>(taco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Taco createTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}

	@PutMapping(path = "/{id}", consumes = "application/json")
	public Taco updateTaco(@PathVariable Long id, @RequestBody Taco taco) {
		taco.setId(id);
		return tacoRepo.save(taco);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Taco updatePartialTaco(@PathVariable Long id, @RequestBody Taco tacoPatch) {
		var taco = tacoRepo.findById(id).get();
		if (tacoPatch.getName() != null) {
			taco.setName(tacoPatch.getName());
		}
		if (tacoPatch.getIngredients() != null) {
			taco.setIngredients(tacoPatch.getIngredients());
		}
		if (tacoPatch.getCreatedAt() != null) {
			tacoPatch.setCreatedAt(tacoPatch.getCreatedAt());
		}
		return tacoRepo.save(taco);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Taco> deleteTaco(@PathVariable Long id) {
		try {
			tacoRepo.deleteById(id);
			return new ResponseEntity<Taco>(HttpStatus.NO_CONTENT);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<Taco>(HttpStatus.NOT_FOUND);
		}

	}

}

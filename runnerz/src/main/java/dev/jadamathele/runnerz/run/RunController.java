package dev.jadamathele.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {
	private final RunRepository runRepository;

	public RunController (RunRepository runRepository) {
		this.runRepository = runRepository;
	}

	public List<Run> findAll() {
		return runRepository.findAll();
	}

	@GetMapping("/{id}")
	public Run findById(@PathVariable Integer id) {
		Optional<Run> run = runRepository.findById(id);
		if(run.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found");
		}
		return run.get();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@Valid @RequestBody Run run) {
		runRepository.create(run);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
		runRepository.update(run, id);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		runRepository.delete(id);
	}

	public List<Run> findByLocation(@RequestParam String location) {
		return runRepository.findByLocation(location);
	}
}

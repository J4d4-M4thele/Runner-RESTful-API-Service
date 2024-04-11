package dev.jadamathele.runnerz.run;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

	@GetMapping("/hello")
	public String home() {
		return "Hello Runnerz";
	}
}

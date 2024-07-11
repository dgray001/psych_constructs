package fyi.lnz.psych_constructs.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fyi.lnz.psych_constructs.model.TestModel;

@RestController
public class TestController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/test")
	public TestModel greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new TestModel(counter.incrementAndGet(), String.format(template, name));
	}
}

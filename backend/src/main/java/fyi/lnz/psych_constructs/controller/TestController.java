package fyi.lnz.psych_constructs.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proto.Person;

@ApiController
public class TestController {
	private static final String template = "Hello, %s!";
	private final AtomicInteger counter = new AtomicInteger();

	@GetMapping("/test")
	public byte[] greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return Person.newBuilder().setName(String.format(template, name)).setId(counter.incrementAndGet()).build().toByteArray();
	}
}

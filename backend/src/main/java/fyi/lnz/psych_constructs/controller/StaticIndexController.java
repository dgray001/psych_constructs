package fyi.lnz.psych_constructs.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticIndexController {
  @GetMapping("/")
  public Resource homePage() {
    return new ClassPathResource("static/index.html");
  }
}

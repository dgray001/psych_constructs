package fyi.lnz.psych_constructs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fyi.lnz.psych_constructs.util.Constants;

@SpringBootApplication
public class PsychConstructsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsychConstructsApplication.class, args);
    System.out.println("Launched in environment: %s".formatted(Constants.getEnvironment()));
	}

}

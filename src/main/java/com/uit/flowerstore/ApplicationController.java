package com.uit.flowerstore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ApplicationController {
	
	@GetMapping("/index")
	public String goHome() {
		return "index";
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}

}

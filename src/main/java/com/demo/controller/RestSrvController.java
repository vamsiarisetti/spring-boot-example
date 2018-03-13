package com.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestSrvController {

	@RequestMapping(value="/TestRest", produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<String> testRest(){
		List<String> lst = new ArrayList<>();
		lst.add("One");
		lst.add("Two");
		lst.add("Three");
		return lst;
	}
}
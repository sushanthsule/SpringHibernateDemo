package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Person;
import com.example.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@Validated @RequestBody Person person, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
	        List<String> message = new ArrayList<String>();
	        for (FieldError e : errors){
	            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
		}
		Person savedPerson = personService.save(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@Validated @RequestBody Person person, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
	        List<String> message = new ArrayList<String>();
	        for (FieldError e : errors){
	            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
	        }
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
		}
		if(person.getId() == null || person.getId() == 0) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{'message' : 'Please use post method for creating new entry'}");
		}
		Person savedPerson = personService.save(person);
		return ResponseEntity.ok(savedPerson);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		personService.delete(id);
		return ResponseEntity.ok("{'message' : 'Successfully deleted entry'");
	}
	
	@GetMapping(value = "/getall")
	public ResponseEntity<?> getall(){
		List<Person> personlists = personService.getall();
		return ResponseEntity.ok(personlists);
	}
	
}

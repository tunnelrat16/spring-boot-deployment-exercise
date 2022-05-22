package com.sikaeducation.barkwire.dog;

import java.util.Map;
import java.util.HashMap;

import com.sikaeducation.barkwire.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("api/dogs")
public class DogController {
  @Autowired
  private DogService dogService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Iterable<Dog>> list(){
    Iterable<Dog> dogs = dogService.list();
    return createHashPlural(dogs);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Dog> read(@PathVariable Long id) {
    Dog dog = dogService
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("No dog with that ID"));
    return createHashSingular(dog);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Dog> create(@Validated @RequestBody Dog dog) {
    Dog createdDog = dogService.create(dog);
    return createHashSingular(createdDog);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Dog> update(@RequestBody Dog dog, @PathVariable Long id) {
    Dog updatedDog = dogService
      .update(dog)
      .orElseThrow(() -> new ResourceNotFoundException("No dog with that ID"));

    return createHashSingular(updatedDog);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    dogService.deleteById(id);
  }

  private Map<String, Dog> createHashSingular(Dog dog){
    Map<String, Dog> response = new HashMap<String, Dog>();
    response.put("dog", dog);

    return response;
  }

  private Map<String, Iterable<Dog>> createHashPlural(Iterable<Dog> dogs){
    Map<String, Iterable<Dog>> response = new HashMap<String, Iterable<Dog>>();
    response.put("dogs", dogs);

    return response;
  }
}

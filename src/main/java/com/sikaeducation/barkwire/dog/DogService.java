package com.sikaeducation.barkwire.dog;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogService {
  @Autowired
  private DogRepository dogRepository;

  public Iterable<Dog> list(){
    return dogRepository.findAll();
  }

  public Optional<Dog> findById(Long id){
    return dogRepository.findById(id);
  }

  public Dog create(Dog dog) {
    return dogRepository.save(dog);
  }

  public Optional<Dog> update(Dog dog) {
    Optional<Dog> foundDog = dogRepository.findById(dog.getId());

    if (foundDog.isPresent()) {
        Dog updatedDog = foundDog.get();
        updatedDog.setName(dog.getName());
        updatedDog.setBreed(dog.getBreed());

        dogRepository.save(updatedDog);
        return Optional.of(updatedDog);
      } else {
        return Optional.empty();
      }
  }

  public void deleteById(Long id) {
    dogRepository.deleteById(id);
  }
}

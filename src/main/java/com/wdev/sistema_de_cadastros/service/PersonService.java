package com.wdev.sistema_de_cadastros.service;

import com.wdev.sistema_de_cadastros.DTO.PersonDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.PersonModel;
import com.wdev.sistema_de_cadastros.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PersonService {
    @Autowired
    private  PersonRepository personRepository;

    public Optional<PersonModel> findById(UUID id){
        return personRepository.findById(id);
    }

    public List<PersonModel> findByNameContainingIgnoreCase(Optional<String> name){
        return personRepository.findByNameContainingIgnoreCase(name);
    }

    public SequencedCollection<PersonModel> findPersonByParamsOrGetAll(Optional<String> name, Optional<Integer> age, Optional<String> email){
        List<PersonModel> persons;
        if (name.isPresent()) {
            persons = personRepository.findByNameContainingIgnoreCase(name);
        } else if (age.isPresent()) {
            persons = personRepository.findByAge(age);
        } else if (email.isPresent()) {
            persons = personRepository.findByEmail(email);
        } else {
            persons = personRepository.findAll();
        }
        return persons;
    }

    public List findByAge(Optional<Integer> age){
        return personRepository.findByAge(age);
    }

    public List<PersonModel> findAll(){
        return personRepository.findAll();
    }

    public PersonModel savePerson(PersonDTO personDTO) throws CustomizedExceptions {
        BigDecimal alturaConvertida = new BigDecimal(personDTO.getHeight().replace(",", "."));

        PersonModel buildPerson = new PersonModel();
        buildPerson.setName(personDTO.getName());
        buildPerson.setEmail(personDTO.getEmail());
        buildPerson.setAge(personDTO.getAge());
        buildPerson.setHeight(alturaConvertida);

        List byEmail = personRepository.findByEmail(Optional.ofNullable(personDTO.getEmail()));
        if (!byEmail.isEmpty()) {
            throw CustomizedExceptions.emailExistsException("Email already registered!");
        }
        return personRepository.save(buildPerson);
    }

    public void deleteById(UUID id) throws CustomizedExceptions {
        boolean present = personRepository.findById(id).isPresent();
        if(!present){
            throw CustomizedExceptions.idNotExistException("There was an error deleting your id, check that it is correct");
        }
        personRepository.deleteById(id);
    }
}

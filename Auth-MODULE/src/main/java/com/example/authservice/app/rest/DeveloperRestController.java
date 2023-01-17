package com.example.authservice.app.rest;

import com.example.authservice.app.model.Developer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1")
public class DeveloperRestController {

    private List<Developer> developers = Stream.of(
            new Developer(1L,"Ivam","Ivanov"),
            new Developer(2L,"Sergey","Sergeev"),
            new Developer(3L,"Genady","Bukin")
    ).collect(Collectors.toList());




    @GetMapping("/developers")
    public List<Developer> getAll(){
        return developers;
    }

    @GetMapping("/developers/{id}")
    public Developer getById(@PathVariable Long id){
        return developers.stream().filter(developer -> developer.getId()
                .equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Developer developer(@RequestBody Developer developer){
        this.developers.add(developer);
        return developer;
    }

    @DeleteMapping("{id}")
    public void deleteById(Long id){
        this.developers.removeIf(developer -> developer.getId().equals(id));
    }

 }

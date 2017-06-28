package com.hedgo.restwithcors.controler;

import com.hedgo.restwithcors.model.Hero;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/heroes")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class HeroController {
    static public List<Hero> heroList = new ArrayList<Hero>();

    static {
        heroList.add(new Hero(1, "Superman"));
        heroList.add(new Hero(2, "Batman"));
        heroList.add(new Hero(3, "Spiderman"));
        heroList.add(new Hero(4, "Punisher"));
    }

    @GetMapping
    public List<Hero> getHeroes(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("getAll start");
        heroList.forEach(System.out::print);
        System.out.println("getAll end");
        return heroList;
    }

    @GetMapping("/{heroId}")
    @ResponseBody
    public Hero getHero(@PathVariable int heroId) {
        System.out.println("getOne id =" + heroId);
        return heroList.stream()
                .filter(x -> x.getId() == heroId)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @ResponseBody
    public Hero addHero(@RequestBody Hero hero) throws Exception {
        System.out.println("create");
        heroList.add(hero);
        return hero;
    }

    @PutMapping
    @ResponseBody
    public Hero updateHero(@RequestBody Hero hero) {
        System.out.println("update with hero =" + hero);
        Hero heroInList = heroList.stream()
                .filter(x -> x.getId() == hero.getId())
                .findFirst()
                .orElse(null);
        heroInList.setName(hero.getName());

        heroList.forEach(System.out::print);

        return heroInList;
    }

    @DeleteMapping("/{heroId}")
    @ResponseBody
    public boolean deleteHero(@PathVariable int heroId) {
        System.out.println("delete id =" + heroId);
        heroList = heroList.stream().filter(x -> x.getId() != heroId).collect(Collectors.toList());
        return false;
    }
}
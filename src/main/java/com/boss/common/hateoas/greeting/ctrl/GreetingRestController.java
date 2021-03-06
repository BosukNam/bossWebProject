package com.boss.common.hateoas.greeting.ctrl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.boss.common.hateoas.greeting.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GreetingRestController {

    private static final String TEMPLATE = "Hello, %s!";

    @Autowired
    private RedisTemplate redisStringTemplate;

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {

        redisStringTemplate.opsForValue().set(name, "hello");
        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingRestController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}

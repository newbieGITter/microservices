package eu.kielczewski.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import eu.kielczewski.example.domain.User;
import eu.kielczewski.example.exception.UserAlreadyExistsException;
import eu.kielczewski.example.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Inject
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
    public User createUser(@RequestBody @Valid final User user) {
        LOGGER.debug("Received request to create the {}", user);
        return userService.save(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> listUsers() {
        LOGGER.debug("Received request to list all users");
        return userService.getList();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getMessage();
    }

}

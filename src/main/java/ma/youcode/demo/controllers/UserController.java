package ma.youcode.demo.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.youcode.demo.models.User;
import ma.youcode.demo.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
/*
 * ce contrôleur est un contrôleur REST.cette classe va pouvoir traiter les
 * requêtes que nous allons définir. Il indique aussi que chaque méthode va
 * renvoyer directement la réponse JSON à l'utilisateur, donc pas de vue dans le
 * circuit.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	// produces -> serialisation
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// recuperer id via url =>
	public ResponseEntity<User> getUser(@PathVariable String id) {

		User user = userService.getByUserId(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// reçevoir des valeurs depuis l'url
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<User> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "15") int limit) {
		List<User> users = userService.getUsers(page, limit);

		return users;
	}

	@RolesAllowed("Admin")
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {

		User createUser = userService.createUser(user);
		return new ResponseEntity<User>(createUser, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
		User userUpdate = userService.updateUser(id, user);
		return new ResponseEntity<User>(userUpdate, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

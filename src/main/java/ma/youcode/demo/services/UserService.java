package ma.youcode.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ma.youcode.demo.dto.UserDto;
import ma.youcode.demo.models.User;

public interface UserService extends UserDetailsService{
	
	User createUser(User userDto);

	User getUser(String email);
	
	User getByUserId(String userId);
	
	User updateUser(String userId, User user);
	
	void deleteUser(String userId);

	List<User> getUsers(int page, int limit);

}

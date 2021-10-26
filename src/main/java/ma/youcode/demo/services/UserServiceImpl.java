package ma.youcode.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ma.youcode.demo.models.User;
import ma.youcode.demo.repositories.UserRepository;
import ma.youcode.demo.security.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(User userDto) {
		User checkUser = userRepository.findByEmail(userDto.getEmail());

		if (checkUser != null)
			throw new RuntimeException("User déjà existe !");

		//User userEntity = new User();
		// copie le contenu de usrDto dans user
		//BeanUtils.copyProperties(userDto, userEntity);
		
		ModelMapper modelMapper = new ModelMapper();
		User userEntity = modelMapper.map(userDto, User.class);
		
		
		System.out.println("email" + userDto.getEmail());

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setEmailVerificationToken("token");
		userEntity.setUserId(utils.generateStringId(60));

		User newUser = userRepository.save(userEntity);

		return newUser;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(),
				new ArrayList<>());
	}

	@Override
	public User getUser(String email) {
		User userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return userEntity;
	}

	@Override
	public User getByUserId(String userId) {
		User userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		return userEntity;
	}

	@Override
	public User updateUser(String userId, User user) {

		User userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		userEntity.setNom(user.getNom());
		userEntity.setPrenom(user.getPrenom());

		User userUpdate = userRepository.save(userEntity);

		return userUpdate;
	}

	@Override
	public void deleteUser(String userId) {
		User userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		userRepository.delete(userEntity);

	}

	@Override
	public List<User> getUsers(int page, int limit) {
		if (page > 0) {
			page -= 1;
		}
		
		org.springframework.data.domain.Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<User> userPage = userRepository.findAll(pageableRequest);
		List<User> users = userPage.getContent();
		
		return users;
	}

}

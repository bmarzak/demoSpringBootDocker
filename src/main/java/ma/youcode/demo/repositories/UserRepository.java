package ma.youcode.demo.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ma.youcode.demo.models.User;

//permet de préciser que le bean est un repository (dao)
@Repository
//CrudRepository<User, Long>
public interface UserRepository  extends PagingAndSortingRepository<User, Long>{
	
	User findByEmail(String email);
	
	User findByUserId(String userId);

}

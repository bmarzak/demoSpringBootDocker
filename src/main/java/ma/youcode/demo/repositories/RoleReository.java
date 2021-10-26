package ma.youcode.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.demo.models.Role;

public interface RoleReository extends JpaRepository<Role, Long> {

}

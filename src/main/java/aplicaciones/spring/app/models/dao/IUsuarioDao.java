package aplicaciones.spring.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import aplicaciones.spring.app.models.entity.User;

public interface IUsuarioDao extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
}

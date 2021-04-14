package aplicaciones.spring.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import aplicaciones.spring.app.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long>{

	
	
}

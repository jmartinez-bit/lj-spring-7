package aplicaciones.spring.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aplicaciones.spring.app.models.dao.IRoleDao;
import aplicaciones.spring.app.models.dao.IUsuarioDao;
import aplicaciones.spring.app.models.entity.Role;
import aplicaciones.spring.app.models.entity.User;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = usuarioDao.findByUsername(username);
		
		if(user == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"'");
			throw new UsernameNotFoundException("Username "+username+" no existe en el sistema!");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: user.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("Error login: usuario '"+username+"' no tiene roles asignados!");
			throw new UsernameNotFoundException("Error login: usuario '"+username+"' no tiene roles asignados!");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) usuarioDao.findAll();
	}
	
	@Transactional
	public User save(User user) {
		user.setEnabled(true);
		user.setPassword(encoder.encode(user.getPassword()));
		User userNew = usuarioDao.save(user);
		Role role = new Role();
		role.setUserId(userNew);
		role.setAuthority("ROLE_USER");
		roleDao.save(role);
		return userNew;
	}
	
	@Transactional
	public User update(User user) {
		return usuarioDao.save(user);
	}
	
	@Transactional
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}
	
	public User findOne(Long id) {
		return usuarioDao.findById(id).get();
	}

}

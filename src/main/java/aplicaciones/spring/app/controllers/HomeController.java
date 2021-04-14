package aplicaciones.spring.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import aplicaciones.spring.app.models.entity.User;
import aplicaciones.spring.app.models.service.JpaUserDetailsService;

@Controller
public class HomeController {

	@Autowired
	private JpaUserDetailsService userDetails;
	
	@GetMapping("/")
	public String index(Authentication authentication, Model model) {
		
		if(authentication != null) {
			model.addAttribute("username", authentication.getName());
		}
		
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("users", userDetails.findAll());
		return "index";
	}
	
	@GetMapping("/form")
	public String create(Model model) {
		User user = new User();
		model.addAttribute("titulo", "Nuevo contacto");
		model.addAttribute("edit", false);
		model.addAttribute("user", user);
		return "form";
	}
	
	@PostMapping("/form")
	public String save(@Valid User user, BindingResult result, Model model, RedirectAttributes flash) {
		userDetails.save(user);
		return "redirect:/";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid User user, BindingResult result, Model model, RedirectAttributes flash) {
		User userEdit = userDetails.findOne(user.getId());
		userEdit.setUsername(user.getUsername());
		userEdit.setLastName(user.getLastName());
		userDetails.update(userEdit);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String create(@PathVariable(value = "id") Long id, Model model) {
		User user = null;
		
		if(id > 0) {
			user = userDetails.findOne(id);
		}else {
			return "redirect:/";
		}
		
		model.addAttribute("titulo", "Editar contacto");
		model.addAttribute("edit", true);
		model.addAttribute("user", user);
		return "form";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes flash) {
		userDetails.delete(id);
		return "redirect:/";
	}
	
}

package aplicaciones.spring.app.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import aplicaciones.spring.app.models.entity.Role;
import aplicaciones.spring.app.models.entity.User;
import aplicaciones.spring.app.models.service.JpaUserDetailsService;

@Controller
public class LoginController {
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetails;
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model, Principal principal,
			RedirectAttributes flash, @RequestParam(value = "logout", required = false) String logout) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Nombre de usuario o constraseña incorrecto");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Gracias por visitar !!");
		}

		return "login";
	}
	
	@GetMapping("/register")
	public String create(Model model) {
		User user = new User();
		model.addAttribute("titulo", "Registrar contacto");
		model.addAttribute("edit", false);
		model.addAttribute("user", user);
		return "form";
	}

}

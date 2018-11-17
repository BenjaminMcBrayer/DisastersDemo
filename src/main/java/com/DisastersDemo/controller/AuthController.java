package com.DisastersDemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DisastersDemo.entity.User;
import com.DisastersDemo.repo.UsersRepository;

@Controller
@SessionAttributes({ "user" })
public class AuthController {

	@Autowired
	UsersRepository uR;

	// Login, logout, sessions
	@RequestMapping("/login")
	public ModelAndView showLoginForm() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView submitLoginForm(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session, RedirectAttributes redir) {
		User user = uR.findByUsername(username);
		if (user == null || !password.equals(user.getPassword())) {
			ModelAndView mv = new ModelAndView("login");
			mv.addObject("message", "Incorrect username or password.");
			return mv;
		}
		session.setAttribute("user", user);
		redir.addFlashAttribute("message", "Logged in.");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session, RedirectAttributes redir) {
		session.invalidate();
		redir.addFlashAttribute("message", "Logged out.");
		return new ModelAndView("redirect:/");
	}
}

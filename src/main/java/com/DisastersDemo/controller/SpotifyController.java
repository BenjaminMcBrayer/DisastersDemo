package com.DisastersDemo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DisastersDemo.service.spotify.SpotifyService;

@Controller
@SessionAttributes("accessToken")
public class SpotifyController {

	@Autowired
	SpotifyService sS;
	
	@RequestMapping("/spotifycallback")
	public ModelAndView spotifyCallback(@RequestParam("code") String code, HttpSession session) {
		System.out.println(code);
		ModelAndView mv = new ModelAndView("redirect:/sketcher");
		mv.addObject("code", code);
		String accessToken = sS.getSpotifyAccessToken(code);
		session.setAttribute("accessToken", accessToken);
		return mv;
	}
	
	@RequestMapping("/spotifysearchtest")
	public ModelAndView spotifySearchTest() {
		RestTemplate rT = new RestTemplate();
		
		return new ModelAndView();
	}
}

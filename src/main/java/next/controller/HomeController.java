package next.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.repository.QuestionRepository;

@Controller
public class HomeController {
	@Autowired
	private QuestionRepository questionRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) throws Exception {
		model.addAttribute("questions", questionRepository.findAll());
		return "index";
	}
}

package next.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import next.dao.QuestionDao;

@Controller
public class HomeController {
	@Autowired
	private QuestionDao questionDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() throws Exception {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("questions", questionDao.findAll());
		return mav;
	}
}

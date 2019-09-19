package scb.wdb.coco;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import scb.wdb.domain.bbs;
import scb.wdb.domain.user;
import scb.wdb.persistence.bbs_dao;
import scb.wdb.persistence.member_dao;

@Controller
public class HomeController {
	
	@Inject
	private member_dao m_dao;
	
	@Inject
	private bbs_dao b_dao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public int login(user u, HttpSession Hsession) {
		return m_dao.Login(u, Hsession); 
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public int register(user u) {
		return m_dao.Register(u);
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	@RequestMapping(value = "/write_bbs", method = RequestMethod.GET)
	public String write_bbs(HttpSession session) {
		user u = (user) session.getAttribute("u");
		if(u == null) return "redirect:/";
		return "write_bbs";
	}
	
	@ResponseBody
	@RequestMapping(value = "/write_bbs", method = RequestMethod.POST)
	public int write_bbs(bbs b, HttpSession session) {
		user u = (user) session.getAttribute("u");
		if(u == null) return -1;
		b.setB_owner(u.getU_no());
		return b_dao.write_bbs(b);
	}
	
	@ResponseBody
	@RequestMapping(value = "/bbs_all", method = RequestMethod.GET)
	public List<bbs> bbs_all() {
		return b_dao.bbs_all();
	}
	
	@ResponseBody
	@RequestMapping(value = "/get_bbs", method = RequestMethod.GET)
	public bbs get_bbs(bbs b) {
		return b_dao.get_bbs(b);
	}

	
	
	@ControllerAdvice
	public class JsonpAdviceController extends AbstractJsonpResponseBodyAdvice {
		public JsonpAdviceController() {
			super("callback");
		}
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	
}

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
	// 싱글톤으로 선언된 것을 주입 받는다.
	@Inject
	//해당하는 class에서 member_dao라는 자료형을 가지는 m_dao를 선언.
	private member_dao m_dao;
	
	@Inject
	private bbs_dao b_dao;
	
	// GET으로 /페이지를 요청하면
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		// VIEW 폴더에 indox.jsp 파일을 view 페이지로 선언.
		return "index";
	}
	
	// body의 값을 매핑하는 용도
	@ResponseBody
	// post로 login으로 요청할 경우
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//login이라는 메소드명을 가지고 매개변수는 user u, httpsession Hsession
	public int login(user u, HttpSession Hsession) {
		//m_dao.Login(u, Hsession)을 호출하고 반환한다.
		return m_dao.Login(u, Hsession); 
	}
	
	//GET으로 reigster페이즈를 요청 할 경우.
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		// view안에 있는 register.jsp를 view 페이지로 지정한다.
		return "register";
	}
	
	// body의 값을 매핑하는 용도
	@ResponseBody
	// post로 /register 페이지로 요청할 경우.
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	// 메소드 이름이 register 매개변수는 user 자료형의 변수명은 u
	public int register(user u) {
		return m_dao.Register(u);
	}
	
	// GET으로 main페이지에 요청할 때 
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		// VIEWS 폴더에 있는 main.jsp를 view 페이지로 선언한다.
		return "main";
	}
	
	@RequestMapping(value = "/write_bbs", method = RequestMethod.GET)
	public String write_bbs(HttpSession session) {
		// session에 u로 된 정보를 member 객체에 u로 담음
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

	
	// JSONP를 사용하기 위해 선언
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

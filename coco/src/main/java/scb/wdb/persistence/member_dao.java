package scb.wdb.persistence;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import scb.wdb.domain.user;

@Repository
public class member_dao {
	
	@Inject
	private SqlSession session;
	private static final String NameSpace = "memberMapper.";
	
	public int Id_Check(user u) {
		try {
			return session.selectOne(NameSpace + "Id_Check", u);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int Register(user u) {
		int Id_Check_Result = Id_Check(u);
		if(Id_Check_Result != 0) return Id_Check_Result;
		
		try {
			session.insert(NameSpace + "Register", u);
			return 0;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}
	
	public int Login(user u, HttpSession Hsession) {
		int Id_Search_Result = -1;
		try {
			Id_Search_Result = session.selectOne(NameSpace + "Login", u);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		if(Id_Search_Result != 1) return Id_Search_Result;
		
		try {
			user u_info = session.selectOne(NameSpace + "Login_Info", u);
			Hsession.setAttribute("u", u_info);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}
}

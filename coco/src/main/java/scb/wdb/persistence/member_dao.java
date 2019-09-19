package scb.wdb.persistence;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import scb.wdb.domain.user;

@Repository
public class member_dao {
	// 싱글톤으로 선언된 것을 주입 받음.
	@Inject
	private SqlSession session;
	// 자신이 사용하고 싶은 mapper의 namespace이름
	private static final String NameSpace = "memberMapper.";
	
	public int Id_Check(user u) {
		// 예외 처리
		try {
			// 결과값을 반환
			return session.selectOne(NameSpace + "Id_Check", u);
		}
		catch (Exception e) {
			// 오류 발생 시 오류 출력과 -1 반환
			e.printStackTrace();
			return -1;
		}
	}
	
	public int Register(user u) {
		// 위에 선언된 Id_Check(u)의 결과값을 Id_Check_Result에 담는다.
		int Id_Check_Result = Id_Check(u);
		// Id_Check_Result값이 0이 아니라면 Id_Check_Result값을 반환
		// 0은 아이디가 중복되지 않음
		if(Id_Check_Result != 0) return Id_Check_Result;
		
		try {
			// sqlsession에 insert를 할꺼고 내가 쓸 맵퍼는 위에 12번째줄에 선언된거고
            // 내가 사용 할 QUERY문은 Register를 사용하고 넘길 값은 u이다.
            // 이후 설명은 다 생략 똑같은 내용.
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

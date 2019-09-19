package scb.wdb.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import scb.wdb.domain.bbs;

@Repository
public class bbs_dao {
	
	@Inject
	private SqlSession session;
	private static final String NameSpace = "bbsMapper.";
	
	public int write_bbs(bbs b) {
		try {
			session.insert(NameSpace + "write_bbs", b);
			return 0;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public List<bbs> bbs_all(){
		try {
			return session.selectList(NameSpace + "bbs_all");
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public bbs get_bbs(bbs b){
		//시도
		try {
			//리스트로 받은 게시글을 리턴
			return session.selectOne(NameSpace + "get_bbs", b);
		}
		catch (Exception e) {
			//오류 로그 출력
			e.printStackTrace();
			//오류시 널 출력
			return null;
		}
	}
}

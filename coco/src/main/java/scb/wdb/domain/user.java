package scb.wdb.domain;

public class user {
	// 해당 클래스에서 u_no, u_id, u_pw를 선언
	private int u_no;
	private String u_id;
	private String u_pw;
	
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	//String 값을 반환하는 get_M_id 메소드
	public String getU_id() {
		// 해당 클래스의 전역변수로 선언된 m_id를 반환한다.
		return u_id;
	}
	// 반환값이 없는 setU_id 매개변수는 String u_id를 가진다.
	public void setU_id(String u_id) {
		// 전역 변수에 있는 u_id에 매개변수로 받은 u_id를 담는다.
		this.u_id = u_id;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
}

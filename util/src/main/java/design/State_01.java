package design;

public class State_01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State_sample ss = State_sample.STATE1.action(Action_sample.TYPE1);
		System.out.println(ss);
		State_sample ss1 = ss.action(Action_sample.TYPE2);
		System.out.println(ss1);
		State_sample ss2 = ss.action(Action_sample.TYPE3);
		System.out.println(ss2);
	}

}


enum Action_sample {
	TYPE1, TYPE2, TYPE3;
}

enum State_sample {
	STATE1 {
		public State_sample action(Action_sample as) {
			switch(as) {
				case TYPE1:
					return STATE2;
				default:
					return null;
			}
		}
	},
	
	STATE2 {
		public State_sample action(Action_sample as) {
			switch(as) {
				case TYPE2:
					return STATE3;
				default:
					return null;
			}
		}
	},
	
	STATE3 {
		public State_sample action(Action_sample as) {
			switch(as) {
				case TYPE3:
					return null;
				default:
					return null;
			}
		}
	};
	
	public abstract State_sample action(Action_sample as);
	
//	TEST01("code", "des");
//	
//	String code = null;
//	String des = null;
//	
//	private State_sample(String code, String des) {
//		this.code = code;
//		this.des = des;
//	}
//	
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getDes() {
//		return des;
//	}
//
//	public void setDes(String des) {
//		this.des = des;
//	}
}
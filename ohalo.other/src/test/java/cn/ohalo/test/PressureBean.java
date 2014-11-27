package cn.ohalo.test;

public class PressureBean {

	int userId;
	String bpDate;
	String bpTime;
	int syspress; // 收缩压(mmHg)
	int diapress; // 舒张压(mmHg)

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBpDate() {
		return bpDate;
	}

	public void setBpDate(String bpDate) {
		this.bpDate = bpDate;
	}

	public String getBpTime() {
		return bpTime;
	}

	public void setBpTime(String bpTime) {
		this.bpTime = bpTime;
	}

	public int getSyspress() {
		return syspress;
	}

	public void setSyspress(int syspress) {
		this.syspress = syspress;
	}

	public int getDiapress() {
		return diapress;
	}

	public void setDiapress(int diapress) {
		this.diapress = diapress;
	}
}

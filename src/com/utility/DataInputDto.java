package com.utility;

import java.io.Serializable;

public class DataInputDto implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private int speed;
	private int vGap;
	private String operation;
	

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getvGap() {
		return vGap;
	}

	public void setvGap(int vGap) {
		this.vGap = vGap;
	}

	@Override
	public String toString() {
		return "DataInputDto: {speed=" + getSpeed() + "vGap=" + getvGap() + "operation=" + getOperation() + "}";

	}

}

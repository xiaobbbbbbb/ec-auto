package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Vector;



public class DownsideVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Vector<Integer> vector;

	private String data;

	

	public Vector<Integer> getVector() {
		return vector;
	}



	public void setVector(Vector<Integer> vector) {
		this.vector = vector;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	@Override
	public String toString() {
		return "SeriesVO [vector=" + vector + ", data=" + data + "]";
	}
}

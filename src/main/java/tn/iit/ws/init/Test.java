package tn.iit.ws.init;

import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Date d1 = new Date();
		Date d = new Date(d1.getTime());
		d.setDate(d.getDate()+1);
		System.out.println(d.getTime()-d1.getTime());
	}
}

package com.streamtest.controller;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((char)(Math.random()*('ÆR'-'°¡')+'°¡'));
		int min='°¡';
		for(int i=1;i<100;i++) {
			System.out.println((char)(min+i));
		}
	}

}

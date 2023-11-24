package com.streamtest.controller;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((char)(Math.random()*('힣'-'가')+'가'));
		int min='가';
		for(int i=1;i<100;i++) {
			System.out.println((char)(Math.random()*('힣'-'가')+'가'));
			//System.out.println((char)(min+i));
		}
	}

}

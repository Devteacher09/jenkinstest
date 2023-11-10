package com.streamtest.controller;

import java.util.stream.Stream;

public class Stream05_병렬처리하기 {

	public static void main(String[] args) {
		long start=System.nanoTime();
		
		long result=sequentialSum(100000000L);
		
		System.out.println(System.nanoTime()-start+"ms");
		
		//병렬처리시 
		long start2=System.nanoTime();
		long result2=sequentialParallel(100000000L);
		System.out.println(System.nanoTime()-start2+"ms");
		//왜 병렬처리한게 속도가 느리니?
		
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
	
	//매개변수로 전달받은값까지 합을 구하는 메소드 구현
	public static long sequentialSum(long n) {
		//for문을 이용해서 구현하기
//		long result=0;
//		for(long i=1;i<=n;i++) {
//			result+=i;
//		}
//		return result;
		return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, Long::sum);
		//return Stream.iterate(1L, i-> i+1).limit(n).reduce(0L,(total,next)->total+next);
	}
	//병렬처리 설정은 성능측정을 해봐야한다. 병력스트림을 잘못이용하면 오히려 성능이 더 떨러질수도있다.
	//성능테스트는 jmh라이브러리를 이용하여할 수 있다. -> jmhtest프로젝트 참고.
	public static long sequentialParallel(long n){
		return Stream.iterate(1L, i -> i+1).limit(n).parallel().reduce(0L, Long::sum);
	}
}

package com.streamtest.controller;

import java.util.stream.Stream;

public class Stream05_����ó���ϱ� {

	public static void main(String[] args) {
		long start=System.nanoTime();
		
		long result=sequentialSum(100000000L);
		
		System.out.println(System.nanoTime()-start+"ms");
		
		//����ó���� 
		long start2=System.nanoTime();
		long result2=sequentialParallel(100000000L);
		System.out.println(System.nanoTime()-start2+"ms");
		//�� ����ó���Ѱ� �ӵ��� ������?
		
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
	
	//�Ű������� ���޹��������� ���� ���ϴ� �޼ҵ� ����
	public static long sequentialSum(long n) {
		//for���� �̿��ؼ� �����ϱ�
//		long result=0;
//		for(long i=1;i<=n;i++) {
//			result+=i;
//		}
//		return result;
		return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, Long::sum);
		//return Stream.iterate(1L, i-> i+1).limit(n).reduce(0L,(total,next)->total+next);
	}
	//����ó�� ������ ���������� �غ����Ѵ�. ���½�Ʈ���� �߸��̿��ϸ� ������ ������ �� �����������ִ�.
	//�����׽�Ʈ�� jmh���̺귯���� �̿��Ͽ��� �� �ִ�. -> jmhtest������Ʈ ����.
	public static long sequentialParallel(long n){
		return Stream.iterate(1L, i -> i+1).limit(n).parallel().reduce(0L, Long::sum);
	}
}

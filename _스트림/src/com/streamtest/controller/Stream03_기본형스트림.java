package com.streamtest.controller;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream03_기본형스트림 {
	//세가지의 기본형특화 스트림을 제공한다. 
	//스트림 API는 박싱 비용을 피할 수 있도록 각 기본자료형의 스트림을 제공한다. -> 숫자 데이터를 관리할대 최적화할 수 있다.
	//int ->  IntStream
	//double -> DoubleStream
	//loing -> longStream
	//각 스트림 인터페이스에는 각 값을 계산할 수 있는 유용한 메소드를 가지고 있음
	// sum, max, reduce 등
	private static List<Student> list=StudentDao.getStudents();
	
	public static void main(String[] args) {
		//random클래스 이용하여 stream만들기 
		//기본으로 만들면 inits()매소드를 이용하면 무한으로 랜덤값을 생성함. 
		//limit()메소드를 이용해서 제한함.
		//1~10 랜덤숫자 5개 출력하기 *로또번호출력하기
		IntStream is=new Random().ints(1,45).distinct().limit(6);
		is.forEach(s->System.out.print(s+" "));
		System.out.println();
		
		System.out.println("===== range()메소드이용하기 =====");
		//range()매소드를 이용해서 기본자료형 stream 생성하기
		//순서대로 숫자를 나열해서 stream을 생성하는 매소드
		//range(첫번째숫자, 마지막숫자)
		IntStream.range(10,20).forEach(s->System.out.print(s+" "));
		System.out.println();
		LongStream.range(100, 110).forEach(s->System.out.print(s+" "));
		System.out.println();
		
		//위에서 제공하는 000Stream으로 변환할때는 기본 Stream에서 mapToInt, mapToDouble, mapToLong을 이용한다.
		IntStream intstream=list.stream().mapToInt(Student::getAge);
		System.out.println(intstream);
		System.out.println(intstream.count());
		//스트림을 한번사용하면 재사용하지 못함.
		intstream=list.stream().mapToInt(Student::getAge);
		System.out.println(intstream.distinct().count());
		
		//학생중 나이가 가장 많은 학생의 나이 구하기
		//max()메소드 이용 : max매소드는 optional클래스를 반환하기 때문에 출력할때 OptionalInt클래스가 제공하는 메소드를 이용해서 출력함.
		OptionalInt opint=list.stream().mapToInt(Student::getAge).max();
		int maxAge=opint.orElse(1);
		System.out.println(maxAge);
		
		//클래스의 자료형이 다르기때문에 대입이 불가능함.
		//기본형특화 stream을 일반 stream으로 다시 변경하려면 boxed()매소드를 이용하면됨.
		//Stream<Integer> ageStream=list.stream().mapToInt(Student::getAge);//에러발생
		Stream<Integer> ageStream=list.stream().mapToInt(Student::getAge).boxed();
		
		//System.out.println(maxAge);
		
		//IntStream클래스를 이용해서 특정범위의 숫자가져오기
		//range, rangeClosed매소드를 이용해서 특정범위를 숫자 전체를 가져올수 있다
		//range()는 종료값이 포함되지않음
		//rangeClosed() 종료값이 포함됨.
		IntStream rangeint=IntStream.range(1, 100);//1~99까지의 수 가져오기
		rangeint.forEach(System.out::println);
		rangeint=IntStream.rangeClosed(1, 100);
		rangeint.forEach(System.out::println);
		
		//doubleStream이용하기
		DoubleStream dstream=list.stream().mapToDouble(Student::getHeight);
		dstream.limit(5).forEach(System.out::println);
		dstream=list.stream().mapToDouble(Student::getHeight).distinct();
		//학생중 가장 큰키는 몇cm인가?
		OptionalDouble opDouble=dstream.max();
		System.out.println(opDouble.orElse(160.8));
		
		
		
		
		
	}
}








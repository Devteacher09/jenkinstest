package com.streamtest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream02_데이터조작 {

	public static void main(String[] args) {
		List<Student> students=StudentDao.getStudents();
		//스트림은 collection의 데이터를 내무반복문을 활용해서 최적화된 로직으로 collection 데이터를 filter(조건조회), map(특정데이터 출력), 
		//reduce(누적), match(일치하는 값 찾기)등을 실행하는 api이다. 
		//collection 데이터에 대해 filter, map, reduce, match등을 실행할 때 병렬처리기능까지 갖추고 있음.
		//내부로직(반복문을 통한 원하는 값을 찾는 등)을 구성하는게 아니라 SQL구문처럼 명령형방식으로 호출해서 사용하는 구문을 작성할 수 있게 됨.
		
		//streamAPI를 이용하지 않고 남자학생찾기
		//외부반복을 이용해서 for문부터 시작해서 사용해야함
		List<Student> result=new ArrayList();
		for(Student s : students) {
			if(s.getGender()==Student.Gender.M) {
				result.add(s);
			}
		}
		printStudent(result);		
		//streamAPI이용하기
		//java8버전부터 collection에 stream클래스를 지원하고 stream은 각종 collection의 데이터를 다룰수있는 메소드를 제공함.
		//filter(Predicate) collection데이터를 원하는값만 출력해내는기능
		//for문을 사용하지 않고 한줄로 가독성좋게 표현할 수 있다. 물론 람다에 익숙해야한다.
		//result=students.stream().filter(s->s.getGender()==Student.Gender.M).collect(Collectors.toList());
		result=students.stream().filter(s->s.getGender()==Student.Gender.M).collect(Collectors.toCollection(ArrayList::new));
		printStudent(result);
		
		//collect()는 조회한 결과가 Stream으로 나오는데 그 결과를 다시 collection객체로 반환해주는 기능을 한다.
		//collect()매개변수에 인수는 Collectors클래스가 제공하는 매소드를 이용한다.
		//Collectors클래스 메소드내용 정리하기 **
		
		//stream은 한개만 실행하는것이 아니고 여려개를 파이프라인으로 여거 streamAPI함수를 붙여서 실행이 가능하다.
		//남학생중 나이가 많은 3명만 조회하고싶을 경우
		System.out.println("==== stream 이용시 ====");
		result=students.stream().filter(s->s.getGender()==Student.Gender.M)
				.sorted(Comparator.comparing(Student::getAge).reversed()).limit(3)
				.collect(Collectors.toList());//sql문처럼 나열해서 조건을 구성할 수 있음.
		printStudent(result);
		//streamAPI를 사용하지 않고 조건조회하기
		result.clear();
		//남자만 조회
		for(Student s:students) {
			if(s.getGender()==Student.Gender.M) {
				result.add(s);
			}
		}
		//조회된결과로 sort
		//sort까지 구현하려면.. 힘드니.. 생략하겠음.ㅋㅋ
		result.sort(Comparator.comparing(Student::getAge).reversed());
		Iterator<Student> it=result.iterator();
		//앞에 3명만 출력하는 로직
		int count=0;
		while(it.hasNext()) {
			it.next();
			if(count>=3) {
				it.remove();
			}
			count++;
		}
		System.out.println("==== stream 미이용시 ====");
		printStudent(result);
		
		//stream 슬라이싱이용하기
		//takeWhile : filter에서 첫번째 false가 나오면 내부반복을 중단시키고 그전까지의 결과를 반환 
		//dropWhile : false가 나온 인덱스부터 마지막인덱스까지 모두 반환하는 메소드
		printStudent(students);
		System.out.println("======== filter이용 ==========");
		result=students.stream().filter(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);
		System.out.println("========= takeWhile이용 ==========");
		result=students.stream().takeWhile(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);	
		System.out.println("=========== dropWhile이용 ===========");
		result=students.stream().dropWhile(s->s.getAge()>10).collect(Collectors.toList());
		printStudent(result);
		
		//limit() : collection을 앞에서부터 원하는 인덱스까지 반환해주는 기능 -> sort와 많이 사용할것으로 예상됨.
		//sort후 top-n자료를 가져올때 용이할것으로 생각됨.
		System.out.println("===== 가장 어린학생 3명 조회하기 =====");
		result=students.stream().sorted(Comparator.comparing(Student::getAge)).limit(3).collect(Collectors.toList());
		printStudent(result);
		
		//요소건너뛰기
		//skip() : 앞인덱스부터 인수의값만큼 무시하고 다음부터 출력하는 메소드
		System.out.println("===== skip()메소드 이용하기 ====== ");
		result=students.stream().skip(4).collect(Collectors.toList());
		printStudent(result);
		
		
		//limit()와 skip()응용하기, 4번째부터 10번째학생출력하기
		System.out.println("====== 4번째부터 10번째학생출력하기 ======");
		result=students.stream().skip(3).limit(10).collect(Collectors.toList());
		printStudent(result);
		
		//hasMap은 List로 변환해서 출력해줘야한다.
		result=new ArrayList(StudentDao.getStudentMap().values());
		
		 
		
		//map()매소드 이용하기
		//특정 데이터만 추출하는 메소드
		List<String> studentNames=students.stream().map(Student::getName).collect(Collectors.toList());
		System.out.println(studentNames);
		
		//주소가 경기도 인것만 출력하기
		List<String> studentAddr=students.stream().map(Student::getAddress).filter(s->s.contains("경기도"))
				.collect(Collectors.toList());
		System.out.println(studentAddr);
		
		//각 인덱스의 문자열을 문자 한개씩 배열로 만들기
		List<String> strList=List.of("안녕하세요","나는나에요","안녕호호호");
		List<String[]> resultStringArr=strList.stream().map(s->s.split("")).collect(Collectors.toList());
		for(String[] strArr : resultStringArr) {
			for(String t: strArr) {
				System.out.println(t);
			}
		}
		System.out.println("Stream으로 출력하기");
		strList.stream().map(s->s.split("")).forEach(s->Arrays.stream(s).forEach(System.out::print));
		System.out.println();
		//위 예제에서 split은 배열로 반환하기 때문에 collect 메소드로 결과를 리스트로 만들면 List<String[]> 타입이 나오게 된다.
		//내용을 List<String>으로 출력하려면 flatMap(Arrays::stream)매소드를 이용해서 처리해야한다.
		
		
		//문자열에서 사용한 문자(알파벳) 중복값 없이 검색하기
		//스트링 배열이 반환되는 문제를 flatMap메소드를 이용해서 처리 -> 여러개로 생성된 스트림을 하나의 스트림으로 만들어주는 매소드!
		//배열형식으로 반환되는 여러개의 stream값을 하나의 스트림으로 만들어주는 기능 
		
		List<String> resultStr=strList.stream().map(s->s.split("")).flatMap(s->Arrays.stream(s)).distinct().collect(Collectors.toList());
		
		//map과 flatMap의 출력결과차이 확인
		System.out.println("일반Map실행 : "+strList.stream().map(s->s.split("")).collect(Collectors.toList()));
		
		//flatMap실행결과 -> List<String>으로 반환되기 때문에 기본 stream을 이용해서 filter, distinct등의 매소드를 이용하여 처리할 수 있음
		//flatMap은 주로 배열을 나열하여 데이터를 조작할때 사용함.
		System.out.println("flatMap 실행 : "+strList.stream().map(s->s.split("")).flatMap(s->Arrays.stream(s)).collect(Collectors.toList()));
		//List<String> resultStr=strList.stream().map(s->s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
		System.out.println(resultStr);
		
		List<Integer> targetNum=List.of(1,2,3,4,5);
		//저장된 값의 제곱근을 출력하는것 
		List<Integer> numResult=targetNum.stream().map(s->s*s).collect(Collectors.toList());
		System.out.println(numResult);
		
		//두개의 리스트 합쳐서 출력하기 -> 병합하기
		//streamapi매소드내부에서 stream를 다시 호출하여 구성할 수 있음.
		List<Integer> su1=List.of(1,2,3);
		List<Integer> su2=List.of(3,4);
		List<int[]> r=su1.stream().flatMap(i-> su2.stream().map(j->new int[] {i,j})).collect(Collectors.toList());
		//출력하기
		for(int[] v:r) {
			System.out.print("(");
			for(int i:v) {
				System.out.print(i);
			}
			System.out.println(")");
		}
		
		
		//필터링한 student객체 반환하기 -> map을 이용
		//깊은 복사를 하기 위해 clone를 사용함.  
		result=students.stream().filter(s->s.getAge()<20).map(s->s.clone()).collect(Collectors.toList());
		printStudent(result);
		
		
		
		
		//검색과 매칭하기
		//anyMatch : collection에서 Predicate의 결과가 true가 나오는게 하나라도 있으면 true를 반환해줌
		if(students.stream().anyMatch(s->s.getAge()==23)) {
			System.out.println("스무살이 있네!!");
		}
		//allMatch : collection에서 predicate의 결과가 모두 true를 반환하면 true를 반환해주는 메소드
		if(students.stream().allMatch(s->s.getAddress().length()>=3)) {
			System.out.println("주소가 모두 4글자 이상이다!");
		}
		
		//noneMatch : collection에 predicate의 결과가 모두 false가 나올경우 true를 반환하는 메소드
		if(students.stream().noneMatch(s->s.getName().equals("유병승"))) {
			System.out.println("유병승은 없습니다");
		}
		
		
		
		//collection의 검색결과 반환하기
		//findAny()매소드 : filter에서 true값을 반환하는 결과가 나오면 중단하고 그 값을 Optional객체로 반환함.
		//Optional클래스를 반환함.
		Optional<Student> searchResult=students.stream().filter(s->s.getAge()>5).findAny();
		
		//Optional null값처리에 대한 wapper클래스로 null값이 반환될때 발생하는 에러를 줄여줄 수 있음
		System.out.println(searchResult);
		//get : optional에 값을 출력하는 매소드 *optional에 값이 없으면 NoSearchElementException이 발생함.
		//searchResult.get();
		//isPresent : Optional객체에 값이 있는지 없는지 확인하는 매소드 있으면 true, 없으면 false가 출력됨.
		System.out.println(searchResult.isPresent());
		//ifPresent : 값이 있으면 매개변수(Consumer인터페이스) 로직을 실행하는 함수, 없으면 생략함.
		searchResult.ifPresent(s->System.out.println(s));
		//orElse : 값이 있으면 값을 출력하고 없으면 매개변수로 넘어온 값을 출력하는 기능.
		System.out.println(searchResult.orElse(new Student()));
		
		
		//reduce함계나 누적을 계산하는 함수
		//reduce는 두개의 인수를 받는다
		//1 : 매번 실행되는 결과(반환값)를 저장하는 변수
		//2 : collection의 값이 한개씩 대입되는 변수
		List<Integer> counter=List.of(5,6,8,1,24,5,6,712,4,5);
		//counter의 합계구하기
		Optional<Integer> resultSum=counter.stream().reduce((t,v)->t+v);
		System.out.println(resultSum.get());
		
		
		//나이가 많은 사람 5명
		System.out.println(students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).limit(5)
				.map(Student::getAge).collect(Collectors.toList()));
		
		//나이가 많은 사람 5명의 평균나이를 구하는 stream
		students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).limit(5)
		.map(Student::getAge).reduce((total,v)->total+v).ifPresent(totalVal->System.out.println(totalVal/5.0));
		
		
		Stream<Student> s=students.stream();
		s.filter(t->t.getAge()>30).collect(Collectors.toList());
		//stream클래스는 한번만 사용이 가능하다. 재사용이 불가능함.
		s.map(Student::getAddress).collect(Collectors.toList());//에러발생!!
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	private static void printStudent(List<Student> list) {
		System.out.println(list.size());
		System.out.println(list);		
	}
	
}

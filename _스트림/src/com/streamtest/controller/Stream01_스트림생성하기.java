package com.streamtest.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.streamtest.model.dao.StudentDao;
import com.streamtest.model.vo.Student;

public class Stream01_스트림생성하기 {
	//Stream데이터를 만드는 방법에 대해 알아보자.
	public static void main(String[] args) {
		System.out.println("===== Stream.of매소드이용해서만들기 =====");
//		값으로 스트림 만들기
//		Stream.of()매소드이용하기 
//		of매소드를 이용해서 각데이터를 stream으로 만들어서 활용할수있다
		Stream<String> test=Stream.of("Java","Oracle","Html","css","JavaScript");
		//List<String> result=test.filter(s->s.length()>4).collect(Collectors.toList());
//		System.out.println(result);
		test.filter(s->s.length()>4).forEach(System.out::println);
		
		//배열을 스트림으로 만들때는 Arrays.stream()매소드를 이용한다.
		String[] data= {"Java","Oracle","Html","css","JavaScript"};
		Arrays.stream(data);
		
		
		//다수의 기본값을 스트림으로 만들고 필터링해서 출력하기
		Stream<Integer> intValue=Stream.of(1,23,4,5,12,44);
		intValue.filter(v->v>20).forEach(System.out::println);
		//다수의 값을 스트림으로 만들어 최대값출력하기
		intValue=Stream.of(1,23,4,5,12,44);
		int maxVal=intValue.max((v,v1)->v-v1).orElse(0);
		System.out.println(maxVal);	
		
		//데이터가 널인(데이터 없는) 스트림 만들기
		System.out.println("==== Stream.empty() / Stream.ofNullable() 비어있는 스트림만들기 ====");
		Stream<Object> emptyStream= Stream.empty();
		Stream<String> emptyStream2= Stream.empty();
		Stream<Integer> emptyStream3= Stream.empty();
		//비어있기때문에 아무것도 출력되지않음, 더 중요한것은 nullpointer에러도 발생하지 않음.
		//자료형에 상관없이 모든 자료형에 대해 만들 수 있음
		emptyStream.forEach(System.out::println);
		
		//널이 값을 Stream에 대입할 수 있다. null값이 들어가게 되면 Stream매소드를 이용하면 nullpointerexception이 발생함.
		Stream<Student> nullStream=Stream.of(null,null,null);
		//nullStream.forEach(System.out::println);
		//nullStream.filter(s->s.getName().length()>3).forEach(System.out::println);
		
		//스트림을 이용할때 대입되는 객체가 널일수도있을때 stream을 생성하는 방식
		//null값이 있는 경우 빈Stream을 만들어 처리하기
		//삼항연산자를 이용해서 처리하기
		String value=null;
		Stream<String> strStream=value==null?Stream.empty():Stream.of(value);
		//ofNullable메소드 이용하기
		strStream=Stream.ofNullable(value);//value값이 null이면 empty()로 생성하고 아니면 value값으로 생성함.
		strStream.forEach(System.out::println);//null이기 때문에 출력되는값이 없음.
		value="test";
		strStream=Stream.ofNullable(value);
		strStream.forEach(System.out::println);//test가 출력됨.
		
		
		System.out.println("===== Stream.builder메소드 이용하기 =====");
		//Stream빌더로 생성하기 
		//builder()매소드이용하기
		//Stream.Builder클래스로 stream을 builder로 생성할 수 있다. -> Stream.Builder<T>
		//생성되 Stream.Builder클래스는 stream을 생성하는 클래스로 add메소드를 이용해서 stream에서활용할 데이터를 추가할 수 있음
		//add매소드를 통해 데이터를 추가한 후 Stream을 build()매소드로 생성한다.
		Stream.Builder<String> builder=Stream.builder();
		Stream<String> strStream1=builder.add("유병승").add("홍길동").add("이순신").build();
		strStream1.filter(s->s.contains("유")).forEach(System.out::println);
		
		System.out.println("===== Stream.generate메소드 이용하기 =====");
		//Gendrator이용해서 Stream만들기 -> 무한으로 반복되기 때문에 limit()메소드와 같이 사용
		//Stream.generate(Supplier<T>) -> Supplier T get()매소드를 가지고 있음
		//Stream.generate()매소드를 이용하면 supplier매소드에서 반환되는 값을 가지고 stream의 데이터를 만들 수 있음
	
		//홍길동 stream데이터로 만들기
		//아래와같이 설정하면 홍길동을 무한으로 생성함.
		//Stream.generate(()->"홍길동").forEach(System.out::println);
		//홍길동 원하는 갯수만큼만 생성하려면 limit()메소드를 이용해서 제한을 하면 된다.
		Stream.generate(()->"홍길동").limit(5).forEach(System.out::println);
		
		
		System.out.println("===== iterator메소드 이용하기 =====");
		//iterate()메소드를 이용해서 수열형태의 데이터 Stream을 만들 수 있다. -> 무한으로 생성되기 때문에 limit()메소드와 같이 써야함.
		//iterate()매소드에는 두개의 인수가 들어감
		//첫번째 : 초기값
		//두번째 : 한개의 매개변수, 리턴값을 갖는 함수형인터페이스 n -> 리턴형 * n의값은 첫번째 실행시에는 첫번재매개번수를
		//       그이후에는 이전 로직에서 반환된 값을 인수로 넣어줌. 
		//두번째 매개변수의 로직을 실행해서 데이터를 구성하여 Stream을 만듬 * 누적하는 reduce와 비슷한 로직임.
		//초기값(첫번째매개변수)이 0번째 값, 두번째 매개변수의 반환값을 1,2,3... 값으로 각각 stream에 저장함. 두 각 데이터를 반환되는 것으로 한개씩만들어줌. 
		Stream.iterate(10, n -> n*2).limit(10).forEach(System.out::println);
		Stream.iterate("하", n->n+"호").limit(5).forEach(System.out::println);
		
		//iterate로 피보나치수열 출력하기
		//iterate 피보나치만든 결과
		Stream.iterate(new int[] {0,1}, n->new int[] {n[1],n[0]+n[1]}).limit(20).forEach(t->{System.out.print(t[0]+t[1]);});
		System.out.println();
		System.out.println("==== 피보나치수열 값 출력하기 ====");
		Stream.iterate(new int[] {0,1}, n->new int[] {n[1],n[0]+n[1]}).limit(20).map(t->t[0]+", ").forEach(System.out::print);
		System.out.println();
		
		//iterate메소드이용하여 조건에 맞춰서 생성하기
		//iterate메소드는 두개의 세개의 매개변수를 받을 수도있음 -> 이매소드를 이용하면 굳이 limit() 매소드를 이용하지않고 중단할 수 있음.
		//첫번째 : stream초기값
		//두번째 : iterator를 중단할 수 있는 조건식 * false가 나오면 iterator는 중단됨 -> Predicate인터페이스
		//세번째 : stream을 만들 메소드
		Stream.iterate(0, n->n<100, n->n+1).forEach(System.out::print);//1~99까지 출력됨.
		System.out.println();
		
		
		//스트림서킷 메소드인 takeWhile을 이용해도 중단시킬 수 있음 
		System.out.println("===== takeWhile이용하기 ====");
		Stream.iterate(0,n->n+1).takeWhile(n->n<100).forEach(System.out::print);
		System.out.println();
		
		
		
		
		
		System.out.println("===== 배열을 스트림전환하기 =====");
		//배열을 Arrays.stream() static매소드를 이용해서 배열을 스트림으로 전환하여 데이터를 관리할 수 있다.
		//기본자료형은 별도의 스트림클래스로 관리를 함. 각 자료형별로 스트림을 가지고있음 IntStream, Double, LongStream등이 있음
		//기본자료형배열은 그 해당자료형스트림으로 반환된다. 반환되는것을 기본Stream으로 반환하려면 boxed()매소드를 이용하면됨.
		int[] intArr= {10,220,3,40,50,10,5,4,7,100};
		Stream<Integer> suStream=Arrays.stream(intArr).boxed();
		suStream.forEach(System.out::print);
		System.out.println();
		double[] dArr= {5.12,6.22,6.12,10.55,12.66};
		Stream<Double> dStream=Arrays.stream(dArr).boxed();
		dStream.forEach(System.out::print);
		System.out.println();
		
		//기본자료형이 아닌 객체배열은 boxed()메소드를 이용하지 않고 사용할 수 있음.
		String[] names= {"홍길동","김길동","장길동","최길동"};
		Stream<String> namesStream=Arrays.stream(names);
		namesStream.forEach(System.out::print);
		System.out.println();
		//활용할 데이터 랜덤으로 생성하기
		Student[] students= {
					new Student.Builder().stdNo(1).name("test1").age((int)(Math.random()*50+10)).
					gender(1%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("주소1").build(), 
					new Student.Builder().stdNo(2).name("test2").age((int)(Math.random()*50+10)).
					gender(2%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("주소2").build(), 
					new Student.Builder().stdNo(3).name("test3").age((int)(Math.random()*50+10)).
					gender(3%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("주소3").build(),
					new Student.Builder().stdNo(4).name("test4").age((int)(Math.random()*50+10)).
					gender(4%2==0?Student.Gender.M:Student.Gender.F)
					.height(Math.round(((Math.random()*(190-150))+150)*100/100.0))
					.grade((int)(Math.random()*3+1)).address("주소4").build()
					};
		
		Stream<Student> studentStream=Arrays.stream(students);
		studentStream.forEach(System.out::println);
		
		
		System.out.println("===== Collection을 이용해서 스트림만들기 =====");
		//collection에 있는 데이터를 가지고 stream만들기
		//자바8에서 collection객체에 stream()메소드를 가지고 있으며, 
		//그 매소드를 이용해서 간편하게 collection을 가지고 stream을 만들 수 있다.
		List<Student> studentList=StudentDao.getStudents();
		Set<Student> studentSet=new HashSet(StudentDao.getStudents());
		Map<Integer,Student> studentMap=StudentDao.getStudentMap();
		
		System.out.println("===== List Student =====");
		Stream<Student> listStream=studentList.stream();
		listStream.limit(5).forEach(System.out::println);
		System.out.println("===== Set Student =====");
		Stream<Student> setStream=studentSet.stream();
		setStream.limit(5).forEach(System.out::println);
		System.out.println("===== Map Student =====");
		//map에는 stream이 존재하지 않음. 
		//studentMap.stream().forEach(System.out::println);
		//Map은 Collection객체로 변환하여 사용해야함.
		new ArrayList(studentMap.values()).stream().limit(5).forEach(System.out::println);
		
		System.out.println("===== csv방식으 데이터 stream으로 저장하기 =====");
		//csv방식으로 넘어오 데이터 분할해서 stream으로 저장하기
		String csvData="유병승,19,경기도시흥시,남,180.5,64.5";
		//Patter클래스를 이용해서 파싱해서 처리할 수 있다.
		Stream<String> csvStream=Pattern.compile(",").splitAsStream(csvData);
		csvStream.forEach(s->System.out.print(s+" "));
		System.out.println();
		
		//nio패키지에 있는 File클래스를이용해서 데이터 가져오기
		//파일에 저장된 문자열 데이터를 문자열로 가져오기
		//noi패키지의 Files클래스의 lines매소드를 이용하면 stream<String>클래스를 반환한다. 
		try(Stream<String> filestream=Files.lines(Paths.get("filetest.txt"),Charset.defaultCharset());) {
			//개행을 기준으로 문자열 출력하기
			//filestream.forEach(s->System.out.println(s));
			//문자의 갯수 출력하기
			long count=filestream.flatMap(s->Arrays.stream(s.split(""))).count();
			System.out.println(count);
					
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}










package com.streamtest.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

//Collector를 세개의 제네릭타입을 갖는다, 첫번째는 데이터타입, 두번째는 누적, 세번째는 최종 반환타입
public class ToMyListCollect<T> implements Collector<T, List<T>, List<T>>{

	@Override
	public Supplier<List<T>> supplier() {
		// TODO Auto-generated method stub
		return ArrayList::new;//컨테이너 생성하기
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		// TODO Auto-generated method stub
		return List::add;//Stream에서 전달되는값을 추가하는 메소드 지정
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		// TODO Auto-generated method stub
		return (preList,nextList)-> {
			preList.addAll(nextList); 
			return preList;
		};
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		// TODO Auto-generated method stub
		return Function.identity();
	}

	@Override
	public Set<Characteristics> characteristics() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH,Characteristics.CONCURRENT));
	}
	
	
}

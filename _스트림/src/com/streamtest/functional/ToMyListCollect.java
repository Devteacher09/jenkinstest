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

//Collector�� ������ ���׸�Ÿ���� ���´�, ù��°�� ������Ÿ��, �ι�°�� ����, ����°�� ���� ��ȯŸ��
public class ToMyListCollect<T> implements Collector<T, List<T>, List<T>>{

	@Override
	public Supplier<List<T>> supplier() {
		// TODO Auto-generated method stub
		return ArrayList::new;//�����̳� �����ϱ�
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		// TODO Auto-generated method stub
		return List::add;//Stream���� ���޵Ǵ°��� �߰��ϴ� �޼ҵ� ����
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

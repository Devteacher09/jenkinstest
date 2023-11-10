package com.streamtest.model.vo;


public class Student {
	
	public static enum Gender{M,F,T};
	private long stdNo;
	private String name;
	private int age;
	private double height;
	private Gender gender;
	private int grade;
	private String address;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String name, int age, Gender gender, int grade, String address) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.grade = grade;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	public long getStdNo() {
		return stdNo;
	}

	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}

	public Double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height=height;
	}
	
	
	@Override
	public String toString() {
		return "Student [stdNo=" + stdNo + ", name=" + name + ", age=" + age + ", height=" + height + ", gender="
				+ gender + ", grade=" + grade + ", address=" + address + "]";
	}



	//builder���� �����ϱ�
	//����Ŭ������ �̿��ؼ� ó���ϱ�
	public static class Builder{//�ܺο��� Ŭ���������� ������ �� �ֵ��� static���� ����.
		//������ ������� ����
		private String name;
		private int age;
		private double height;
		private Gender gender;
		private int grade;
		private String address;
		private long stdNo;
		//�ʼ����� �ִ� ��� �Ű����� �ִ� �����ڸ� ���� ������.
//		public Builder(String name, int age) {
//			this.name=name;
//			this.age=age;
//		}
		public Builder name(String val) {
			name=val;
			return this;
		}
		//������ �������� �żҵ带 �̿��ؼ� ó��
		public Builder age(int val) {
			age=val;
			return this;
		}
		public Builder gender(Gender val) {
			gender=val;
			return this;
		}
		public Builder grade(int val) {
			grade=val;
			return this;
		}
		public Builder address(String val) {
			address=val;
			return this;
		}
		
		public Builder stdNo(long val) {
			stdNo=val;
			return this;
		}
		public Builder height(double val) {
			height=val;
			return this;
		}
		public Student build() {
			return new Student(this);
		}
	}
	private Student(Builder builder) {
		this.name=builder.name;
		this.age=builder.age;
		this.height=builder.height;
		this.gender=builder.gender;
		this.grade=builder.grade;
		this.address=builder.address;
		this.stdNo=builder.stdNo;
	}
	
	@Override
	public Student clone() {
		return new Student.Builder().name(name).age(age).address(address).gender(gender).grade(grade).stdNo(stdNo).build();
	}
		
	
}

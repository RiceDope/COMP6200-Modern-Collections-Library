package com.rwalker;

public class ManualTesting {
    public static void main(String[] args) throws NoSuchMethodException{
        Sequence<Student> test = new Sequence<Student>();
        // test.append(new Student(21, "George"));
        // test.append(new Student(20, "Nathan"));
        // test.append(new Student(16, "Ben"));
        // test.append(new Student(17, "Thomas"));
        System.out.println(test.toString());
        test.insert(0, new Student(50, "Dave"));
        System.out.println(test.toString());
    }
}

// implements Comparable<Student>
class Student implements Comparable<Student>{

    private String name;
    private int age;
    
    public Student(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Student other){
        return Integer.compare(getAge(), other.getAge());
    }

    @Override
    public String toString(){
        return "Age " + age + " Name " + name + " "; 
    }

}
package org.hope6537.note.tij.fifteen;

class ClassAssFactory<T> {
    T x;

    public ClassAssFactory(Class<T> kind) {
        try {
            x = kind.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public T getX() {
        return x;
    }
}

class Employee {
    @Override
    public String toString() {
        return "Employee []";
    }
}

public class InstantiateGenericType {
    public static void main(String[] args) {
        ClassAssFactory<Employee> fe = new ClassAssFactory<Employee>(Employee.class);
        System.out.println(fe.getX().toString());
    }
}

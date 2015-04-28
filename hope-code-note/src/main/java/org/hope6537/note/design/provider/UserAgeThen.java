package org.hope6537.note.design.provider;

/**
 */
public class UserAgeThen extends CompositeSpecification {

    private int age;

    public UserAgeThen(int age) {
        this.age = age;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getAge() > age;
    }
}

package org.hope6537.note.design.provider;

/**
 */
public interface UserSpecification {

    public boolean isSatisfiedBy(User user);

    public UserSpecification and(UserSpecification specification);

    public UserSpecification or(UserSpecification specification);

    public UserSpecification not();

}

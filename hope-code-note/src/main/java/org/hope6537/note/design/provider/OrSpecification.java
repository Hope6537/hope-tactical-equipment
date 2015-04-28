package org.hope6537.note.design.provider;

/**
 */
public class OrSpecification extends CompositeSpecification {

    private UserSpecification left;
    private UserSpecification right;

    public OrSpecification(UserSpecification left, UserSpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) || right.isSatisfiedBy(user);
    }

}

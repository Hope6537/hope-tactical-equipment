package org.hope6537.note.design.provider;

/**
 */
public class AndSpecification extends CompositeSpecification {

    private UserSpecification left;
    private UserSpecification right;

    public AndSpecification(UserSpecification left, UserSpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) && right.isSatisfiedBy(user);
    }

}

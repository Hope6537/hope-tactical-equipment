package org.hope6537.note.design.provider;

/**
 */
public class NotSpecification extends CompositeSpecification {

    private UserSpecification spec;

    public NotSpecification(UserSpecification spec) {
        this.spec = spec;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return !spec.isSatisfiedBy(user);
    }

}

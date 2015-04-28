package org.hope6537.note.design.provider;

/**
 */
public abstract class CompositeSpecification implements UserSpecification {

    @Override
    public UserSpecification and(UserSpecification specification) {
        return new AndSpecification(this, specification);
    }

    @Override
    public UserSpecification or(UserSpecification specification) {
        return new OrSpecification(this, specification);
    }

    @Override
    public UserSpecification not() {
        return new NotSpecification(this);
    }
}

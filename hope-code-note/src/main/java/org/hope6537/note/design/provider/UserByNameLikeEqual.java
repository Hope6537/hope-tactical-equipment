package org.hope6537.note.design.provider;

/**
 */
public class UserByNameLikeEqual extends CompositeSpecification {

    public static final String LIKE_FLAG = "%";

    private String likeStr;

    public UserByNameLikeEqual(String likeStr) {
        this.likeStr = likeStr;
    }


    @Override
    public boolean isSatisfiedBy(User user) {
        boolean result;
        String name = user.getName();
        String str = likeStr.replace(LIKE_FLAG, "");
        if (likeStr.endsWith(LIKE_FLAG) && !likeStr.startsWith(LIKE_FLAG)) {
            result = name.startsWith(str);
        } else if (likeStr.startsWith(LIKE_FLAG) && likeStr.endsWith(LIKE_FLAG)) {
            result = name.endsWith(str);
        } else {
            result = name.contains(str);
        }
        return result;
    }
}

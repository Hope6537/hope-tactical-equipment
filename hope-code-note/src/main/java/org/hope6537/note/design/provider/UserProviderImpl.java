package org.hope6537.note.design.provider;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class UserProviderImpl implements UserProvider {

    private List<User> userList;


    public UserProviderImpl(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public List<User> findUser(UserSpecification condition) {
        return userList.stream().filter(condition::isSatisfiedBy).collect(Collectors.toList());
    }
}

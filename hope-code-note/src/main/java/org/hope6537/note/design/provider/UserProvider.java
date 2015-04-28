package org.hope6537.note.design.provider;

import java.util.List;

/**
 */
public interface UserProvider {

    public List<User> findUser(UserSpecification condition);

    public void setUserList(List<User> userList);

    public List<User> getUserList();


}

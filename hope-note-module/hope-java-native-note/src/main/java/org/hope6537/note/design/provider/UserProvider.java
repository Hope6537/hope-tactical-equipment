package org.hope6537.note.design.provider;

import java.util.List;

/**
 */
public interface UserProvider {

    public List<User> findUser(UserSpecification condition);

    public List<User> getUserList();

    public void setUserList(List<User> userList);


}

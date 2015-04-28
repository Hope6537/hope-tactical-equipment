package org.hope6537.note.design.provider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Client {

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Ace", 11));
        userList.add(new User("Bob", 12));
        userList.add(new User("Charlie", 13));
        userList.add(new User("Delta", 14));
        userList.add(new User("Echo", 15));
        userList.add(new User("Fox", 16));
        userList.add(new User("Group", 17));

        UserProvider userProvider = new UserProviderImpl(userList);
        UserSpecification userSpecification = new UserAgeThen(15);
        userProvider.findUser(userSpecification).forEach(System.out::println);

        UserSpecification userSpecification1 = new UserByNameLike("%Echo%");
        userProvider.findUser(userSpecification1).forEach(System.out::println);

        //原复合查询
        /*System.out.println("====");
        UserSpecification specification1 = new UserByNameLike("%Echo%");
        UserSpecification specification2 = new UserAgeThen(12);
        userList = userProvider.findUser(specification1);
        userProvider.setUserList(userList);
        userProvider.findUser(specification2).forEach(System.out::println);*/

        System.out.println("====");
        UserSpecification spec1 = new UserAgeThen(14);
        UserSpecification spec2 = new UserByNameLike("%Fox%");
        userProvider.findUser(spec1.and(spec2)).forEach(System.out::println);

    }

}

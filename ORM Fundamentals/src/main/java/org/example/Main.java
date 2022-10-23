package org.example;

import org.example.models.User;
import org.example.orm.EntityManager;
import org.example.orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.example.orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("root", "", "custom-orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
        userEntityManager.doCreate(User.class);

        User user1 = new User("Test1", "myP@ssword123", 21, LocalDate.now());
        User user2 = new User("Test2", "myP@ssword123", 22, LocalDate.now());
        User user3 = new User("Test3", "myP@ssword123", 23, LocalDate.now());

        userEntityManager.persist(user1);
        userEntityManager.persist(user2);
        userEntityManager.persist(user3);

        for (User user : userEntityManager.find(User.class)) {
            System.out.printf("%s - %s ; ", user.getId(), user.getUsername());
        }
        System.out.println();

        User userToBeDeleted = userEntityManager.findFirst(User.class, "`age` = 22");

        userEntityManager.doDelete(userToBeDeleted);

        for (User user : userEntityManager.find(User.class)) {
            System.out.printf("%s - %s ; ", user.getId(), user.getUsername());
        }
    }
}
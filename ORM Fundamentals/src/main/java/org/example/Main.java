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

        User user = new User("Test", "myP@ssword123", 20, LocalDate.now());

        userEntityManager.persist(user);

        user.setUsername("John12");
        userEntityManager.persist(user);

        User johnUserObject = userEntityManager.findFirst(User.class, "`username` LIKE 'John12'");

        System.out.println(johnUserObject.getUsername());
    }
}
package org.example.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DBContext<E> {
    boolean persist(E entity) throws IllegalAccessException, SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    Iterable<E> find(Class<E> table, String where) throws NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException;

    E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}

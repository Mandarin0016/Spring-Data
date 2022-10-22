package org.example.orm;

import org.example.annotations.Column;
import org.example.annotations.Entity;
import org.example.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DBContext<E> {

    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getIdColumn(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        String tableName = getTableName(entity.getClass());

        if (value == null || (long) value <= 0) {
            return doInsert(entity, primaryKey, tableName);
        }

        return doUpdate(entity, primaryKey, tableName);
    }

    private boolean doUpdate(E entity, Field primaryKey, String tableName) throws SQLException, IllegalAccessException {
        String newValues = getNewValues(getDBFieldsWithoutIdentity(entity), getEntityValuesWithoutIdentity(entity));

        String sql = String.format("""
                UPDATE %s
                SET %s
                WHERE `id` = %s;
                """, tableName, newValues, primaryKey.get(entity));

        return connection.prepareStatement(sql).execute();
    }

    private String getNewValues(String dbFieldsWithoutIdentity, String entityValuesWithoutIdentity) {
        String[] fields = dbFieldsWithoutIdentity.split(",");
        String[] values = entityValuesWithoutIdentity.split(",");
        List<String> newValueSet = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            newValueSet.add(String.format("%s = %s", fields[i], values[i]));
        }
        return String.join(",", newValueSet);
    }

    private String getEntityValuesWithoutIdentity(E entity) throws IllegalAccessException {
        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)).toList();
        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            values.add(String.format("\"%s\"", field.get(entity).toString()));
        }
        return String.join(",", values);
    }

    private String getDBFieldsWithoutIdentity(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(Column.class) && !f.isAnnotationPresent(Id.class)).map(field -> field.getAnnotation(Column.class).name()).collect(Collectors.joining(","));
    }

    private boolean doInsert(E entity, Field pk, String tableName) throws IllegalAccessException, SQLException {
        String dbFields = getDBFieldsWithoutIdentity(entity);
        String values = getEntityValuesWithoutIdentity(entity);

        String sql = String.format("INSERT INTO %s(%s) VALUES (%s);", tableName, dbFields, values);
        setEntityId(entity, pk, tableName);

        return connection.prepareStatement(sql).execute();
    }

    private void setEntityId(E entity, Field pk, String tableName) throws SQLException, IllegalAccessException {
        Long id = getLastIdInserted(tableName) + 1;
        pk.set(entity, id);
    }

    private Long getLastIdInserted(String tableName) throws SQLException {
        String sql = String.format("""
                SELECT max(`id`)
                FROM `%s`;
                """, tableName);
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    @Override
    public Iterable<E> find(Class<E> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(clazz);

        String query = String.format("""
                SELECT * FROM %s;
                """, tableName);

        return getEntities(clazz, query);
    }

    @Override
    public Iterable<E> find(Class<E> clazz, String where) throws NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tableName = getTableName(clazz);

        String query = String.format("""
                SELECT * FROM %s %s;
                """, tableName, where != null ? " WHERE " + where : "");

        return getEntities(clazz, query);
    }

    private Iterable<E> getEntities(Class<E> clazz, String query) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        Collection<E> models = new ArrayList<>();

        while (resultSet.next()) {
            E entity = clazz.getDeclaredConstructor().newInstance();
            fillEntity(clazz, resultSet, entity);
            models.add(entity);
        }

        return models;
    }

    @Override
    public E findFirst(Class<E> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(clazz);

        String query = String.format("""
                SELECT * FROM %s LIMIT 1;
                """, tableName);

        ResultSet resultSet = connection.prepareStatement(query).executeQuery();

        E entity = clazz.getDeclaredConstructor().newInstance();

        resultSet.next();
        fillEntity(clazz, resultSet, entity);

        return entity;
    }

    @Override
    public E findFirst(Class<E> clazz, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(clazz);

        String query = String.format("""
                SELECT * FROM %s %s LIMIT 1;
                """, tableName, where != null ? " WHERE " + where : "");

        ResultSet resultSet = connection.prepareStatement(query).executeQuery();

        E entity = clazz.getDeclaredConstructor().newInstance();

        resultSet.next();
        fillEntity(clazz, resultSet, entity);

        return entity;
    }

    private void fillEntity(Class<E> clazz, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] fields = Arrays.stream(clazz.getDeclaredFields()).toArray(Field[]::new);

        for (Field field : fields) {
            field.setAccessible(true);
            fillField(field, resultSet, entity);
        }
    }

    private void fillField(Field field, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(entity, resultSet.getInt(getDbAttributeName(field)));
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            field.set(entity, resultSet.getLong(getDbAttributeName(field)));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(resultSet.getString(getDbAttributeName(field))));
        } else {
            field.set(entity, resultSet.getString(getDbAttributeName(field)));
        }
    }

    private String getDbAttributeName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();

    }

    private Field getIdColumn(Class<?> entity) {
        return Arrays.stream(entity.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));

    }

    private String getTableName(Class<?> entity) {
        Entity annotation = entity.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new UnsupportedOperationException("Provided class does not have entity exception.");
        }

        return annotation.name();
    }
}

package org.example._03_university;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _03_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

    }
}
package org.example._04_hospital;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _04_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

    }
}
package org.example._05_Bills;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _05_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

    }
}
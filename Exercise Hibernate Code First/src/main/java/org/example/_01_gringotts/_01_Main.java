package org.example._01_gringotts;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _01_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

    }
}
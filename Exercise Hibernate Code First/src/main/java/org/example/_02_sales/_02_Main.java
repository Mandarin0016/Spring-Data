package org.example._02_sales;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _02_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("h_code_first_ex");

    }
}
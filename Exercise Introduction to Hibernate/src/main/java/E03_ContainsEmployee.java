import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Scanner;

public class E03_ContainsEmployee {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        String name = scanner.nextLine();

        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Employee e WHERE CONCAT(e.firstName, ' ',e.lastName) =:givenName", Long.class);
        query.setParameter("givenName", name);

        Long employeeCount = query.getSingleResult();

        System.out.println(employeeCount == 0 ? "No" : "Yes");

        entityManager.close();
    }
}

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class E11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        String prefix = scanner.nextLine();

        entityManager.createQuery("""
                        SELECT e FROM Employee e
                        WHERE LOWER(e.firstName)
                        LIKE :pattern
                        """, Employee.class)
                .setParameter("pattern", prefix + "%")
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }
}

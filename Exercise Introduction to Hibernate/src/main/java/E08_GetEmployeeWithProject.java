import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class E08_GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner console = new Scanner(System.in);
        int id = Integer.parseInt(console.nextLine());

        Employee employee = entityManager.find(Employee.class, id);

        System.out.println(employee);

        entityManager.close();
    }
}

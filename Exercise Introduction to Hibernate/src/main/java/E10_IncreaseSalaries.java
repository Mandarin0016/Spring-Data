import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class E10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Employee> employees = entityManager.createQuery("""
                SELECT e
                FROM Employee e
                WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')
                """, Employee.class).getResultList();

        entityManager.getTransaction().begin();

        employees.forEach(employee -> {
            employee.setSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() * 1.12));
            entityManager.persist(employee);
            System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
        });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

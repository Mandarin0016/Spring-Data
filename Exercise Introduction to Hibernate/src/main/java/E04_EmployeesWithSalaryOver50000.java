
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class E04_EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<String> employeeQuery = entityManager.createQuery("""
                SELECT e.firstName FROM Employee e WHERE e.salary > 50000
                """, String.class);

        employeeQuery.getResultList().forEach(System.out::println);

        entityManager.close();
    }
}

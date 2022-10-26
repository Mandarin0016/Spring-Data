import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class E12_EmployeesMaximumSalaries {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager
                .createQuery("SELECT CONCAT(e.department.name, ' ', MAX(e.salary)) " +
                        "FROM Employee e " +
                        "GROUP BY e.department.id " +
                        "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", String.class)
                .getResultList()
                .forEach(System.out::println);
    }
}

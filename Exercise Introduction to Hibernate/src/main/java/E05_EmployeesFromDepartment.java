import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class E05_EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<String> employeeInformation = entityManager.createQuery("""
                SELECT CONCAT(e.firstName, ' ', e.lastName, ' from Research and Development - $', e.salary ) 
                FROM Employee e
                WHERE e.department.name = 'Research and Development'
                ORDER BY e.salary ASC, e.id ASC
                """, String.class).getResultList();

        employeeInformation.forEach(System.out::println);

        entityManager.close();
    }
}

import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class E06_AddingNewAddressAndUpdatingEmployee {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        String employeeLastName = scanner.nextLine();

        entityManager.getTransaction().begin();

        Address address = new Address();
        address.setText("Vitoshka 15");
        entityManager.persist(address);

        entityManager.createQuery("""
                        UPDATE Employee e 
                        SET e.address = :givenAddress
                        WHERE e.lastName = :givenLastName
                        """)
                .setParameter("givenAddress", address)
                .setParameter("givenLastName", employeeLastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

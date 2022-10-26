import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class E13_RemoveTowns {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner console = new Scanner(System.in);

        String town = console.nextLine();

        List<Address> addresses = entityManager.createQuery("""
                SELECT a
                FROM Address a
                WHERE a.town.name = :town
                """, Address.class).setParameter("town", town).getResultList();

        entityManager.getTransaction().begin();

        addresses.forEach(a -> {
            a.getEmployees().forEach(e -> {
                e.setAddress(null);
                entityManager.persist(e);
            });
            entityManager.remove(a);
        });

        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        entityManager.createQuery("""
                DELETE FROM Town t
                WHERE t.name = :givenName
                """).setParameter("givenName", town).executeUpdate();

        System.out.printf("%d %s in %s deleted", addresses.size(), addresses.size() == 1 ? "address" : "addresses", town);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

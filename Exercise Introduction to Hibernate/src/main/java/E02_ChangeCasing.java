
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class E02_ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Town> allTowns = entityManager
                .createQuery("SELECT a FROM Town a", Town.class).getResultList();

        entityManager.getTransaction().begin();

        for (Town town : allTowns) {
            if (town.getName().length() <= 5) {
                town.setName(town.getName().toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

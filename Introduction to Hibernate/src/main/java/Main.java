import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Student student1 = new Student("Test1", 2, "test@gmail.com");
        entityManager.persist(student1);

        Student student = new Student("Emo", 19);
        entityManager.persist(student);

        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
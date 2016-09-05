import javax.persistence.*;

@Embeddable
public class DBHelper {
    @Embedded
    private EntityManagerFactory emFactory;
    @Embedded
    private EntityManager em;

    public DBHelper() {
        emFactory = Persistence.createEntityManagerFactory("DB");
        em = emFactory.createEntityManager();
    }

    public boolean persistInATransaction(Object o) {
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

        try {
            em.persist(o);
            entityTransaction.commit();
        } catch (Exception e) {
            System.out.println("Failed transaction: " + e.toString());
            entityTransaction.rollback();
            return false;
        }

        return true;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void close() {
        em.close();
        emFactory.close();
    }
}

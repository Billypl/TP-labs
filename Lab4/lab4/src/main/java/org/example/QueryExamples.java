package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class QueryExamples {

    public static EntityManagerFactory entityManagerFactory;

    public static List<Mage> getMagsWithPowerGraterThan(int power) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> mags = entityManager
                .createQuery("SELECT m FROM Mage m WHERE m.level > :power", Mage.class)
                .setParameter("power", power)
                .getResultList();
        entityManager.close();
        return mags;
    }

    public static List<Tower> getTowersLowerThan(int height) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> towers = entityManager
                .createQuery("SELECT t FROM Tower t WHERE t.height < :height", Tower.class)
                .setParameter("height", height)
                .getResultList();
        entityManager.close();
        return towers;
    }

    public static List<Mage> getGreatestMageOfEachTower() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> mags = entityManager
                .createQuery( "SELECT m FROM Mage m WHERE (m.tower, m.level) IN " +
                        "(SELECT m2.tower, MAX(m2.level) FROM Mage m2 GROUP BY m2.tower)", Mage.class)
                .getResultList();
        entityManager.close();
        return mags;
    }
}

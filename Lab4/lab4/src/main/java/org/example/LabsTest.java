package org.example;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class LabsTest {

    private final EntityManagerFactory entityManagerFactory;

    public LabsTest() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PT-lab");
        QueryExamples.entityManagerFactory = entityManagerFactory;
    }

    public void run() {
        createMagsAndTowers();
        printAll();

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.print("Enter command (exit, remove, show, add, queries): ");
            String line = scanner.nextLine();

            if (line.equals("exit")) {
                break;
            }
            if (line.equals("remove")) {
                System.out.println("Provide type (M/T) and name:");
                line = scanner.nextLine();
                String[] parameters = line.split(" ");
                String typeToRemove = parameters[0];
                String name = parameters[1];
                if (typeToRemove.equalsIgnoreCase("M")) {
                    remove(Mage.class, name);
                } else if (typeToRemove.equalsIgnoreCase("T")) {
                    remove(Tower.class, name);
                }
            } else if (line.equals("add")) {
                System.out.print("Provide type (M/T) - mage (name, power, tower name) | tower (name, height): ");
                line = scanner.nextLine();
                String[] params = line.split(" ");
                String type = params[0];
                if (type.equalsIgnoreCase("M")) {
                    String name = params[1];
                    String power = params[2];
                    String towerName = params[3];
                    addMage(name, Integer.parseInt(power), towerName);
                } else if (type.equalsIgnoreCase("T")) {
                    String name = params[1];
                    int power = Integer.parseInt(params[2]);
                    addTower(new Tower(name, power));
                }
            } else if (line.equals("show")) {
                printAll();
            } else if (line.equals("queries")) {
                System.out.println("### QUERY MAGS GREATER THAN ###");
                QueryExamples.getMagsWithPowerGraterThan(44)
                        .forEach(mage -> System.out.println("\t" + mage.toString()));
                System.out.println("### QUERY TOWERS LOWER THAN ###");
                QueryExamples.getTowersLowerThan(31)
                        .forEach(tower -> System.out.println("\t" + tower.toString()));
                System.out.println("### QUERY GREATEST MAGE FORM EACH TOWER ###");
                QueryExamples.getGreatestMageOfEachTower()
                        .forEach(mage -> System.out.println("\t" + mage.toString()));
            }
        }
        scanner.close();
        entityManagerFactory.close();
    }

    private void createMagsAndTowers() {
        Tower t1 = new Tower("TwoTowers", 0);
        Tower t2 = new Tower("Lighthouse", 69);
        Tower t3 = new Tower("BigBen", 30);
        Mage m1 = new Mage("Stefan", 11, t1);
        Mage m2 = new Mage("Adam", 22, t1);
        Mage m3 = new Mage("Andrzej", 33, t2);
        Mage m4 = new Mage("Rafał", 44, t2);
        Mage m5 = new Mage("Michał", 55, t3);
        Mage m6 = new Mage("Mateusz", 66, t3);
        Mage m7 = new Mage("Kobieta", 77, t3);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(t3);
        entityManager.persist(m1);
        entityManager.persist(m2);
        entityManager.persist(m3);
        entityManager.persist(m4);
        entityManager.persist(m5);
        entityManager.persist(m6);
        entityManager.persist(m7);
        transaction.commit();

        entityManager.close();
    }

    private void printAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "SELECT m FROM Mage m";
        Query query = entityManager.createQuery(queryString);

        List<Mage> products = query.getResultList();
        products.forEach(mage -> System.out.println("\t" + mage.toString()));
        entityManager.close();
    }

    private <T> void remove(Class<T> type, String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Object toRemove = entityManager
                .createQuery("SELECT t FROM " + type.getSimpleName() + " t WHERE t.name LIKE :name")
                .setParameter("name", name)
                .getSingleResult();
        entityManager.remove(toRemove);

        transaction.commit();
        entityManager.close();
    }

    private void addTower(Tower tower) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(tower);

        transaction.commit();
        entityManager.close();
    }

    private void addMage(String name, int power, String towerName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Tower mageTower = (Tower) entityManager
                .createQuery("SELECT t FROM Tower t WHERE t.name LIKE :name")
                .setParameter("name", towerName)
                .getResultList().getFirst();
        Mage mage = new Mage(name, power, mageTower);

        entityManager.persist(mage);

        transaction.commit();
        entityManager.close();
    }
}


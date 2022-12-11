import model.CandyBar;
import model.Hall;
import model.Sweet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//     View view = new View();
//     view.runProgram();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        Hall hall1 = new Hall("Sala", "ddesc", "location", 10);
        Hall hall2 = new Hall("Sala2", "ddesc2", "location2", 110);
        Hall hall3 = new Hall("Sala3", "ddesc3", "location3", 103);

        List<Sweet> sweets = new ArrayList<>();
        Sweet sweet1 = new Sweet("bomboane");
        Sweet sweet2 = new Sweet("prajituri");
        Sweet sweet3 = new Sweet("ciocolata");

        sweets.add(sweet1);
        sweets.add(sweet2);
        List<Sweet> sweets1 = new ArrayList<>();
        sweets1.add(sweet3);
        sweets1.add(sweet1);
        CandyBar candyBar1 = new CandyBar("Candybar1", "descriere Candy", sweets);
        CandyBar candyBar2 = new CandyBar("Candybar2", "descriere Candy2", sweets1);


        manager.persist(hall1);
        manager.persist(hall2);
        manager.persist(hall3);
        manager.persist(candyBar1);
        manager.persist(candyBar2);

        manager.getTransaction().commit();
        manager.close();
        factory.close();

    }
}
package tests.Controller;//package tests;

import Controller.BusinessOwnerController;
import Controller.OrganiserController;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.inMemory.BusinessOwnerInMemoryRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOwnerControllerTest {

    private BusinessOwner businessOwner;
    private Organiser organiser;
    private Hall hall;

    /*private Product product=new Process()
    private Hall hall = new Hall("name", "description", "location", 1000);
    private Message message = new Message(hall, "12-12-2022", "13-12-2022", 500, "description");
    private Offer offer = new Offer(hall, "12-12-2022", "13-12-2022", 500, "description", 1000);*/

    @BeforeEach
    void setUp() {

        businessOwner= new BusinessOwner("firstnameBO", "lastnameBO", "businessOwner", "1234");
        BusinessOwnerController.getInstance().setBusinessOwner(businessOwner);
        organiser= new Organiser("firstnameORG", "lastnameORG", "organiser", "5678");
        OrganiserController.getInstance().setOrganiser(organiser);
        hall = new Hall("sala0", "very big", "Cluj-Napoca", 150);

    }

    @Test
    void checkNewMessages() {

        System.out.println("CheckNewMessages Test\n");
        assertTrue(BusinessOwnerController.getInstance().checkNewMessages()); //lista e goala, deci ret true

        Message message = new Message(hall, organiser, businessOwner, "12.12.2022", "13.12.2022", 12, "descr1");

        businessOwner.getReceivedMessages().add(message);
        assertFalse(BusinessOwnerController.getInstance().checkNewMessages()); //lista nu e goala, deci ret false
//        for(Message message1 : businessOwner.getReceivedMessages()) {
//            message1.setStatus(Status.ACCEPTED);
//        }
//        assertTrue(BusinessOwnerController.getInstance().checkNewMessages());

    }

    @Test
    void checkSentOffers() {

        System.out.println("CheckSentOffers Test\n");
        assertTrue(BusinessOwnerController.getInstance().checkSentOffers()); //momentan nu a trim nicio oferta, deci lista e goala => ret true

        Offer offer1= new Offer(businessOwner,organiser,hall,10000,"descr2");

        businessOwner.getSentOffers().add(offer1);
        assertFalse(BusinessOwnerController.getInstance().checkSentOffers());
    }

    @Test
    void createProduct() {
        System.out.println("CreatedProduct Test\n");

        assertFalse(businessOwner.getProducts().contains(hall));//lista de produse a b.o e goala => nu contine sala, deci false

        BusinessOwnerController.getInstance().createProduct(hall);
        assertTrue(businessOwner.getProducts().contains(hall));

        Hall hall1=new Hall("sala1", "small", "Sibiu", 50);
        assertFalse(businessOwner.getProducts().contains(hall1));

    }

    @Test
    void deleteProduct() {
        System.out.println("DeletedProduct Test\n");

        BusinessOwnerController.getInstance().createProduct(hall);
        assertTrue(businessOwner.getProducts().contains(hall));
        BusinessOwnerController.getInstance().deleteProduct(hall.getId());
        assertFalse(businessOwner.getProducts().contains(hall));

    }

    @Test
    void modifyProduct() {
        System.out.println("ModifyProduct Test\n");

        Sweet sweet1 = new Sweet("chocolate");
        Sweet sweet2 = new Sweet("haribo");

        List<Sweet> sweets = new ArrayList<>(Arrays.asList(sweet1, sweet2));
        CandyBar candyBar = new CandyBar("name", "description", sweets);
        CandyBar candyBar1 = new CandyBar("newName", "newDescription", sweets);

        BusinessOwnerController.getInstance().createProduct(candyBar);
        assertTrue(businessOwner.getProducts().contains(candyBar));

        BusinessOwnerController.getInstance().modifyProduct(candyBar.getId(), candyBar1);
        assertTrue(businessOwner.getProducts().contains(candyBar1));

//        assertEquals(businessOwner.getAds())
//        assertTrue(businessOwner.getAds().contains(newAd));

    }
//
//    @Test
//    void sort() {
//    }
//
//    @Test
//    void filterByAcceptedMessage() {
//    }
//
//    @Test
//    void filterByDeclinedMessage() {
//    }
//
//    @Test
//    void filterByNumberOfMadeOffers() {
//    }
//
//    @Test
//    void showOffersByRandomBO() {
//    }
//
//    @Test
//    void isBusinessOwnerAd() {
//    }
}
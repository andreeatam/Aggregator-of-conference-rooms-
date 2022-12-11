package tests.Controller;//package tests;
//
//import Controller.BusinessOwnerController;
//import Controller.OrganiserController;
//import model.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repo.inMemory.OrganiserInMemoryRepository;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OrganiserControllerTest {
//
//    private Organiser organiser;
//    private Hall hall = new Hall("name", "description", "location", 1000);
//    private Message message = new Message(hall, "12-12-2022", "13-12-2022", 500, "description");
//    private Offer offer = new Offer(hall, "12-12-2022", "13-12-2022", 500, "description", 1000);
//
//    @BeforeEach
//    void setUp() {
//
//        organiser= new Organiser("firstname", "lastname", "username", "1234");
//        OrganiserController.getInstance().setOrganiser(organiser);
//
//    }
//
//    @Test
//    void checkNewReceivedOffers() {
//
//        System.out.println("CheckNewReceivedOffers Test\n");
//
//        assertTrue(OrganiserController.getInstance().checkNewReceivedOffers());
//        organiser.getReceivedOffers().add(offer);
//        assertFalse(OrganiserController.getInstance().checkNewReceivedOffers());
//    }
//
//    @Test
//    void checkRequestedOffers() {
//
//        System.out.println("CheckRequestedOffers Test\n");
//
//        assertTrue(OrganiserController.getInstance().checkRequestedOffers());
//        organiser.getSentMessages().add(message);
//        assertFalse(OrganiserController.getInstance().checkRequestedOffers());
//    }
//
//    @Test
//    void sort() {
//    }
//
//    @Test
//    void filterByAcceptedOffer() {
//    }
//
//    @Test
//    void filterByDeclinedOffer() {
//    }
//
//    @Test
//    void filterByNumberOfSentMessages() {
//    }
//
//    @Test
//    void showMessagesByRandomOrg() {
//    }
//}
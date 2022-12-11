package tests.Controller;

import Controller.Server;
import org.junit.jupiter.api.Test;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;
import repo.inMemory.ProductsInMemoryRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void login() {
        System.out.println("Login Tests\n");
        ArrayList<String> credentials1 = new ArrayList<>(Arrays.asList("1", "paulstefan002", "9082"));
        ArrayList<String> credentials2 = new ArrayList<>(Arrays.asList("1", "wrongUsername", "9082"));
        ArrayList<String> credentials3 = new ArrayList<>(Arrays.asList("1", "paulstefan002", "wrongPassword"));
        ArrayList<String> credentials4 = new ArrayList<>(Arrays.asList("2", "raulstefan002", "1234"));
        ArrayList<String> credentials5 = new ArrayList<>(Arrays.asList("2", "wrongUsername", "9082"));
        ArrayList<String> credentials6 = new ArrayList<>(Arrays.asList("2", "paulstefan002", "wrongPassword"));
        ArrayList<String> credentials7 = new ArrayList<>(Arrays.asList("-1", "paulstefan002", "wrongPassword"));

        assertTrue(Server.getInstance().login(credentials1));
        assertFalse(Server.getInstance().login(credentials2));
        assertFalse(Server.getInstance().login(credentials3));
        assertTrue(Server.getInstance().login(credentials4));
        assertFalse(Server.getInstance().login(credentials5));
        assertFalse(Server.getInstance().login(credentials6));
        assertFalse(Server.getInstance().login(credentials7));

    }

    @Test
    void signUp() {

        System.out.println("SignUp Tests\n");
        ArrayList<String> credentials1 = new ArrayList<>(Arrays.asList("1", "firstname1", "lastname1", "username1","9082"));
        ArrayList<String> credentials2 = new ArrayList<>(Arrays.asList("2", "firstname2", "lastname2", "username2", "1234"));
        ArrayList<String> credentials3 = new ArrayList<>(Arrays.asList("-1", "firstname-1", "lastname-1", "username-1", "1234"));

        assertTrue(Server.getInstance().signUp(credentials1));
        assertTrue(Server.getInstance().signUp(credentials2));
        assertFalse(Server.getInstance().login(credentials3));

    }

    @Test
    void getProduct() {

        System.out.println("GetAd Tests\n");

        Integer idProduct1 = 1;
        Integer idProduct2 = 10;

        assertNotEquals(Server.getInstance().getProduct(idProduct1), null);
        assertEquals(Server.getInstance().getProduct(idProduct1), ProductsInMemoryRepository.getInstance().findById(idProduct1));
        assertNull(Server.getInstance().getProduct(idProduct2));

    }

    @Test
    void getBusinessOwner() {

        System.out.println("getBusinessOwner Tests\n");

        String username1 = "paulstefan002";
        String username2 = "raulstefan002";

        assertNotEquals(Server.getInstance().getBusinessOwner(username2), null);
        assertEquals(Server.getInstance().getBusinessOwner(username2), BusinessOwnerInMemoryRepository.getInstance().findById(username2));
        assertNull(Server.getInstance().getBusinessOwner(username1));


    }

    @Test
    void getOrganiser() {

        System.out.println("getOrganiser Tests\n");

        String username1 = "paulstefan002";
        String username2 = "raulstefan002";

        assertNotEquals(Server.getInstance().getOrganiser(username1), null);
        assertEquals(Server.getInstance().getOrganiser(username1), OrganiserInMemoryRepository.getInstance().findById(username1));
        assertNull(Server.getInstance().getOrganiser(username2));
    }
}
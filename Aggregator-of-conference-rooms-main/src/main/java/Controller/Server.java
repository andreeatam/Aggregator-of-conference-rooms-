package Controller;

import interfaces.BusinessOwnerRepositoryInterface;
import interfaces.OrganiserRepositoryInterface;
import interfaces.ProductRepositoryInterface;
import model.BusinessOwner;
import model.Organiser;
import model.Product;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;
import repo.inMemory.ProductsInMemoryRepository;

import java.util.ArrayList;

public class Server {
    private final OrganiserRepositoryInterface organisers = OrganiserInMemoryRepository.getInstance();
    private final BusinessOwnerRepositoryInterface businessOwners = BusinessOwnerInMemoryRepository.getInstance();
    private final ProductRepositoryInterface products = ProductsInMemoryRepository.getInstance();

    private static Server single_instance = null;

    public static Server getInstance() {
        if (single_instance == null){
            single_instance = new Server();
        }
        return single_instance;
    }

    public Server() {
    }

    public boolean login(ArrayList<String> credentials) {

        if (credentials.get(0).equals("1") && organisers.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e org si s-a gasit in bd (are cont)
            return true; //se dechide meniul pt organiser
        }
        if (credentials.get(0).equals("2") && businessOwners.findByUsernameAndPassword(credentials.get(1), credentials.get(2)) != null) { //daca e b.o. si are deja cont
            return true;
        }
       return false;
    }
    public boolean signUp(ArrayList<String> credentials) {
        if(credentials.get(0).equals("1")) { //daca in string ul de credetiale, e organiser
            organisers.add(OrganiserController.getInstance().createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un org si apoi se adauga la acea instanta unica
            return true;
        }
        if (credentials.get(0).equals("2")) { //daca in string ul de credetiale, e b.o
            businessOwners.add(BusinessOwnerController.getInstance().createUser(credentials)); //se creeaza un user cu acele credentiale => se ret un b.o. si apoi se adauga la acea instanta unica
            return true;
        }
        return false;
    }

    public void createBusinessOwnerInController(String username) {
        BusinessOwnerController.getInstance().setBusinessOwner(businessOwners.findById(username));}
    public void createOrganiserInController(String username) {
        OrganiserController.getInstance().setOrganiser(organisers.findById(username));
    }

    public Product getProduct(Integer idProduct) {
        return products.findById(idProduct);
    }

    public BusinessOwner getBusinessOwner(String username) {
        return businessOwners.findById(username);
    }

    public Organiser getOrganiser(String username) {
        return organisers.findById(username);
    }
}

package Controller;

import interfaces.UserControllerInterface;
import model.*;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;
import repo.inMemory.ProductsInMemoryRepository;

import java.util.ArrayList;
import java.util.Collections;

public class BusinessOwnerController implements UserControllerInterface<BusinessOwner, ArrayList<String>> {
    private static BusinessOwnerController single_instance = null;
    private BusinessOwner businessOwner;

    public static BusinessOwnerController getInstance() {
        if (single_instance == null){
            single_instance = new BusinessOwnerController();
        }
        return single_instance;
    }

    public BusinessOwnerController() {
    }

    public BusinessOwner getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(BusinessOwner businessOwner) {
        this.businessOwner = businessOwner;
    }

    @Override
    public BusinessOwner createUser(ArrayList<String> credentials) {
        businessOwner = new BusinessOwner(credentials.get(1), credentials.get(2), credentials.get(3), credentials.get(4));
        return businessOwner;
    }

    public boolean checkNewMessages() {
        return businessOwner.getReceivedMessages().isEmpty(); //daca lista de msj primite e goala => ret 1 (true)
    }
    public boolean checkSentOffers() {
        return businessOwner.getSentOffers().isEmpty(); //daca lista de oferte trim e goala => ret true
    }

    public void createProduct(Product newProduct) {
        businessOwner.getProducts().add(newProduct); //ad in lista de produse a b.o. noul produs
        ProductsInMemoryRepository.getInstance().add(newProduct); //add produsul ca sa fie vzt ca 1 sg instanta alaturi de restul
    }

    // e ok?
    public void deleteProduct(Integer idProduct){
        this.businessOwner.getProducts().removeIf(product -> product.getId().equals(idProduct));
        ProductsInMemoryRepository.getInstance().remove(idProduct);
    }

    // e ok?
    public void modifyProduct(Integer idProduct, Product newProduct) {
        for (Product product : this.businessOwner.getProducts()) {
            if (product.getId().equals(idProduct)) {
                product.setName(newProduct.getName());
                product.setDescription(newProduct.getDescription());
            }
        }
    }

    public void declineMessage(Message message) {

        message.setStatus(Status.DECLINED);
    }


    public void makeOffer(Offer offer, Message message) {
        Organiser organiser = OrganiserInMemoryRepository.getInstance().findOrganiserByMessageId(message.getIdMessage()); //se gaseste org
        organiser.getReceivedOffers().add(offer); //adaugam oferta in lista de oferte primite a org
        businessOwner.getSentOffers().add(offer); //adaugam oferta in lista de oferte trimise a b.o.
        offer.setStatus(Status.SENT); //setam starea ofertei la SENT
        message.setStatus(Status.ACCEPTED); //setam starea msj la ACCEPTED
    }

    //sorteaza lista de b.o. dupa comparatorul de username a b.o.
    public static ArrayList<BusinessOwner> sort(){
        ArrayList<BusinessOwner> allBusinessOwners= BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner();
        Collections.sort(allBusinessOwners, new BusinessOwner());

        return allBusinessOwners;

    }

    //filtrare dupa mesaje acceptate - e ok dat ca param?
    public ArrayList<Message> filterByAcceptedMessage(){
        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
        for(Message message:businessOwner.getReceivedMessages()){
            if(message.getStatus().equals(Status.SENT)){
                filteredMessage.add(message);
                //trb si afisat?
            }
        }
        return filteredMessage;
    }

    //filtrare dupa mesaje respinse
    public ArrayList<Message> filterByDeclinedMessage(){
        ArrayList<Message> filteredMessage=new ArrayList<>(Collections.emptyList());
        for(Message message:businessOwner.getReceivedMessages()){
            if(message.getStatus().equals(Status.DECLINED)){
                filteredMessage.add(message);
                //trb si afisat?
            }
        }
        return filteredMessage;
    }

    //filtrare b.o. dupa nr total de oferte facute; nr total de oferte a unui b.o. trb sa fie >= un nr oarecare
    public ArrayList<BusinessOwner>filterByNumberOfMadeOffers(int NoOffers) {
        ArrayList<BusinessOwner> filteredBusinessOwners = new ArrayList<>(Collections.emptyList());
        for (BusinessOwner b : BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner()) {
            if (b.getSentOffers().size() >= NoOffers) {
                filteredBusinessOwners.add(b);
            }
        }
        return filteredBusinessOwners;
    }

    //se afiseaza toate ofertele ale unui anumit b.o.
    public ArrayList<Offer> showOffersByRandomBO(BusinessOwner b){
        ArrayList<Offer> returnedOffers = new ArrayList<>(Collections.emptyList());
        for (BusinessOwner businessOwner : BusinessOwnerInMemoryRepository.getInstance().getAllBusinessOwner()) //cauta in lista de b.o. pe cel dat ca parametru
            if(businessOwner.getFirstName().equals(b.getFirstName()) && businessOwner.getLastName().equals(b.getLastName())) { //daca il gaseste
                for(Offer o: b.getSentOffers()) { //pt fiecare oferta a b.o dat ca param (gasit)
                    //view.showOffer(o);
                    returnedOffers.add(o);
                }
            }
        return returnedOffers;
    }

    //verificare daca un produs e al unui b.o. dupa id-ul produsului
    public boolean isBusinessOwnerProduct(Integer idProduct) {
        for(Product product : businessOwner.getProducts()) {
            if(product.getId() == idProduct) {
                return true;
            }
        }
        return false;
    }
}


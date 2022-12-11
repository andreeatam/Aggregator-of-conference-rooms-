package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Entity
@Table(name = "organisers")
public class Organiser extends User implements Comparator<Organiser> {

    @Override
    public int compare(Organiser organiser1, Organiser organiser2){
        return organiser1.getUsername().compareTo(organiser2.getUsername());
    }
    @OneToMany(mappedBy = "receiver")
    private List<Offer> receivedOffers = new ArrayList<>();
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();


    public Organiser(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        this.receivedOffers = new ArrayList<>();
    }

    public Organiser() {

    }

    public List<Offer> getReceivedOffers() {
        return receivedOffers;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }


//    @Override
//    public void add(Message newMessage) {
//
//    }
//
//    @Override
//    public void remove(Integer integer) {
//
//    }
//
//    @Override
//    public void update(Integer integer, Message newEntity) {
//
//    }
//
//    @Override
//    public Message findById(Integer integer) {
//        return null;
//    }

//    @Override
//    public void add(Offer newOffer){
//        for(Offer offer: this.sentOffers){
//            if(of.getIdOffer().equals(entity.getIdOffer())){
//                return;
//            }
//        }
//        this.sentOffers.add(entity);
//    }
//
//    //sterge o oferta dupa id din lista de oferte trimise
//    @Override
//    public void remove(Integer id){
//
//        this.sentOffers.removeIf(of -> of.getIdOffer().equals(id));
//    }
//
//    @Override
//    public void update(Integer id, Offer new_offer) {
//
//        for( Offer of : this.sentOffers) {
//            if(of.getIdOffer().equals(id)) {
//                of.setStartingDate(new_offer.getStartingDate());
//                of.setEndingDate(new_offer.getEndingDate());
//                of.setDescription(new_offer.getDescription());
//                of.setAdInOffer(new_offer.getAdInOffer());
//                break;
//            }
//        }
//    }
//
//    @Override
//    public Offer findById(Integer id) {
//        for(Offer offer : this.sentOffers) {
//            if(offer.getIdOffer().equals(id)) {
//                return offer;
//            }
//        }
//        return null;
//    }



}

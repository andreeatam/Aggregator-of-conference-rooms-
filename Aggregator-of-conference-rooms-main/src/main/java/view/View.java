package view;

import Controller.BusinessOwnerController;
import Controller.OrganiserController;
import Controller.Server;
import model.*;
import repo.inMemory.BusinessOwnerInMemoryRepository;
import repo.inMemory.OrganiserInMemoryRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class View {

    private final Server server;

    private final BusinessOwnerController businessOwnerController;

    private final OrganiserController organiserController;

    public View() {
        this.server = Server.getInstance();
        this.businessOwnerController = BusinessOwnerController.getInstance();
        this.organiserController = OrganiserController.getInstance();
    }


    public void runProgram(){
        while(true) {
            int option = welcomeView(); //se alege din exact primul meniu o opt -login/signup/exit
            if(option == 1) {
                loginMenu();
            } else if (option == 2) {
                signUpMenu();
            } else if (option == 0) {
                break;
            } else {
                wrongNumber();
            }
        }
    }

    public void signUpMenu() {
        ArrayList<String> credentials = signupView(); //ret un string format din datele de la SignUp
        boolean flag = server.signUp(credentials); //flag ul retine daca s-a facut cu succes sign up ul
        if(flag) { //if(flag==1)
            userCreatedSuccessfully();
            loginMenu();
        }
        else{
            somethingWentWrong();
        }
    }

    public int welcomeView() {
        Scanner input = new Scanner(System.in); //cls Scanner e folosita pt a lua input-ul user ului

        System.out.println("Welcome!");
        System.out.println("Select option: ");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. SignUp");

        int option = input.nextInt(); //nextInt() - citeste o valoare int a user ului

        return option;
    }

    //met ce returneaza un string de credentiale format din DI (tipUser + username + passw)
    public ArrayList<String> loginView() {
        Scanner input = new Scanner(System.in);
        ArrayList<String> credentials = new ArrayList<>();

        System.out.println("Select:");
        boolean ok = true;
        String userType = "";
        while (ok) {
            System.out.println("1-event organiser");
            System.out.println("2-business owner");
            userType = input.nextLine(); //nextLine() - citeste un string a user-ului
            if(userType.equals("1") || userType.equals("2")) {
                ok = false;
            }
            else {
                wrongNumber();
            }
        }

        System.out.println("username: ");
        String username = input.nextLine();
        System.out.println("password: ");
        String password = input.nextLine();

        //adaugam in string ul de credentials, tip user(b.o/org) + username + passw
        credentials.add(userType);
        credentials.add(username);
        credentials.add(password);
        return credentials;
    }

    public void loginMenu() {
        ArrayList<String> credentials = loginView(); //string de credentiale  format din (tipUser + username + passw)
        boolean isUser = server.login(credentials);
        if(isUser) {
            if(credentials.get(0).equals("1"))
                organiserMenu(credentials.get(1));
            else if(credentials.get(0).equals("2"))
                businessOwnerMenu(credentials.get(1));
        }
        else {
            wrongCredentials();
            loginMenu();
        }
    }



    public void businessOwnerMenu(String username) {
        int option = businessOwnerView(); //se alege o opt din meniu b.o.
        server.createBusinessOwnerInController(username); //se cauta b.o dupa id in lista de b.o si se set noul b.o (cel gasit)

        if(option == 1) {
           showBusinessOwnerProducts();
        }
        else if(option == 2) {
            newMessagesMenu();
        }
        else if(option == 3) {
            allMessagesMenu();
        }
        else if(option == 4) {
            showOffers();
        }
        else if(option == 5) {
            createProductMenu();
        }
        else if(option==6){
            deleteProductMenu();
        }
        else if(option==7) {
            modifyProductMenu();
        }
        else if(option == 8) {
            runProgram();
        }
        else {
            wrongNumber(); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }
        businessOwnerMenu(username); //se poate face o noua alegere din meniu
    }

    public void newMessagesMenu() {
        if (businessOwnerController.checkNewMessages()) { //daca lista de oferte cerute a b.o. e goala
            noNewMessages(); //nu exista msj nou
        }
        for (Message message : businessOwnerController.getBusinessOwner().getReceivedMessages()) { //pt fiecare mesaj a org catre b.o. din lista de oferte cerute
            if(message.getStatus().equals(Status.SENT)) { //daca starea msj e de SENT
                showMessage(message); //vezi msj
                askOfferMaking(); //apare msj daca vrei sa faci o oferta
                boolean answer = answer(); //se ret rasp true/false
                if (answer) {  //if(answer!=false) //daca rasp e da
                    makeOfferMenu(message); //se face oferta
                    offerSent(); //apare msj de oferta creata cu succes
                } else { //daca rasp e nu
                    businessOwnerController.declineMessage(message); //se set starea msj la DECLINED
                    messageDeclined(); //apare msj de declined
                }
            }
        }
    }

    public void makeOfferMenu(Message message) {
        Offer offer = createOfferView(message);
        businessOwnerController.makeOffer(offer, message);
    }

    public void allMessagesMenu() {
        if (businessOwnerController.checkNewMessages()) { //daca lista de oferte cerute a b.o. e goala
            noMessages(); //nu exista mesaje primite
        }
        for (Message message : businessOwnerController.getBusinessOwner().getReceivedMessages()) { //pt fiecare mesaj a org catre b.o. din lista de oferte cerute
            showMessage(message); //se arata msj de forma.. pe care il trimite org b.o.
        }

    }

    public void showOffers() {
        if(businessOwnerController.checkSentOffers()) { //daca lista de oferte trimise a b.o. e goala
            noOffers(); //apare msj ca nu ai ce oferte sa vezi
        }
        else {
            for (Offer offer : businessOwnerController.getBusinessOwner().getSentOffers()) {
                showOffer(offer);
            }
        }
    }

    //se creeaza un produs si apoi se adauga la instanta
    public void createProductMenu() {
        Product createdProduct = createProductView();
        businessOwnerController.createProduct(createdProduct);
    }

    public void deleteProductMenu(){
        Integer idProduct = ProductIdByBusinessOwner();
        businessOwnerController.deleteProduct(idProduct);
    }

    public void modifyProductMenu() {
        Integer idProduct = ProductIdByBusinessOwner();
        Product newProduct = createProductView();
        businessOwnerController.modifyProduct(idProduct, newProduct);
    }

    public void organiserMenu(String username) {
        int option = organiserView(); //se alege o opt din meniu org

       server.createOrganiserInController(username); //se cauta org dupa id in lista de org si se set noul org (cel gasit)

        if(option == 1){
            showProducts();
        }
        else if(option == 2) {
           showNewOffersMenu();
        }
        else if(option == 3) {
            showSentMessages();
        }
        else if(option == 4) {
            showReceivedOffers();
        }
        else if(option == 5){
            sendMessageMenu();
            messageSent();
        }
        else if(option == 6) {
            runProgram();
        }
        else {
            wrongNumber(); //se face o alta alegere din meniu pt ca optiunea nu a fost valida
        }
        organiserMenu(username);
    }


    public void showNewOffersMenu() {
        if(organiserController.checkNewReceivedOffers()) { //daca lista de oferte primite a org e goala
            noNewMessages(); //nu exita msj nou
        }
        else {
            for (Offer offer : organiserController.getOrganiser().getReceivedOffers()) { //pt fiecare oferta din lista de oferte primite a org
                if (offer.getStatus().equals(Status.SENT)) { //daca starea ofertei e SENT
                    showOffer(offer); //se afis oferta
                    askOfferAccepting(); //apare msj daca vrei sa accepti oferta
                    boolean answer = answer();
                    if (answer) { //daca rasp e true
                        organiserController.acceptOffer(offer); //status ul ofertei devine ACCEPTED
                        offerAccepted(); //apare msj cu oferta acceptata
                    } else {
                        organiserController.declineOffer(offer); //status ul ofertei devine DECLINED
                        offerDeclined(); //apare msj cu oferta respinsa
                    }
                }
            }
        }
    }

    public void showSentMessages() {
        if(organiserController.checkRequestedOffers()) { //daca lista de oferte cerute a org e goala
            noSentMessages(); //apare msj ca nu ai trimis inca niciun msj
        }
        else { //daca lista nu e goala
            for (Message message : organiserController.getOrganiser().getSentMessages()) { //pt fiecare msj din lista de oferte cerute a org
                showMessage(message); //se afis msj
            }
        }
    }

    public void showReceivedOffers() {
        if(organiserController.checkNewReceivedOffers()) {
            noSentMessages();
        }
        else {
            for (Offer offer : organiserController.getOrganiser().getReceivedOffers()) {
                showOffer(offer);
            }
        }
    }

    public void sendMessageMenu() {
        Message message = createMessageView(); //se creaza un msj
        organiserController.sendMessage(message);
    }

    public void wrongCredentials() {
        System.out.println("Wrong username or password please try again");
    }


    //metoda ce ret un string de credentiale obtinute din signUp (cu validarile necesare de username daca acesta a mai fost folosit sau nu)
    public ArrayList<String> signupView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Sign up form: ");
        System.out.println("First Name:");
        String firstName = input.nextLine();
        System.out.println("Last Name: ");
        String lastName = input.nextLine();

        boolean ok = true;

        System.out.println("username: ");
        String username = input.nextLine();

        while(ok) { //while(ok==true)
            //in bd avem deja un username existent pt un oarecare utiliz, nu putem avea 2 cu acelasi username
            //deci, se incearca un username pana se gaseste unul diferit de toate

            if(server.getBusinessOwner(username) != null || server.getOrganiser(username) != null) {
                System.out.println("Unavailable username, please choose another one");
            }
            else { //gaseste un username nou, adica il accepta pe cel introdus
                ok = false;
            }
            System.out.println("username: ");
            username = input.nextLine();
        }
        System.out.println("Password: ");
        String password = input.nextLine();

        String userType = null;
        ok = true;

        while(ok) {
            System.out.println("I am a: ");
            System.out.println("1-event organiser");
            System.out.println("2-business owner");
            userType = input.nextLine();
            if(userType.equals("1") || userType.equals("2")) {
                ok = false;
            }
            else {
                wrongNumber();
            }
        }

        ArrayList<String> credentials = new ArrayList<>(Arrays.asList(userType, firstName, lastName, username, password));
        return credentials;

    }

    public void somethingWentWrong() {
        System.out.println("Something went wrong...");
        System.out.println("Please try again later!");
    }
    public void userCreatedSuccessfully() {
        System.out.println("User created Successfully!");
        System.out.println("Please login");
    }

    public void wrongNumber() {
        System.out.println("Please choose a valid option");
    }


    public int businessOwnerView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show your Products");
        System.out.println("2. Show new Messages");
        System.out.println("3. Show all Messages");
        System.out.println("4. Show all Offers");
        System.out.println("5. Create product");
        System.out.println("6. Delete product");
        System.out.println("7. Modify product");
        System.out.println("8 Log out");
        int option = input.nextInt();

        return option;
    }

    public int organiserView(){
        Scanner input=new Scanner(System.in);

        System.out.println("Select: ");
        System.out.println("1. Show all products");
        System.out.println("2. Show new offers"); // dupa status
        System.out.println("3. Show sent messages");
        System.out.println("4. Show all offers");
        System.out.println("5. Send message to ask for an offer");
        System.out.println("6. Log out ");
        int option=input.nextInt();

        return option;

    }

    public void showProduct(Product product){
        System.out.println("Id:" + product.getId());
        System.out.println("Product: " + product.getName());
        System.out.println("Rating: " + product.getRating());
        System.out.println("Description: " + product.getDescription() + "\n");

    }


    public void showProducts() {
        List<Product> products = organiserController.getProducts();
        for(Product product: products) {
            showProduct(product);
        }
    }

    public void showBusinessOwnerProducts() {
        List<Product> products = businessOwnerController.getBusinessOwner().getProducts();
        if(products.isEmpty()) {
            System.out.println("You don't have products listed!\n");
        }
        else {
            for (Product product : products) {
                showProduct(product);
            }
        }
    }
    public void showOffer(Offer offer) {
        System.out.println("Offer Id: " + offer.getId());
        System.out.println("Status: " + offer.getStatus());
        System.out.println("Price: " + offer.getPrice());
        System.out.println("Description: " + offer.getDescription());
        System.out.println("Product Id: " + offer.getProduct().getId()+"-Product "+offer.getProduct().getName()+"\n");
    }

    //metoda org pe care o trimite b.o.
    public void showMessage(Message message) {
        System.out.println("Product Id: " + message.getProduct().getId()+"-Product "+message.getProduct().getName());
        System.out.println("Status: " + message.getStatus());
        System.out.println("Starting Date: " + message.getStartingDate());
        System.out.println("Ending Date: " + message.getEndingDate());
        System.out.println("Guests' number: " + message.getGuests());
        System.out.println("Description: " + message.getDescription()+"\n");

    }

    public Integer selectTypeOfProduct() {

        boolean ok = true;
        Scanner input = new Scanner(System.in);
        Integer option = null;


        while (ok) {
            System.out.println("Choose the type of the service you create: ");
            System.out.println("1- Hall renting");
            System.out.println("2- Dj");
            System.out.println("3- Candybar");
            option = input.nextInt();
            if(option == 1) {
                return 1;
            }
            if(option == 2) {
                return 2;
            }
            if (option == 3) {
                return 3;
            }
            wrongNumber();

        }
        somethingWentWrong();
        return null;
    }
    public Product createProductView() {
        System.out.println("Product: ");

        //se ret tipul de produs pe care il ofera businessOwner ul
        System.out.println("Enter the values of the product you offer: ");
        Integer option = selectTypeOfProduct();
        if(option == 1) {
            return createHallView();
        }
        if(option == 2) {
            return createDJView();
        }
        if(option == 3) {
            return createCandyBarView();
        }
        return null;
    }

    public Product getProductView() {

        Scanner input = new Scanner(System.in);
        System.out.println("Please insert the product Id: ");

        int idProduct = input.nextInt();
        Product product = server.getProduct(idProduct);

        while (product == null) {
            System.out.println("Please insert a valid Id: ");
            showProducts();
            idProduct = input.nextInt();
            product = server.getProduct(idProduct);
        }
        return product;
    }

    public Integer ProductIdByBusinessOwner() {

        Scanner input = new Scanner(System.in);
        System.out.println("Please insert the product Id: ");

        int idProduct = input.nextInt();

        while (!businessOwnerController.isBusinessOwnerProduct(idProduct)) {
            System.out.println("Please insert a valid Id: ");
            showBusinessOwnerProducts();
            idProduct = input.nextInt();
        }
        return idProduct;
    }

    public Hall createHallView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        System.out.println("Location: ");
        String location = input.nextLine();
        System.out.println("Capacity: ");
        Integer capacity = input.nextInt();

        return new Hall(name, description, location, capacity);
    }

    public DJ createDJView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();

        // facut teste sa raspunda corect
        boolean lights = false;
        System.out.println("Lights: yes/no");
        String answer = input.nextLine();
        if(answer.equals("yes") || answer.equals("y")) {
            lights = true;
        }

        boolean stereo = false;
        System.out.println("Stereo: yes/no");
        answer = input.nextLine();
        if(answer.equals("yes") || answer.equals("y")) {
            stereo = true;
        }
        return new DJ(name, description, lights, stereo);
    }

    public CandyBar createCandyBarView() {
        Scanner input = new Scanner(System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        System.out.println("Add sweets: ");
        boolean ok = true;
        ArrayList<Sweet> sweets = new ArrayList<>();

        while (ok) {
            String sweetString = input.nextLine();
            if(sweetString.equals(" ") || sweetString.equals("\n") || sweetString.equals("")) {
                ok = false;
            }
            Sweet sweet = new Sweet(sweetString);
            sweets.add(sweet);
        }
        return new CandyBar(name, description, sweets);
    }

    public Message createMessageView() {
        Scanner input = new Scanner(System.in);

        System.out.println("Start Date: ");
        String startDate = input.nextLine();
        System.out.println("End Date: ");
        String endDate=input.nextLine();

        System.out.println("Description: ");
        String description=input.nextLine();

        Product productInMessage = getProductView();

        Integer guests = null;
        while (true) {
            System.out.println("Number of guests: ");
            guests = input.nextInt();
            if (productInMessage instanceof Hall) { //daca prod din anuntul din msj e o instanta a salii, caci doar la sala ai nr de invitati
                if(guests <= (((Hall) productInMessage).getCapacity())) { //daca incap invitatii in sala
                    break;
                }
                else {
                    System.out.println("Too many guest for the Hall");
                    System.out.println("Maximum capacity is "+ ((Hall) productInMessage).getCapacity());
                    System.out.println("Please enter a smaller value if you want an offer!");
                }
            }
            else { //daca prod din anuntul din msj NU e o instanta a salii
                break;
            }
        }

        //se ret un Msj(prod, sender ORG, receiver BO returnat din id ul prod, stDate, endDate, guests, description)
        return new Message(productInMessage, OrganiserController.getInstance().getOrganiser(),  BusinessOwnerInMemoryRepository.getInstance().findBusinessOwnerByProductId(productInMessage.getId()), startDate, endDate, guests, description);

    }

    //oferta de creare e un msj cu un pret si o descriere
    public Offer createOfferView(Message message) {
        Scanner input = new Scanner(System.in);

        System.out.println("Description: ");
        String description = input.nextLine();

        System.out.println("Price: ");
        Integer price = input.nextInt();


        //ret o oferta (sender BO, receiver ORG gasit dupa id msj, ret prod din msj, pret, descriere)
        return new Offer(BusinessOwnerController.getInstance().getBusinessOwner(), OrganiserInMemoryRepository.getInstance().findOrganiserByMessageId(message.getIdMessage()), message.getProduct(), price, description);

    }

    public void noNewMessages() {
        System.out.println("Nothing new...");
        System.out.println("Check again later\n");
    }


    public void askOfferMaking() {
        System.out.println("Do you want to make an offer? (Yes/No)");
    }

    public void askOfferAccepting() {
        System.out.println("Do you want to accept an offer? (Yes/No)");
    }

    public boolean answer() {
        Scanner input = new Scanner(System.in);
        while(true) {
            String answer = input.nextLine();
            if (answer.equals("yes") || answer.equals("y") || answer.equals("Yes"))
                return true;
            if (answer.equals("no") || answer.equals("n") || answer.equals("No"))
                return false;
            System.out.println("Please select Yes or No");
        }
    }

    public void noMessages() {
        System.out.println("You don't have any messages");
        System.out.println("Check again later\n");
    }

    public void noOffers() {
        System.out.println("You haven't made any offer yet\n");
    }

    public void noSentMessages() {
        System.out.println("You haven't sent any messages yet\n");
    }
    public void messageSent() {
        System.out.println("Message sent successfully!\n");
    }

    public void offerSent() {
        System.out.println("Offer sent successfully!\n");
    }

    public void messageDeclined() {
        System.out.println("Message declined!\n");
    }
    public void offerDeclined() {
        System.out.println("Offer declined!\n");
    }

    public void offerAccepted() {
        System.out.println("Offer accepted!\n");
    }


}

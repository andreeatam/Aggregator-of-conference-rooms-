package tests.Repository;

import model.BusinessOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.inMemory.BusinessOwnerInMemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOwnerRepositoryTest {

    @BeforeEach
    void setUp() {
        BusinessOwnerInMemoryRepository.getInstance();
    }

    @Test
    void add() {
        System.out.println("Test add business owner");
        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "bowner1", "123");

        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 2);

        BusinessOwnerInMemoryRepository.getInstance().add(businessOwner1);
        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 3);

    }

    @Test
    void remove() {
        System.out.println("Test remove business owner");

        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 2);
        BusinessOwnerInMemoryRepository.getInstance().remove("raulstefan002");

        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 1);

        BusinessOwnerInMemoryRepository.getInstance().remove("bowner1");
        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 1);
    }

    @Test
    void update() {
        System.out.println("Test update business owner");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul1111", "123");

        assertEquals(BusinessOwnerInMemoryRepository.getInstance().getSize(), 2);
        BusinessOwnerInMemoryRepository.getInstance().update("raulstefan002", businessOwner1);

        assertNotNull(BusinessOwnerInMemoryRepository.getInstance().findById("raul1111"));

        assertNull(BusinessOwnerInMemoryRepository.getInstance().findById("raulstefan002"));

    }

    @Test
    void findById() {
        System.out.println("Test find business owner by username");

        BusinessOwner businessOwner1 = new BusinessOwner("asd", "b", "raul", "123");
        BusinessOwnerInMemoryRepository.getInstance().add(businessOwner1);

        assertNull(BusinessOwnerInMemoryRepository.getInstance().findById("raul1111"));
        assertEquals(BusinessOwnerInMemoryRepository.getInstance().findById("raul"), businessOwner1);

    }
}
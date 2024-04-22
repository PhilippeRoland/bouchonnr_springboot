package com.philippe.bouchonnr.Repository;

import com.philippe.bouchonnr.entity.Listing;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.ListingRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ListingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ListingRepository listingRepository;

    @Test
    public void testWhenFindById_returnListing() {
        // given
        String name = "wineName";
        Wine wine = new Wine();
        wine.setName(name);
        Wine persistedWine = entityManager.persist(wine);
        entityManager.flush();

        String sellerName = "store";
        double price = 19.21d;
        Listing listing = new Listing();
        listing.setWine(persistedWine);
        listing.setSeller(sellerName);
        listing.setPrice(price);
        Listing persistedlisting = entityManager.persist(listing);
        entityManager.flush();

        // when
        Optional<Listing> foundListing = listingRepository.findById(persistedlisting.getId());

        // then
        Assert.assertNotNull(foundListing);
        Assert.assertTrue(foundListing.isPresent());
        Assert.assertEquals(sellerName, foundListing.get().getSeller());
        Assert.assertEquals(price, foundListing.get().getPrice(), 1e-15);
        Assert.assertEquals(persistedWine, foundListing.get().getWine());
    }

}

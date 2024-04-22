package com.philippe.bouchonnr.Repository;

import com.philippe.bouchonnr.entity.Alert;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.AlertRepository;
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
public class AlertRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlertRepository alertRepository;

    @Test
    public void testWhenFindById_returnAlert() {
        // given
        String name = "wineName";
        Wine wine = new Wine();
        wine.setName(name);
        Wine persistedWine = entityManager.persist(wine);
        entityManager.flush();

        double minPrice = 5d;
        double maxPrice = 30d;
        String notificationUrl = "https://bouchonnr.free.beeceptor.com";
        Alert alert = new Alert();
        alert.setWine(persistedWine);
        alert.setNotificationUrl(notificationUrl);
        alert.setMaxPrice(maxPrice);
        alert.setMinPrice(minPrice);

        Alert persistedAlert = entityManager.persist(alert);
        entityManager.flush();

        // when
        Optional<Alert> foundAlert = alertRepository.findById(persistedAlert.getId());

        // then
        Assert.assertNotNull(foundAlert);
        Assert.assertTrue(foundAlert.isPresent());
        Assert.assertEquals(persistedWine, foundAlert.get().getWine());
        Assert.assertEquals(notificationUrl, foundAlert.get().getNotificationUrl());
        Assert.assertEquals(minPrice, foundAlert.get().getMinPrice(), 1e-15);
        Assert.assertEquals(maxPrice, foundAlert.get().getMaxPrice(), 1e-15);


    }

}

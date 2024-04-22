package com.philippe.bouchonnr.Repository;

import com.philippe.bouchonnr.entity.Rating;
import com.philippe.bouchonnr.entity.Wine;
import com.philippe.bouchonnr.repository.RatingRepository;
import com.philippe.bouchonnr.repository.WineRepository;
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
public class RatingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void testWhenFindById_returnRating() {
        // given
        String name = "wineName";
        Wine wine = new Wine();
        wine.setName(name);
        Wine persistedWine = entityManager.persist(wine);
        entityManager.flush();

        Rating rating = new Rating();
        rating.setWine(persistedWine);
        int score = 1;
        rating.setScore(score);
        Rating persistedRating = entityManager.persist(rating);
        entityManager.flush();

        // when
        Optional<Rating> foundRating = ratingRepository.findById(persistedRating.getId());

        // then
        Assert.assertNotNull(foundRating);
        Assert.assertTrue(foundRating.isPresent());
        Assert.assertEquals(wine, foundRating.get().getWine());
        Assert.assertEquals(score, foundRating.get().getScore());
    }

    @Test
    public void testWhenAddRating_wineScoreChange() {
        // given
        String name = "wineName";
        Wine wine = new Wine();
        wine.setName(name);
        Wine persistedWine = entityManager.persist(wine);
        entityManager.flush();
        entityManager.detach(persistedWine); //See https://stackoverflow.com/questions/73948999

        Rating rating = new Rating();
        rating.setWine(persistedWine);
        int score = 1;
        rating.setScore(score);
        entityManager.persist(rating);
        entityManager.flush();

        Rating rating2 = new Rating();
        rating2.setWine(persistedWine);
        int score2 = 5;
        rating2.setScore(score2);
        entityManager.persist(rating2);
        entityManager.flush();

        // when
        Optional<Wine> foundWine = wineRepository.findById(persistedWine.getId());

        // then
        Assert.assertNotNull(foundWine);
        Assert.assertTrue(foundWine.isPresent());
        System.out.println(foundWine.get().getRatings().size());
        Assert.assertEquals(3, foundWine.get().getAvgRating(), 1e-15); //need small delta as avg rating is floating point
    }

}

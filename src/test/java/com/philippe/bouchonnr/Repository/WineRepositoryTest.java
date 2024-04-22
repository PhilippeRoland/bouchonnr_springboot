package com.philippe.bouchonnr.Repository;

import com.philippe.bouchonnr.entity.Wine;
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
public class WineRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WineRepository employeeRepository;

    @Test
    public void testWhenFindById_returnWine() {
        // given
        String name = "wineName";
        Wine wine = new Wine();
        wine.setName(name);
        Wine persisted = entityManager.persist(wine);
        entityManager.flush();

        // when
        Optional<Wine> foundWine = employeeRepository.findById(persisted.getId());

        // then
        Assert.assertNotNull(foundWine);
        Assert.assertTrue(foundWine.isPresent());
        Assert.assertEquals(name, foundWine.get().getName());

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.persistence;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author CesarF
 */

@RunWith(Arquillian.class)
public class RecipePersistenceTest {
    
    @Inject
    RecipePersistence recipePersistence;

    @PersistenceContext
    EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void configTest() {
       try {
           utx.begin();
        em.createQuery("delete from RecipeEntity").executeUpdate();
        utx.commit();
       } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        
    }

    @Test
    public void createRecipeTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        IngredientEntity ingredient = factory.manufacturePojo(IngredientEntity.class);
        List<IngredientEntity> ingredientes = new ArrayList<>();
        ingredientes.add(ingredient);
        newEntity.setIngredientes(ingredientes);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        em.persist(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
        Assert.assertEquals(recipePersistence.find(newEntity.getId()).getIngredientes().get(0), ingredient);
    }
}

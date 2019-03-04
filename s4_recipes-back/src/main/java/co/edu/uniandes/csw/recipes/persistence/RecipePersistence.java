/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.persistence;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author CesarF
 */

@Stateless
public class RecipePersistence {
    private static final Logger LOGGER = Logger.getLogger(RecipePersistence.class.getName());
    
    @PersistenceContext(unitName = "recipesPU")
    protected EntityManager em;
    
  
    public RecipeEntity find(Long id) {
        return em.find(RecipeEntity.class, id);
    }
    
    public List<RecipeEntity> getAll() {
        Query q = em.createQuery("select u from RecipeEntity u");
        return q.getResultList();
    }
    
    public RecipeEntity findByName(String name) {
        // Se crea un query para buscar libros con el isbn que recibe el método como argumento. ":isbn" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From RecipeEntity e where e.name = :name", RecipeEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<RecipeEntity> sameISBN = query.getResultList();
        RecipeEntity result;
        if (sameISBN == null) {
            result = null;
        } else if (sameISBN.isEmpty()) {
            result = null;
        } else {
            result = sameISBN.get(0);
        }
        return result;
    }
    
    //TODO método crear de recipe
    public RecipeEntity createRecipe(RecipeEntity recipeEntity) {
        em.persist(recipeEntity);
        return recipeEntity;
    }
}

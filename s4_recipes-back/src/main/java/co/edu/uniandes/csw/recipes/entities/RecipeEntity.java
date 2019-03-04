/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author CesarF
 */
@Entity
public class RecipeEntity extends BaseEntity {
    
    private String name;
    private String description;
    
    @PodamExclude
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<IngredientEntity> ingredientes;
    
    public RecipeEntity(){
    
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<IngredientEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredientEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;

/**
 *
 * @author jf.castaneda
 */
public class IngredientDTO {
    
    private String name;
    
    private Long calories;
    
    private Long id;

    public IngredientDTO() {
        
    }
    
    public IngredientDTO(IngredientEntity ingredientEntity) {
        this.name = ingredientEntity.getName();
        this.calories = ingredientEntity.getCalories();
        this.id = ingredientEntity.getId();
    }
    
    public IngredientEntity toEntity() {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setCalories(calories);
        ingredientEntity.setId(id);
        ingredientEntity.setName(name);
        return ingredientEntity;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}

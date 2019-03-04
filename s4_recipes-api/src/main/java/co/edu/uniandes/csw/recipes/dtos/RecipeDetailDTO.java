/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CesarF
 */
public class RecipeDetailDTO extends RecipeDTO {
    
    private List<IngredientDTO> ingredientes;
    
    public RecipeDetailDTO(){
        super();
    }
    
    public RecipeDetailDTO(RecipeEntity entity){
        super(entity);
        if(entity.getIngredientes() != null) {
            ingredientes = new ArrayList<>();
            for(IngredientEntity ingredientEntity : entity.getIngredientes()) {
                ingredientes.add(new IngredientDTO(ingredientEntity));
            }
        }
    }
    
    public RecipeEntity toEntity() {
        RecipeEntity recipeEntity = super.toEntity();
        if(ingredientes != null) {
            List<IngredientEntity> ingredientesEntity = new ArrayList<>();
            for(IngredientDTO ingredientDTO : getIngredientes()) {
                ingredientesEntity.add(ingredientDTO.toEntity());
            }
            recipeEntity.setIngredientes(ingredientesEntity);
        }
        return recipeEntity;
    }

    public List<IngredientDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredientDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    
    
}

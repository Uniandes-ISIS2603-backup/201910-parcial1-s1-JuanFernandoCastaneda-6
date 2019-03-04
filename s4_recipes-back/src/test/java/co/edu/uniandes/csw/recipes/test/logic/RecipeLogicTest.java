/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
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
 * @author jf.castaneda
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {

    @Inject
    RecipePersistence recipePersistence;

    @Inject
    RecipeLogic recipeLogic;

    @PersistenceContext
    EntityManager em;
    @Inject
    private UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
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
    
    /**
     * Todo salió bien.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createRecipeTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        IngredientEntity ingrediente = factory.manufacturePojo(IngredientEntity.class);
        List<IngredientEntity> ingredientes = new ArrayList<>();
        ingredientes.add(ingrediente);
        newEntity.setIngredientes(ingredientes);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
        RecipeEntity recipeInDB = em.find(RecipeEntity.class, newEntity.getId());
        Assert.assertEquals(newEntity.getId(), recipeInDB.getId());
        Assert.assertEquals(newEntity.getName(), recipeInDB.getName());
        Assert.assertEquals(newEntity.getDescription(), recipeInDB.getDescription());
        Assert.assertEquals(newEntity.getIngredientes().get(0), ingrediente);
    }

    /**
     * Nombre nulo
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeNameNullTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(null);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }

    /**
     * Nombre vacío.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeEmptyNameTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("");
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }

    /**
     * Nombre mayor a 30 caracteres.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeLargeNameTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName("asjdfskdfhaksdfhklashdflkhaskjdfhaskdfhkashdfklhasjkldfklashkldjfhjklasdfklhaskdfkasldkfhklasdfhklashdfkljhaklsdfhklasjdh");
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }

    /**
     * Descripción nula.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeDescriptionNullTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription(null);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }

    /**
     * Descripción vacía.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeEmptyDescriptionTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription("");
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }

    /**
     * Descripción mayor a 150 caracteres.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createRecipeLargeDescriptionTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setDescription("Instrucciones\n"
                + "Descargue este repositorio y abra los proyectos en Netbeans.\n"
                + "\n"
                + "En Netbeans vaya a Services > Databases > JavaDB y cree una base de datos que se llame recipesPU (los demás campos déjelos en blanco).\n"
                + "\n"
                + "En Netbeans vaya a Tools > Options > Java, seleccione la pestaña Maven y marque la opción Skip Tests for any build executions no directly related to testing.\n"
                + "\n"
                + "Cada vez que complete un paso genere un commit.\n"
                + "\n"
                + "Al finalizar ingrese a sicua y suba el link de su repositorio.\n"
                + "\n"
                + "Sonria :)\n"
                + "\n"
                + "Contexto\n"
                + "Esta aplicación permite gestionar recetas. El recurso presente en la aplicación es Recipe, la cual tiene un nombre (name: String), una descripción (description: String) y un identificador (id: Long) que es la llave primaria.\n"
                + "\n"
                + "En la aplicación usted encontrará que las funcionalidades de solicitar (GET) ya están implementadas.\n"
                + "\n"
                + "Se nos ha solicitado completar los siguientes requisitos:\n"
                + "\n"
                + "Punto 1 (40%): Crear receta\n"
                + "Se desea que el sistema permita crear una receta.\n"
                + "\n"
                + "Ud. debe extender su programa para que cuando ejecute\n"
                + "\n"
                + "POST localhost:8080/s4_recipes-api/api/recipes\n"
                + "\n"
                + "con el json:\n"
                + "\n"
                + "{\n"
                + "    \"name\": \"Tacos de res\",\n"
                + "    \"description\": \"Receta para cocinar tacos típicos con un fuerte sabor picante\"\n"
                + "}\n"
                + "se cree la receta con la información.\n"
                + "\n"
                + "Para esto Ud. debe:\n"
                + "\n"
                + "(5%) Crear el método createRecipe en la clase RecipePersistence el cual permita almacenar la receta en la base de datos\n"
                + "\n"
                + "(5%) Completar el método de prueba createRecipeTest para que valide si se crea correctamente una receta.\n"
                + "\n"
                + "(10%) Crear el método createRecipe en la clase RecipeLogic el cual valida las siguientes reglas de negocio.\n"
                + "\n"
                + "El nombre de la receta es válido, no es vacio ni nulo y tampoco supera los 30 caracteres\n"
                + "No deben haber dos recetas con el mismo nombre\n"
                + "La descripción es válida, no es vacia ni nula y tampoco supera los 150 caracteres\n"
                + "(10%) Cree la clase de pruebas RecipeLogicTest que contiene el método de prueba createRecipeTest para que valide si se crea correctamente una receta.\n"
                + "\n"
                + "(5%) Cree el método createRecipe en la clase RecipeResource que recibe el cuerpo de la receta, solicita la creación de la receta y retorna la receta con su nuevo id.\n"
                + "\n"
                + "(5%) Cree una colección de postman para probar tanto al creación como las reglas de negocio asociadas. Exporte la colección y guardela en la carpeta collections del proyecto s4_recipes-api. Esta colección debe estar parametrizada para correr las pruebas automáticamente.\n"
                + "\n"
                + "Al finalizar suba los cambios y cree un release con el nombre punto_1\n"
                + "\n"
                + "Punto 2 (60%): Agregar ingrediente\n"
                + "(10%) Crear las clases IngredientDTO y IngredientEntity que modelan al ingrediente. Un ingrediente tiene un nombre (name: String) y una cantidad de calorias (calories: Long) y un id (id: Long). En la clase IngredientDTO, además de tener un constructor sin parámetros, defina un constructor para convertir un IngredientEntity en un IngredientDTO:\n"
                + "public IngredientDTO(IngredientEntity ingredient) {\n"
                + "    this.id = ingredient.getId();\n"
                + "    this.name = ingredient.getName();\n"
                + "    ...\n"
                + "}\n"
                + "Para convertir un IngredientDTO en un IngredientEntity defina el siguiente método:\n"
                + "\n"
                + "public IngredientEntity toEntity() {\n"
                + "    IngredientEntity entity = new IngredientEntity();\n"
                + "    entity.setId(this.id);\n"
                + "    entity.setName(this.name);   \n"
                + "    ...		\n"
                + "    return entity;\n"
                + "}\n"
                + "(10%) Defina en RecipeEntity la relación con Ingrediente (unidireccional) e implemente sus set/get. Esta es una relación de composición de uno de muchos (OneToMany).\n"
                + "\n"
                + "(5%) Defina un atributo nuevo en RecipeDetailDTO que representa el listado de ingredientes de la receta. Defina set/get y actualice el método constructor que recibe un RecipeEntity al igual que el método toEntity, el cual retorna un objeto de tipo RecipeEntity, para que también hagan la conversión del listado de ingredientes.\n"
                + "\n"
                + "(5%) Modifique el método createRecipe de la clase RecipeLogic para que tenga en cuenta la siguiente regla de negocio.\n"
                + "\n"
                + "Toda receta debe tener al menos un ingrediente\n"
                + "Si las reglas de negocio se cumplen, se debe llamar la persistencia para que el objeto sea persistido, de lo contrario debe lanzar una excepción BussinessLogicException con un mensaje donde se especifique cuál regla no se cumplió.\n"
                + "\n"
                + "(10%) Modifique la prueba unitaria de la capa de persistencia createRecipe para que ahora valide si la lista de ingredientes que se almacena es correcta.\n"
                + "\n"
                + "(10%) Modifique la prueba unitaria createRecipe de la capa de lógica para que ahora valide si la lista de ingredientes que se almacena es correcta.\n"
                + "\n"
                + "(10%) Cree una colección de postman para probar tanto al creación como las reglas de negocio asociadas. Exporte la colección y guardela en la carpeta collections del proyecto s4_recipes-api. Puede apoyarse en los siguientes escenarios de prueba. Esta colección debe estar parametrizada para correr las pruebas automáticamente.");
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) == null);
        newEntity = recipeLogic.createRecipe(newEntity);
        Assert.assertTrue(recipePersistence.find(newEntity.getId()) != null);
    }
}

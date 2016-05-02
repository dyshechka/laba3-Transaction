package dao;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import models.Animal;

@Stateless
@LocalBean
public class AnimalDAO {

    @PersistenceContext(unitName = "lab3-animals-ejbPU")
    private EntityManager em;

    public void createAnimal(Animal animal) {
        em.persist(animal);
    }
    
    public Animal readAnimal (int id){
        return em.find(Animal.class, id);
    }
    
    public List<Animal> readAllAnimals (){
        Query query = em.createQuery("SELECT a FROM Animal a", Animal.class);
        return query.getResultList();
    }
    
    public void updateAnimalOK(Animal animal){
        em.merge(animal);
    }
    
    public void updateAnimalException(Animal animal){
        em.merge(animal);
        throw new EJBException("ERROR! Exception in animalDAO");
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void updateAnimalWithoutTransaction(Animal animal){
        em.merge(animal);
    }
    
    public void deleteAnimal(Animal animal){
        em.remove(em.merge(animal));
    }
    
}

package service;

import dao.AnimalDAO;
import dao.StorageDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import models.Animal;
import models.Storage;

@Stateless
@LocalBean
public class StorageService {

    @EJB
    private StorageDAO storageDAO;

    @EJB
    private AnimalDAO animalDAO;

    public void createStorage(Storage storage) {
        storageDAO.createStorage(storage);
    }

    public Storage readStorage(int id) {
        return storageDAO.readStorage(id);
    }

    public List<Storage> readAllStorages() {
        return storageDAO.readAllStorages();
    }

    public void deleteStorage(Storage storage) {
        storageDAO.deleteStorage(storage);
    }

    public void createAnimal(Animal animal) {
        if (animal.getCurrDish() < animal.getMaxDish()) {
            animalDAO.createAnimal(animal);
        } else {
            throw new IllegalArgumentException("ERROR! maxDish must be "
                    + "greater or equal than currDish!");
        }
    }

    public Animal readAnimal(int id) {
        return animalDAO.readAnimal(id);
    }

    public List<Animal> readAllAnimals() {
        return animalDAO.readAllAnimals();
    }

    public void deleteAnimal(Animal animal) {
        animalDAO.deleteAnimal(animal);
    }

    public void feedAnimalOK(Storage storage, Animal animal, int foodForAnimal) {
        if (validateAndFeed(storage, animal, foodForAnimal)) {
            storageDAO.updateStorageOK(storage);
            animalDAO.updateAnimalOK(animal);
        } else {
            throw new IllegalArgumentException("ERROR in feedAnimalOK! You are wrong feeding animal!");
        }
    }

    public void feedAnimalRollback(Storage storage, Animal animal, int foodForAnimal) {
        if (validateAndFeed(storage, animal, foodForAnimal)) {
            storageDAO.updateStorageWithRollback(storage);//2 experiment (p.7)
            animalDAO.updateAnimalOK(animal);
        } else {
            throw new IllegalArgumentException("ERROR in ...! ...");
        }
    }

    public void feedAnimalException(Storage storage, Animal animal, int foodForAnimal) {
        if (validateAndFeed(storage, animal, foodForAnimal)) {
            storageDAO.updateStorageOK(storage);
            animalDAO.updateAnimalException(animal);//3 experiment (p.8)
        } else {
            throw new IllegalArgumentException("ERROR in ...! ...");
        }
    }

    public void feedAnimalWithoutTransaction(Storage storage, Animal animal, int foodForAnimal) {
        if (validateAndFeed(storage, animal, foodForAnimal)) {
            storageDAO.updateStorageWithRollback(storage);
            animalDAO.updateAnimalWithoutTransaction(animal);//4 experiment (p.9)
        } else {
            throw new IllegalArgumentException("ERROR in ...! ...");
        }
    }

    public void feedAnimalWithNewTransaction(Storage storage, Animal animal, int foodForAnimal) {
        if (validateAndFeed(storage, animal, foodForAnimal)) {
            storageDAO.updateStorageWithNewTransaction(storage);//5 experiment (p.10)
            animalDAO.updateAnimalException(animal);
        } else {
            throw new IllegalArgumentException("ERROR in ...! ...");
        }
    }

    private boolean validateAndFeed(Storage storage, Animal animal, int foodForAnimal) {
        if ((foodForAnimal > 0)
                && (foodForAnimal <= storage.getCountFood())
                && (foodForAnimal + animal.getCurrDish() <= animal.getMaxDish())) {
            storage.setCountFood(storage.getCountFood() - foodForAnimal);
            animal.setCurrDish(animal.getCurrDish() + foodForAnimal);
            return true;
        }
        return false;
    }
}
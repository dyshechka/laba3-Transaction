package controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import models.Animal;
import models.Storage;
import service.StorageService;

@Named
@RequestScoped
public class StorageController {

    @EJB
    private StorageService storageService;

    private Storage storage;
    private Animal animal;
    private int idStorage;
    private int idAnimal;
    private int foodForAnimal;

    public StorageController() {
        storage = new Storage();
        animal = new Animal();
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getIdStorage() {
        return idStorage;
    }

    public void setIdStorage(int idStorage) {
        this.idStorage = idStorage;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getFoodForAnimal() {
        return foodForAnimal;
    }

    public void setFoodForAnimal(int foodForAnimal) {
        this.foodForAnimal = foodForAnimal;
    }

    public List<Storage> readAllStorages() {
        return storageService.readAllStorages();
    }

    public List<Animal> readAllAnimals() {
        return storageService.readAllAnimals();
    }

    public void createStorage() {
        storageService.createStorage(storage);
    }

    public void deleteStorage(Storage storage) {
        storageService.deleteStorage(storage);
    }

    public void createAnimal() {
        try {
            storageService.createAnimal(animal);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(e.getCause().getMessage()));
        }
    }

    public void deleteAnimal(Animal animal) {
        storageService.deleteAnimal(animal);
    }

    public void feedAnimal(String howFeed) {
        try {
            switch (howFeed) {
                case "feedAnimalOK":
                    feedAnimalOK();
                    break;
                case "feedAnimalRollback":
                    feedAnimalRollback();
                    break;
                case "feedAnimalException":
                    feedAnimalException();
                    break;
                case "feedAnimalWithoutTransaction":
                    feedAnimalWithoutTransaction();
                    break;
                case "feedAnimalWithNewTransaction":
                    feedAnimalWithNewTransaction();
                    break;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(e.getCause().getMessage()));
        }
    }

    private void feedAnimalOK() {
        storageService.feedAnimalOK(storageService.readStorage(idStorage),
                storageService.readAnimal(idAnimal), foodForAnimal);
    }

    private void feedAnimalRollback() {
        storageService.feedAnimalRollback(storageService.readStorage(idStorage),
                storageService.readAnimal(idAnimal), foodForAnimal);
    }

    private void feedAnimalException() {
        storageService.feedAnimalException(storageService.readStorage(idStorage),
                storageService.readAnimal(idAnimal), foodForAnimal);
    }

    private void feedAnimalWithoutTransaction() {
        storageService.feedAnimalWithoutTransaction(storageService.readStorage(idStorage),
                storageService.readAnimal(idAnimal), foodForAnimal);
    }

    private void feedAnimalWithNewTransaction() {
        storageService.feedAnimalWithNewTransaction(storageService.readStorage(idStorage),
                storageService.readAnimal(idAnimal), foodForAnimal);
    }

}

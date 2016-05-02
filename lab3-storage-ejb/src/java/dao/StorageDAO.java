package dao;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import models.Storage;

@Stateless
@LocalBean
public class StorageDAO {

    @PersistenceContext(unitName = "lab3-storage-ejbPU")
    private EntityManager em;
    
    @Resource
    private EJBContext context;

    public void createStorage(Storage storage) {
        em.persist(storage);
    }
    
    public Storage readStorage (int id){
        return em.find(Storage.class, id);
    }
    
    public List<Storage> readAllStorages (){
        Query query = em.createQuery("SELECT s FROM Storage s", Storage.class);
        return query.getResultList();
    }
    
    public void updateStorageOK(Storage storage){
        em.merge(storage);
    }
    
    public void updateStorageWithRollback(Storage storage){
        em.merge(storage);
        context.setRollbackOnly();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateStorageWithNewTransaction(Storage storage){
        em.merge(storage);
    }
    
    public void deleteStorage(Storage storage){
        em.remove(em.merge(storage));
    }

}

package wizut.tpsi.ogloszenia.services;

import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.jpa.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class OffersService {
    @PersistenceContext
    private EntityManager em;

    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }
    public List<CarManufacturer> getCarManufacturers() {
        String jpql = "select cm from CarManufacturer cm order by cm.name";
        TypedQuery<CarManufacturer> query = em.createQuery(jpql, CarManufacturer.class);
        List<CarManufacturer> result = query.getResultList();
        return result;
    }
    public List<BodyStyle> getBodyStyles() {
        return em.createQuery("select bodyStyle from BodyStyle bodyStyle order by bodyStyle.name", BodyStyle.class).getResultList();

    }
    public List<FuelType> getFuelTypes() {
        return em.createQuery("select fuelType from FuelType fuelType order by fuelType.name", FuelType.class).getResultList();

    }
    public List<CarModel> getCarModels() {
        return em.createQuery("select carModel from CarModel carModel order by carModel.name", CarModel.class).getResultList();
    }
    public List<CarModel> getCarModels(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";

        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }
    public Offer getOffer(int id) {
        return em.find(Offer.class, id);
    }
    public List<Offer> getOffers() {
        return em.createQuery("select ofr from Offer ofr order by ofr.price", Offer.class).getResultList();
    }
    public List<Offer> getOffersByModel(int modelId) {
        String jpql = "select ofr from Offer ofr where ofr.model.id = :id order by ofr.price";

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);

        return query.getResultList();
    }
    public List<Offer> getOffersByManufacturer(int manufacturerId) {
        String jpql = "select ofr from Offer ofr where ofr.manufacturer.id = :id order by ofr.price";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }
    public CarModel getModel(int id)
    {
        return em.find(CarModel.class, id);
    }
}

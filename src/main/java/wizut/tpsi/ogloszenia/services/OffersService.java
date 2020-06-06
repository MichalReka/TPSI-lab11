package wizut.tpsi.ogloszenia.services;

import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.jpa.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OffersService {
    @PersistenceContext
    private EntityManager em;
    private int itemsPerPage=20;
    private int numberOfOffers;
    private String sortOffer="dateTime DESC";
    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }

    public String getSortOffer() {
        return sortOffer;
    }

    public void setSortOffer(String sortOffer) {
        this.sortOffer = sortOffer;
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

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getNumberOfPages() {
        List<String> pages = new ArrayList<String>();
        int offerCount=em.createQuery("select ofr from Offer ofr order by ofr."+sortOffer, Offer.class).getResultList().size();
        int pageCount = (int) Math.ceil((double)offerCount/(double)itemsPerPage);
        return pageCount;
    }

    public Offer getOffer(int id) {
        return em.find(Offer.class, id);
    }
    public List<Offer> getOffers(int page) {
        String jpql = "select ofr from Offer ofr order by ofr."+sortOffer;

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setMaxResults(itemsPerPage*page);
        query.setFirstResult(itemsPerPage*(page-1));
        return query.getResultList();
    }
    public List<Offer> getOffersByModel(int modelId,int page) {
        String jpql = "select ofr from Offer ofr where ofr.model.id = :id order by ofr."+sortOffer;

        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);
        query.setMaxResults(itemsPerPage*page);
        query.setFirstResult(itemsPerPage*(page-1));
        return query.getResultList();
    }
    public List<Offer> getOffersByManufacturer(int manufacturerId,int page) {
        String jpql = "select ofr from Offer ofr where ofr.model.manufacturer.id = :id order by ofr."+sortOffer;
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);
        query.setMaxResults(itemsPerPage*page);
        query.setFirstResult(itemsPerPage*(page-1));
        return query.getResultList();
    }
    public CarModel getModel(int id)
    {
        return em.find(CarModel.class, id);
    }
    public Offer createOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }
    public Offer deleteOffer(Integer id) {
        Offer offer = em.find(Offer.class, id);
        em.remove(offer);
        return offer;
    }
    public Offer saveOffer(Offer offer) {
        return em.merge(offer);
    }
}

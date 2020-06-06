package wizut.tpsi.ogloszenia.web;

public class OfferFilter {
    Integer manufacturerId;
    Integer modelId;
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public OfferFilter(Integer manufacturerId, Integer modelId) {
        this.manufacturerId = manufacturerId;
        this.modelId = modelId;
    }
}

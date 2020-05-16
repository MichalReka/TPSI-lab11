package wizut.tpsi.ogloszenia.jpa;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "engine_size")
    private BigDecimal engineSize;

    @Column(name = "engine_power")
    private Integer enginePower;

    @Column(name = "doors")
    private Integer doors;

    @Size(max = 30)
    @Column(name = "colour")
    private String colour;

    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ManyToOne
    private CarModel model;

    @JoinColumn(name = "body_style_id", referencedColumnName = "id")
    @ManyToOne
    private BodyStyle bodyStyle;

    @JoinColumn(name = "fuel_type_id", referencedColumnName = "id")
    @ManyToOne
    private FuelType fuelType;

    public Offer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(BigDecimal engineSize) {
        this.engineSize = engineSize;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Integer enginePower) {
        this.enginePower = enginePower;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public BodyStyle getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(BodyStyle bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}

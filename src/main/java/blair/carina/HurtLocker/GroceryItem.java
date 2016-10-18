package blair.carina.HurtLocker;

import java.util.HashMap;

/**
 * Created by carinablair on 10/17/16.
 */
public class GroceryItem {

    private String name;
    private String type;
    private String price;
    private String expiration;
    private HashMap<String, Integer> priceCount;

    public GroceryItem(String name, String price, String type, String expiration) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.expiration = expiration;
        this.priceCount = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public HashMap<String, Integer> getPriceCount(){ return priceCount;}

}

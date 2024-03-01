package productimporter.suppliers.wayneenterprises;

import productimporter.Pricing;
import productimporter.Product;

import java.math.BigDecimal;

final class WayneEnterprisesProductTranslator {

    public Product translate(WayneEnterprisesProduct p) {
        Pricing pricing = getPricing(p);
        return new Product("WAYNE", p.getId(), p.getTitle(), pricing);
    }

    private Pricing getPricing(WayneEnterprisesProduct p) {
        int listPrice = p.getListPrice();
        int sellingPrice = p.getSellingPrice();
        BigDecimal discount = new BigDecimal(listPrice - sellingPrice);
        return new Pricing(new BigDecimal(listPrice), discount);
    }
}

package productimporter.suppliers.starkindustries;

import productimporter.Pricing;
import productimporter.Product;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProduct;

import java.math.BigDecimal;

class StarkIndustriesProductTranslator {

    public Product translate(StarkIndustriesProduct p) {
//        Pricing pricing = getPricing(p);
//        return new Product("WAYNE", p.getId(), p.getTitle(), pricing);
        return null;
    }

    private Pricing getPricing(StarkIndustriesProduct p) {
//        int listPrice = p.getListPrice();
//        int sellingPrice = p.getSellingPrice();
//        BigDecimal discount = new BigDecimal(listPrice - sellingPrice);
//        return new Pricing(new BigDecimal(listPrice), discount);
        return null;
    }
}

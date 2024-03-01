package productimporter;

import java.util.ArrayList;

public class ProductInventorySpy implements ProductInventory {

    private final ArrayList<Product> log;

    public ProductInventorySpy() {
        log = new ArrayList<Product>();
    }

    public ArrayList<Product> getLog() {
        return log;
    }

    @Override
    public void upsertProduct(Product product) {
        log.add(product);
    }
}

package productimporter.suppliers.starkindustries;

import productimporter.Product;
import productimporter.ProductImporter;

import java.util.ArrayList;

public class StarkIndustriesProductImporter implements ProductImporter  {

    private StarkIndustriesProductSource productSource;
    private StarkIndustriesProductTranslator translator;

    public StarkIndustriesProductImporter(StarkIndustriesProductSource productSource, StarkIndustriesProductTranslator translator) {
        this.productSource = productSource;
        this.translator = translator;
    }

    @Override
    public Iterable<Product> fetchProducts() {
        Iterable<StarkIndustriesProduct> source = productSource.getAllProducts();
        var products = new ArrayList<Product>();
        for (StarkIndustriesProduct s : source) {
            products.add(translator.translate(s));
        }
        return products;
    }
}

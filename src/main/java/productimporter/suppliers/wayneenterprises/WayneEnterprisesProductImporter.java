package productimporter.suppliers.wayneenterprises;

import productimporter.Product;
import productimporter.ProductImporter;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class WayneEnterprisesProductImporter implements ProductImporter {

    private WayneEnterprisesProductSource dataSource;
    private WayneEnterprisesProductTranslator translator;

    public WayneEnterprisesProductImporter(WayneEnterprisesProductSource dataSource) {
        this.dataSource = dataSource;
        this.translator = new WayneEnterprisesProductTranslator();
    }

    @Override
    public Iterable<Product> fetchProducts() {
        return StreamSupport.stream(dataSource.fetchProducts().spliterator(), false)
                .map(p -> translator.translate(p))
                .collect(Collectors.toList());
    }

}

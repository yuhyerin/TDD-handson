package productimporter.suppliers.wayneenterprises;

import productimporter.Product;
import productimporter.ProductImporter;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class WayneEnterprisesProductImporter implements ProductImporter {

    private WayneEnterprisesProductSource dataSource;

    public WayneEnterprisesProductImporter(WayneEnterprisesProductSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Product> fetchProducts() {
        return StreamSupport.stream(dataSource.fetchProducts().spliterator(), false)
                .map(p -> new Product("WAYNE", null, null, null))
                .collect(Collectors.toList());
    }

}

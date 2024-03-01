package productimporter.suppliers.wayneenterprises;

import productimporter.Pricing;
import productimporter.Product;
import productimporter.ProductImporter;

import java.math.BigDecimal;
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
                .map(p -> new Product("WAYNE", p.getId(), p.getTitle(),
                        new Pricing(new BigDecimal(p.getListPrice()), new BigDecimal(p.getListPrice() - p.getSellingPrice())))
                )
                .collect(Collectors.toList());
    }

}

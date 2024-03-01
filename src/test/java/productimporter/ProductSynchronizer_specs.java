package productimporter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProduct;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductImporter;
import productimporter.suppliers.wayneenterprises.WayneEnterprisesProductSourceStub;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductSynchronizer_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("ProductSynchronizer가 inventory에 상품을 잘 저장한다")
    void sut_correctly_saves_products(WayneEnterprisesProduct[] products) {
        var stub = new WayneEnterprisesProductSourceStub(products);
        var importer = new WayneEnterprisesProductImporter(stub);
        var validator = new ListPriceFilter(BigDecimal.ZERO);
        var spy = new ProductInventorySpy();
        var sut = new ProductSynchronizer(importer, validator, spy);

        sut.run();

        Iterable<Product> expected = importer.fetchProducts();
        assertThat(spy.getLog())
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(expected);
    }

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("validator에 의해 검증되지 않은 상품은 저장되지 않는다")
    void sut_does_not_save_invalid_Product(WayneEnterprisesProduct product) {
        // Arrange
        var lowerBound = new BigDecimal(product.getListPrice() + 10000);
        var validator = new ListPriceFilter(lowerBound);

        var stub = new WayneEnterprisesProductSourceStub(product);
        var importer = new WayneEnterprisesProductImporter(stub);
        var spy = new ProductInventorySpy();
        var sut = new ProductSynchronizer(importer, validator, spy);

        // Act
        sut.run();

        // Assert
        assertThat(spy.getLog()).isEmpty();
    }

    @Test
    @DisplayName("validator에 의해 검증되지 않은 상품은 저장되지 않는다")
    void sut_does_not_save_invalid_Product_with_mock() {
        // Arrange
        var pricing = new Pricing(BigDecimal.TEN, BigDecimal.ONE);
        var product = new Product("supplierName", "prodouctCode", "productName", pricing);

        // mock
        ProductImporter importer = mock(ProductImporter.class);
        when(importer.fetchProducts())
                .thenReturn(Arrays.asList(product));

        ProductValidator validator = mock(ProductValidator.class);
        when(validator.isValid(product)).thenReturn(false);

        ProductInventory inventory = mock(ProductInventory.class);

        var sut = new ProductSynchronizer(importer, validator, inventory);

        // Act
        sut.run();

        // Assert
        verify(inventory, never()).upsertProduct(product);
    }
}
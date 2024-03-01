package productimporter.suppliers.wayneenterprises;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

import static org.assertj.core.api.Assertions.assertThat;

class WayneEnterprisesProductImporter_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("테스트 파라미터로 받은 상품갯수가 sut가 불러온 상품갯수와 일치한다")
    void sut_projects_all_products(WayneEnterprisesProduct[] source) {
        var stub = new WayneEnterprisesProductSourceStub(source);
        var sut = new WayneEnterprisesProductImporter(stub);

        Iterable<Product> actual = sut.fetchProducts();

        assertThat(actual).hasSize(source.length);
    }

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("공급자 이름을 잘 설정한다")
    void sut_correctly_sets_supplier_name(WayneEnterprisesProduct[] source) {
        var stub = new WayneEnterprisesProductSourceStub(source);
        var sut = new WayneEnterprisesProductImporter(stub);

        Iterable<Product> actual = sut.fetchProducts();

        assertThat(actual).allMatch(x -> x.getSupplierName().equals("WAYNE"));
    }

}
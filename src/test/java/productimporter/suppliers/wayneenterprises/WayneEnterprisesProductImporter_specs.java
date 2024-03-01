package productimporter.suppliers.wayneenterprises;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

import java.util.ArrayList;
import java.util.List;

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

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("공급자 상품이 가진 프로퍼티를 잘 투영한다")
    void sut_correctly_projects_source_properties(WayneEnterprisesProduct source) {
        var stub = new WayneEnterprisesProductSourceStub(source);
        var sut = new WayneEnterprisesProductImporter(stub);

        List<Product> products = new ArrayList<>();
        sut.fetchProducts().forEach(products::add);
        Product actual = products.get(0);

        assertThat(actual.getProductCode()).isEqualTo(source.getId());
        assertThat(actual.getProductName()).isEqualTo(source.getTitle());

        // 우리 Product 의 가격 타입 > listPrice : 정가, discount : 할인가
        // Wayne 이 제공하는 Product 의 가격 타입 > listPrice : 정가, sellingPrice : 판매가
        // -> 할인가를 제공하지 않기 때문에 할인가를 계산해야 한다. (할인가 = 정가-판매가)
        assertThat(actual.getPricing().getListPrice()).isEqualByComparingTo(Integer.toString(source.getListPrice()));
        assertThat(actual.getPricing().getDiscount())
                .isEqualByComparingTo(Integer.toString(source.getListPrice() - source.getSellingPrice())); // 할인가 = 정가 - 판매가
    }

}
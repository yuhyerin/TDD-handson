package productimporter.suppliers.starkindustries;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import productimporter.DomainArgumentsSource;
import productimporter.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StarkIndustriesProductImporter_specs {

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("테스트 파라미터로 받은 상품갯수가 sut가 불러온 상품갯수와 일치한다")
    void sut_projects_all_products(StarkIndustriesProduct[] sourceProducts) {
        // Arrange
        var productSource = mock(StarkIndustriesProductSource.class);
        when(productSource.getAllProducts()).thenReturn(Arrays.asList(sourceProducts));
        var translator = mock(StarkIndustriesProductTranslator.class);
        var sut = new StarkIndustriesProductImporter(productSource, translator);

        // Act
        Iterable<Product> actual = sut.fetchProducts();

        // Assert
        assertThat(actual).hasSize(sourceProducts.length);
    }

    @ParameterizedTest
    @DomainArgumentsSource
    @DisplayName("stark industries 공급사의 상품을 우리 상품으로 잘 변환한다")
    void sut_correctly_translates_source_products(StarkIndustriesProduct[] sourceProducts, Product[] products) {
        // Arrage
        var productSource = mock(StarkIndustriesProductSource.class);
        when(productSource.getAllProducts()).thenReturn(Arrays.asList(sourceProducts));
        var translator = mock(StarkIndustriesProductTranslator.class);

        // 값을 변환하는 것 자체는 테스트하지 않고, translator 에게 값을 변환하는 특정 조건을 세팅해준다.
        List<Tuple> tuples = IntStream.range(0, Math.min(sourceProducts.length, products.length))
                .mapToObj(index -> Tuple.tuple(sourceProducts[index], products[index]))
                .collect(Collectors.toList());
        for (Tuple tuple : tuples) {
            Object[] values = tuple.toArray();
            when(translator.translate((StarkIndustriesProduct) values[0]))
                    .thenReturn((Product) values[1]);
        }

        var sut = new StarkIndustriesProductImporter(productSource, translator);

        // Act
        Iterable<Product> actual = sut.fetchProducts();

        // Assert
        assertThat(actual).containsExactly(products);
    }
}
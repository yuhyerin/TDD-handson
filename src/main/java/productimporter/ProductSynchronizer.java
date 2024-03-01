package productimporter;

public final class ProductSynchronizer {

    private final ProductImporter importer;
    private final ProductValidator validator;
    private final ProductInventory inventory;

    public ProductSynchronizer(ProductImporter importer, ProductValidator validator, ProductInventory inventory) {
        this.importer = importer;
        this.validator = validator;
        this.inventory = inventory;
    }

    /**
     * Synchronizer
     * 1. importer 로 부터 상품을 얻는다. (입력)
     * 2. validator 로 부터 상품을 검증하여 검증결과를 얻는다. (입력)
     * 3. 검증에 통과한 상품은 inventory 에 저장한다. (출력)
     * */
    public void run() {
        for (Product product : importer.fetchProducts()) {
            inventory.upsertProduct(product);
        }
    }
}

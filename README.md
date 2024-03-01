# 현실 세상의 TDD 실습 코드
## 커머스 서비스
> 여러개의 상품 공급자로부터 상품을 공급받아서 우리 자체 인벤토리에 저장했다가 판매하는 기능 제공

## 아키텍처
<img width="428" alt="commerce_architecture" src="https://github.com/yuhyerin/TDD-handson/assets/37924810/bfa2d910-3758-404d-ad35-c9606930d8ab">

- 인벤토리 (Inventory) : 우리 커머스 서비스의 핵심 도메인에 있는 컴포넌트
- 싱크로나이저 (Product Synchronizor) : 상품을 소싱하는 영역
- 임포터 (Product Importer) : 공급자(Supplier)를 우리 서비스에 맞게 적응시켜주는 어댑터 영역
- 공급자 (SupplierA, SupplierB) : 공급자 영역

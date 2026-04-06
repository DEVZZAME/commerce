import { productSummaries } from "@/features/catalog/data/mockProducts";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductCard } from "@/shared/ui/ProductCard";
import { SectionPanel } from "@/shared/ui/SectionPanel";

const categoryFilters = ["전체", "키보드", "디스플레이", "정리"];

export function ProductListPage() {
  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="상품 목록"
        title="한눈에 탐색하고 바로 비교할 수 있는 목록 화면"
        description="카테고리, 배지, 배송 문구를 동일한 카드 시스템에 올려서 React 컴포넌트 형태로 재구성했습니다."
      />

      <SectionPanel title="카테고리 탐색" description="국문 사용자를 기준으로 필터 라벨과 설명 문구를 단순하게 유지했습니다.">
        <div className="chip-row">
          {categoryFilters.map((filter, index) => (
            <span key={filter} className={`chip${index === 0 ? " chip--active" : ""}`}>
              {filter}
            </span>
          ))}
        </div>
      </SectionPanel>

      <section className="product-grid">
        {productSummaries.map((product) => (
          <ProductCard key={product.id} {...product} />
        ))}
      </section>
    </main>
  );
}

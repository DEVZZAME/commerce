import { Link } from "react-router-dom";
import { productSummaries } from "@/features/catalog/data/mockProducts";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductCard } from "@/shared/ui/ProductCard";
import { SectionPanel } from "@/shared/ui/SectionPanel";

export function HomePage() {
  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="STEP 06 완료"
        title="상품 API 연동과 장바구니 상태관리가 붙은 프론트 시작 화면"
        description="목록과 상세는 백엔드 API를 기준으로 동작하고, 장바구니와 주문 요청은 프론트 상태 흐름으로 이어집니다."
        actionLabel="목록 / 상세 / 장바구니 / 주문 화면 연결"
      />

      <SectionPanel
        title="핵심 화면 바로가기"
        description="홈 화면은 예시 카드만 보여주고, 실제 데이터 조회는 상품 목록과 상품 상세 화면에서 진행합니다."
        extra={
          <Link className="button button--primary" to="/products">
            상품 목록 보기
          </Link>
        }
      >
        <div className="product-grid">
          {productSummaries.slice(0, 3).map((product) => (
            <ProductCard key={product.id} {...product} />
          ))}
        </div>
      </SectionPanel>
    </main>
  );
}

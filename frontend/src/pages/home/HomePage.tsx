import { Link } from "react-router-dom";
import { productSummaries } from "@/features/catalog/data/mockProducts";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductCard } from "@/shared/ui/ProductCard";
import { SectionPanel } from "@/shared/ui/SectionPanel";

export function HomePage() {
  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="국문 우선 프론트엔드"
        title="퍼블리싱 결과를 React 컴포넌트 구조로 옮긴 시작 화면"
        description="이제 목록, 상세, 장바구니 화면이 React 라우팅과 공통 레이아웃 위에서 동작합니다. 다음 단계에서는 여기에 실제 API 연동과 상태관리를 붙이면 됩니다."
        actionLabel="STEP 05-02 ~ 05-08 완료"
      />

      <SectionPanel
        title="핵심 화면 바로가기"
        description="현재는 정적 목업 데이터를 사용하지만, 컴포넌트 경계와 타입은 이후 API 연동을 고려해 분리했습니다."
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

import { Link, useParams } from "react-router-dom";
import { findProductDetail } from "@/features/catalog/lib/findProduct";
import { formatPrice } from "@/features/catalog/lib/format";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductToneImage } from "@/shared/ui/ProductToneImage";
import { SectionPanel } from "@/shared/ui/SectionPanel";
import { SummaryCard } from "@/shared/ui/SummaryCard";

export function ProductDetailPage() {
  const { productId = "" } = useParams();
  const product = findProductDetail(productId);
  const selectedOption = product.options[0];
  const finalPrice = product.price + selectedOption.additionalPrice;

  return (
    <main className="page-stack">
      <PageIntro
        eyebrow={product.sellerName}
        title={product.name}
        description={product.storyTitle}
      />

      <div className="detail-layout">
        <div className="page-stack">
          <SectionPanel title="대표 이미지" description={product.subtitle}>
            <ProductToneImage tone={product.imageTone} badge={product.badge} />
          </SectionPanel>

          <SectionPanel title="상품 설명" description={product.story}>
            <ul className="feature-list">
              {product.highlights.map((highlight) => (
                <li key={highlight}>{highlight}</li>
              ))}
            </ul>
          </SectionPanel>

          <SectionPanel title="옵션 선택" description="실제 옵션 선택 로직은 다음 API 연동 단계에서 상태로 분리합니다.">
            <div className="option-list">
              {product.options.map((option, index) => (
                <div
                  key={option.id}
                  className={`option-card${index === 0 ? " option-card--selected" : ""}`}
                >
                  <div>
                    <strong>{option.name}</strong>
                    <p>{option.description}</p>
                  </div>
                  <div className="option-card__meta">
                    <strong>
                      {option.additionalPrice > 0
                        ? `+${formatPrice(option.additionalPrice)}`
                        : "추가 금액 없음"}
                    </strong>
                    <span>재고 {option.stock}개</span>
                  </div>
                </div>
              ))}
            </div>
          </SectionPanel>
        </div>

        <SummaryCard title="구매 요약">
          <div className="summary-line">
            <span>상품 금액</span>
            <strong>{formatPrice(product.price)}</strong>
          </div>
          <div className="summary-line">
            <span>선택 옵션</span>
            <strong>{selectedOption.name}</strong>
          </div>
          <div className="summary-line">
            <span>옵션 추가</span>
            <strong>{formatPrice(selectedOption.additionalPrice)}</strong>
          </div>
          <div className="summary-total">
            <span>최종 금액</span>
            <strong>{formatPrice(finalPrice)}</strong>
          </div>
          <div className="summary-actions">
            <Link className="button button--accent" to="/cart">
              장바구니 담기
            </Link>
            <Link className="button button--secondary" to="/products">
              목록으로 돌아가기
            </Link>
          </div>
        </SummaryCard>
      </div>
    </main>
  );
}

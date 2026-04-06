import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { fetchProduct } from "@/features/catalog/api/productApi";
import { findProductDetail } from "@/features/catalog/lib/findProduct";
import { formatPrice } from "@/features/catalog/lib/format";
import { mapProductDetail } from "@/features/catalog/lib/productMapper";
import { useCart } from "@/features/cart/state/CartContext";
import { useAsyncState } from "@/shared/hooks/useAsyncState";
import type { ProductDetail } from "@/shared/types/commerce";
import { ErrorBlock } from "@/shared/ui/ErrorBlock";
import { LoadingBlock } from "@/shared/ui/LoadingBlock";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductToneImage } from "@/shared/ui/ProductToneImage";
import { SectionPanel } from "@/shared/ui/SectionPanel";
import { SummaryCard } from "@/shared/ui/SummaryCard";

export function ProductDetailPage() {
  const { productId = "" } = useParams();
  const numericProductId = Number(productId);
  const [product, setProduct] = useState<ProductDetail | null>(null);
  const [selectedOptionId, setSelectedOptionId] = useState<number | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [notice, setNotice] = useState<string | null>(null);
  const { addItem } = useCart();
  const asyncState = useAsyncState();

  useEffect(() => {
    async function loadProduct() {
      asyncState.start();

      try {
        if (!Number.isFinite(numericProductId)) {
          throw new Error("유효하지 않은 상품 경로입니다.");
        }

        const response = await fetchProduct(numericProductId);
        const mapped = mapProductDetail(response, 0);
        setProduct(mapped);
        setSelectedOptionId(mapped.options[0]?.id ?? null);
        setNotice(null);
        asyncState.succeed();
      } catch (error) {
        const fallback = findProductDetail(numericProductId);

        if (fallback) {
          setProduct(fallback);
          setSelectedOptionId(fallback.options[0]?.id ?? null);
          setNotice("백엔드 상세 조회에 실패해 샘플 데이터를 표시합니다.");
          asyncState.succeed();
          return;
        }

        asyncState.fail(
          error instanceof Error ? error.message : "상품 상세를 불러오지 못했습니다.",
        );
      }
    }

    void loadProduct();
  }, [numericProductId]);

  if (asyncState.status === "loading" || !product) {
    return <LoadingBlock title="상품 정보를 불러오는 중입니다" />;
  }

  if (asyncState.status === "error") {
    return (
      <ErrorBlock
        description={asyncState.errorMessage ?? "상품 정보를 불러오지 못했습니다."}
      />
    );
  }

  const selectedOption =
    product.options.find((option) => option.id === selectedOptionId) ?? product.options[0];
  const finalPrice = product.price + (selectedOption?.additionalPrice ?? 0);

  return (
    <main className="page-stack">
      <PageIntro
        eyebrow={product.sellerName}
        title={product.name}
        description={product.storyTitle}
      />
      {notice ? (
        <SectionPanel title="안내" description={notice} />
      ) : null}

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

          <SectionPanel title="옵션 선택" description="옵션 선택과 수량은 장바구니 상태관리와 연결됩니다.">
            <div className="option-list">
              {product.options.map((option) => (
                <div
                  key={option.id}
                  className={`option-card${selectedOption?.id === option.id ? " option-card--selected" : ""}`}
                  role="button"
                  tabIndex={0}
                  onClick={() => setSelectedOptionId(option.id)}
                  onKeyDown={(event) => {
                    if (event.key === "Enter" || event.key === " ") {
                      setSelectedOptionId(option.id);
                    }
                  }}
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
            <div className="quantity-row">
              <span>수량</span>
              <div className="quantity-row__actions">
                <button
                  className="button button--secondary"
                  type="button"
                  onClick={() => setQuantity((current) => Math.max(1, current - 1))}
                >
                  -
                </button>
                <strong>{quantity}</strong>
                <button
                  className="button button--secondary"
                  type="button"
                  onClick={() => setQuantity((current) => current + 1)}
                >
                  +
                </button>
              </div>
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
            <strong>{selectedOption?.name ?? "기본 옵션"}</strong>
          </div>
          <div className="summary-line">
            <span>옵션 추가</span>
            <strong>{formatPrice(selectedOption?.additionalPrice ?? 0)}</strong>
          </div>
          <div className="summary-total">
            <span>최종 금액</span>
            <strong>{formatPrice(finalPrice * quantity)}</strong>
          </div>
          <div className="summary-actions">
            <button
              className="button button--accent"
              type="button"
              onClick={() => {
                if (!selectedOption) return;

                addItem({
                  product,
                  optionId: selectedOption.id,
                  quantity,
                });
              }}
            >
              장바구니 담기
            </button>
            <Link className="button button--secondary" to="/products">
              목록으로 돌아가기
            </Link>
          </div>
        </SummaryCard>
      </div>
    </main>
  );
}

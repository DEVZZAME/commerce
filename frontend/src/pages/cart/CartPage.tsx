import { Link } from "react-router-dom";
import { useCart } from "@/features/cart/state/CartContext";
import { formatPrice } from "@/features/catalog/lib/format";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductToneImage } from "@/shared/ui/ProductToneImage";
import { SectionPanel } from "@/shared/ui/SectionPanel";
import { SummaryCard } from "@/shared/ui/SummaryCard";

export function CartPage() {
  const { items, totalPrice, removeItem } = useCart();
  const discount = items.length > 1 ? 12000 : 0;
  const finalTotal = totalPrice - discount;

  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="장바구니"
        title="결제 직전까지 빠르게 검토할 수 있는 장바구니 화면"
        description="선택한 상품은 Context와 localStorage에 저장되어 새로고침 후에도 유지되고, 주문 / 결제 화면까지 그대로 이어집니다."
      />

      <div className="detail-layout">
        <SectionPanel title="담은 상품" description="현재는 목업 데이터지만 카드 구조는 API 응답 배열에 바로 연결할 수 있게 만들었습니다.">
          {items.length === 0 ? (
            <p className="section-panel__description">
              아직 장바구니에 담긴 상품이 없습니다. 상품 상세 화면에서 옵션을 선택해 담아보세요.
            </p>
          ) : (
            <div className="cart-list">
              {items.map((item) => (
                <article key={item.id} className="cart-item">
                  <ProductToneImage tone={item.imageTone} compact />
                  <div className="cart-item__content">
                    <p className="cart-item__seller">{item.sellerName}</p>
                    <h2 className="cart-item__title">{item.name}</h2>
                    <p className="cart-item__meta">
                      옵션: {item.optionName} · 수량 {item.quantity}
                    </p>
                    <div className="cart-item__footer">
                      <strong className="price-text">
                        {formatPrice(item.price * item.quantity)}
                      </strong>
                      <div className="cart-item__actions">
                        <span>{item.shippingNote}</span>
                        <button
                          className="button button--secondary"
                          type="button"
                          onClick={() => removeItem(item.id)}
                        >
                          삭제
                        </button>
                      </div>
                    </div>
                  </div>
                </article>
              ))}
            </div>
          )}
        </SectionPanel>

        <SummaryCard title="주문 요약">
          <div className="summary-line">
            <span>상품 합계</span>
            <strong>{formatPrice(totalPrice)}</strong>
          </div>
          <div className="summary-line">
            <span>프로모션 할인</span>
            <strong>-{formatPrice(discount)}</strong>
          </div>
          <div className="summary-line">
            <span>배송비</span>
            <strong>무료</strong>
          </div>
          <div className="summary-total">
            <span>결제 예상 금액</span>
            <strong>{formatPrice(finalTotal)}</strong>
          </div>
          <div className="summary-actions">
            <Link className="button button--accent" to="/checkout">
              주문하기
            </Link>
            <Link className="button button--secondary" to="/products">
              쇼핑 계속하기
            </Link>
          </div>
        </SummaryCard>
      </div>
    </main>
  );
}

import { Link } from "react-router-dom";
import { cartItems } from "@/features/catalog/data/mockProducts";
import { formatPrice } from "@/features/catalog/lib/format";
import { PageIntro } from "@/shared/ui/PageIntro";
import { ProductToneImage } from "@/shared/ui/ProductToneImage";
import { SectionPanel } from "@/shared/ui/SectionPanel";
import { SummaryCard } from "@/shared/ui/SummaryCard";

const itemTotal = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
const discount = 12000;
const finalTotal = itemTotal - discount;

export function CartPage() {
  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="장바구니"
        title="결제 직전까지 빠르게 검토할 수 있는 장바구니 화면"
        description="상품 정보, 옵션, 수량, 총액 요약을 같은 컴포넌트 시스템으로 묶었습니다."
      />

      <div className="detail-layout">
        <SectionPanel title="담은 상품" description="현재는 목업 데이터지만 카드 구조는 API 응답 배열에 바로 연결할 수 있게 만들었습니다.">
          <div className="cart-list">
            {cartItems.map((item) => (
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
                    <span>{item.shippingNote}</span>
                  </div>
                </div>
              </article>
            ))}
          </div>
        </SectionPanel>

        <SummaryCard title="주문 요약">
          <div className="summary-line">
            <span>상품 합계</span>
            <strong>{formatPrice(itemTotal)}</strong>
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
            <button className="button button--accent" type="button">
              주문하기 준비
            </button>
            <Link className="button button--secondary" to="/products">
              쇼핑 계속하기
            </Link>
          </div>
        </SummaryCard>
      </div>
    </main>
  );
}

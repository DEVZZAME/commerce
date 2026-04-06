import { useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { submitOrderRequest } from "@/features/checkout/api/orderApi";
import { validateCheckoutForm } from "@/features/checkout/lib/validateCheckoutForm";
import { useCart } from "@/features/cart/state/CartContext";
import { formatPrice } from "@/features/catalog/lib/format";
import { useAsyncState } from "@/shared/hooks/useAsyncState";
import type { CheckoutFormValues, OrderReceipt } from "@/shared/types/commerce";
import { ErrorBlock } from "@/shared/ui/ErrorBlock";
import { PageIntro } from "@/shared/ui/PageIntro";
import { SummaryCard } from "@/shared/ui/SummaryCard";

const initialValues: CheckoutFormValues = {
  recipientName: "",
  phone: "010-",
  address: "",
  detailAddress: "",
  deliveryRequest: "",
};

export function CheckoutPage() {
  const { items, totalPrice, clear } = useCart();
  const [values, setValues] = useState(initialValues);
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [receipt, setReceipt] = useState<OrderReceipt | null>(null);
  const asyncState = useAsyncState();

  const itemCount = useMemo(
    () => items.reduce((sum, item) => sum + item.quantity, 0),
    [items],
  );

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const validationErrors = validateCheckoutForm(values);

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    asyncState.start();
    setErrors({});

    try {
      const orderReceipt = await submitOrderRequest({
        ...values,
        totalPrice,
        itemCount,
      });

      setReceipt(orderReceipt);
      clear();
      asyncState.succeed();
    } catch (error) {
      asyncState.fail(
        error instanceof Error ? error.message : "주문 요청 중 문제가 발생했습니다.",
      );
    }
  }

  if (items.length === 0 && !receipt) {
    return (
      <main className="page-stack">
        <PageIntro
          eyebrow="주문 / 결제"
          title="장바구니가 비어 있습니다"
          description="주문을 진행하려면 먼저 상품을 장바구니에 담아주세요."
        />
        <ErrorBlock
          title="주문할 상품이 없습니다"
          description="상품 목록으로 이동해 상품을 먼저 담은 뒤 다시 시도해주세요."
        />
        <Link className="button button--accent" to="/products">
          상품 목록 보기
        </Link>
      </main>
    );
  }

  return (
    <main className="page-stack">
      <PageIntro
        eyebrow="주문 / 결제"
        title="배송 정보와 주문 요청 흐름을 실제 화면으로 연결"
        description="현재는 mock 주문 요청을 사용하지만, 폼 검증과 제출 상태, 성공 결과까지 실제 흐름처럼 동작합니다."
      />

      {receipt ? (
        <SummaryCard title="주문 요청 완료">
          <div className="summary-line">
            <span>주문 번호</span>
            <strong>{receipt.orderNumber}</strong>
          </div>
          <div className="summary-line">
            <span>접수 시각</span>
            <strong>{new Date(receipt.submittedAt).toLocaleString("ko-KR")}</strong>
          </div>
          <div className="summary-total">
            <span>결제 예정 금액</span>
            <strong>{formatPrice(receipt.totalPrice)}</strong>
          </div>
          <div className="summary-actions">
            <Link className="button button--accent" to="/products">
              상품 더 보기
            </Link>
          </div>
        </SummaryCard>
      ) : (
        <div className="detail-layout">
          <form className="section-panel checkout-form" onSubmit={handleSubmit}>
            <div className="section-panel__header">
              <div>
                <h2 className="section-panel__title">배송 정보 입력</h2>
                <p className="section-panel__description">
                  국문 사용자 기준으로 필수 항목과 오류 메시지를 단순하게 정리했습니다.
                </p>
              </div>
            </div>

            <label className="form-field">
              <span>받는 분</span>
              <input
                value={values.recipientName}
                onChange={(event) =>
                  setValues((current) => ({
                    ...current,
                    recipientName: event.target.value,
                  }))
                }
              />
              {errors.recipientName ? <small>{errors.recipientName}</small> : null}
            </label>

            <label className="form-field">
              <span>연락처</span>
              <input
                value={values.phone}
                onChange={(event) =>
                  setValues((current) => ({ ...current, phone: event.target.value }))
                }
              />
              {errors.phone ? <small>{errors.phone}</small> : null}
            </label>

            <label className="form-field form-field--wide">
              <span>기본 주소</span>
              <input
                value={values.address}
                onChange={(event) =>
                  setValues((current) => ({ ...current, address: event.target.value }))
                }
              />
              {errors.address ? <small>{errors.address}</small> : null}
            </label>

            <label className="form-field form-field--wide">
              <span>상세 주소</span>
              <input
                value={values.detailAddress}
                onChange={(event) =>
                  setValues((current) => ({
                    ...current,
                    detailAddress: event.target.value,
                  }))
                }
              />
              {errors.detailAddress ? <small>{errors.detailAddress}</small> : null}
            </label>

            <label className="form-field form-field--wide">
              <span>배송 요청사항</span>
              <textarea
                value={values.deliveryRequest}
                onChange={(event) =>
                  setValues((current) => ({
                    ...current,
                    deliveryRequest: event.target.value,
                  }))
                }
              />
              {errors.deliveryRequest ? <small>{errors.deliveryRequest}</small> : null}
            </label>

            {asyncState.status === "error" && asyncState.errorMessage ? (
              <p className="form-feedback form-feedback--error">
                {asyncState.errorMessage}
              </p>
            ) : null}

            <button
              className="button button--accent"
              type="submit"
              disabled={asyncState.status === "loading"}
            >
              {asyncState.status === "loading" ? "주문 요청 중..." : "주문 요청하기"}
            </button>
          </form>

          <SummaryCard title="최종 주문 요약">
            <div className="summary-line">
              <span>상품 수량</span>
              <strong>{itemCount}개</strong>
            </div>
            <div className="summary-line">
              <span>상품 합계</span>
              <strong>{formatPrice(totalPrice)}</strong>
            </div>
            <div className="summary-total">
              <span>결제 예정 금액</span>
              <strong>{formatPrice(totalPrice)}</strong>
            </div>
          </SummaryCard>
        </div>
      )}
    </main>
  );
}

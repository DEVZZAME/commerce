import { env } from "@/shared/config/env";
import type { CheckoutFormValues, OrderReceipt } from "@/shared/types/commerce";

type OrderRequestPayload = CheckoutFormValues & {
  totalPrice: number;
  itemCount: number;
};

export async function submitOrderRequest(
  payload: OrderRequestPayload,
): Promise<OrderReceipt> {
  if (env.useMockOrder) {
    await new Promise((resolve) => {
      window.setTimeout(resolve, 700);
    });

    return {
      orderNumber: `CM-${Date.now()}`,
      submittedAt: new Date().toISOString(),
      totalPrice: payload.totalPrice,
    };
  }

  throw new Error(
    "실제 주문 API는 아직 준비되지 않았습니다. 현재는 mock 주문 요청을 사용하세요.",
  );
}

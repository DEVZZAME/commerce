import type { CheckoutFormErrors, CheckoutFormValues } from "@/shared/types/commerce";

export function validateCheckoutForm(values: CheckoutFormValues) {
  const errors: CheckoutFormErrors = {};

  if (!values.recipientName.trim()) {
    errors.recipientName = "받는 분 이름을 입력해주세요.";
  }

  if (!/^010-\d{4}-\d{4}$/.test(values.phone.trim())) {
    errors.phone = "연락처는 010-0000-0000 형식으로 입력해주세요.";
  }

  if (!values.address.trim()) {
    errors.address = "기본 주소를 입력해주세요.";
  }

  if (!values.detailAddress.trim()) {
    errors.detailAddress = "상세 주소를 입력해주세요.";
  }

  if (values.deliveryRequest.length > 100) {
    errors.deliveryRequest = "배송 요청사항은 100자 이하로 입력해주세요.";
  }

  return errors;
}

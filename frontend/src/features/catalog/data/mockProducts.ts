import type { CartItem, ProductDetail } from "@/shared/types/commerce";

export const productDetails: ProductDetail[] = [
  {
    id: "keyboard-copper-frame",
    sellerId: "seller-one",
    sellerName: "셀러 원 아틀리에",
    name: "코퍼 프레임 키보드",
    subtitle: "차분한 금속 질감과 조용한 타건감을 가진 데스크 키보드",
    price: 189000,
    badge: "시그니처",
    category: "키보드",
    description: "브론즈 톤 상판과 무광 차콜 하판으로 정리된 프리미엄 데스크 키보드",
    shippingNote: "오늘 주문 시 48시간 내 출고",
    imageTone: "copper",
    storyTitle: "하루 종일 보이는 물건일수록 시각 피로가 적어야 합니다.",
    story:
      "코퍼 프레임 키보드는 금속 소재 특유의 존재감을 유지하면서도 지나치게 차갑지 않은 인상을 목표로 설계한 제품입니다. 흡음 구조와 낮은 반사율의 키캡 조합으로 장시간 업무 환경에 어울리는 밸런스를 만들었습니다.",
    highlights: [
      "흡음재와 가스켓 구조로 낮춘 타건 소리",
      "브론즈 톤 상판과 무광 차콜 하판 조합",
      "데스크 셋업과 어울리는 절제된 존재감",
    ],
    options: [
      {
        id: "red-switch",
        name: "레드 스위치",
        description: "가볍고 부드러운 리니어 키감",
        additionalPrice: 0,
        stock: 12,
      },
      {
        id: "blue-switch",
        name: "블루 스위치",
        description: "확실한 클릭감을 가진 촉각 피드백",
        additionalPrice: 12000,
        stock: 7,
      },
      {
        id: "brass-weight",
        name: "브라스 웨이트 세트",
        description: "하판 무게를 높여 더 묵직한 타건감 제공",
        additionalPrice: 22000,
        stock: 4,
      },
    ],
  },
  {
    id: "walnut-monitor-stand",
    sellerId: "forest-supply",
    sellerName: "포레스트 서플라이",
    name: "월넛 모니터 스탠드",
    subtitle: "원목 질감과 수납 기능을 함께 가진 데스크 정리용 스탠드",
    price: 82000,
    badge: "신규 입점",
    category: "디스플레이",
    description: "케이블 정리와 데스크 수납을 동시에 해결하는 원목 스탠드",
    shippingNote: "브랜드 직배송 · 3일 내 출고",
    imageTone: "forest",
    storyTitle: "시선은 올리고, 케이블은 숨기고, 책상은 더 단정하게.",
    story:
      "월넛 모니터 스탠드는 작업 중 자주 눈에 들어오는 모니터 하단 공간을 정리하기 위해 만든 제품입니다. 작은 서랍과 케이블 홀을 함께 배치해 기능과 질감을 모두 챙겼습니다.",
    highlights: [
      "책상 위 소품 수납 가능한 얕은 서랍",
      "케이블 홀 포함으로 후면 배선 정리 가능",
      "월넛 컬러 중심의 따뜻한 톤",
    ],
    options: [
      {
        id: "single-drawer",
        name: "싱글 서랍형",
        description: "기본 수납 구조",
        additionalPrice: 0,
        stock: 20,
      },
      {
        id: "double-drawer",
        name: "더블 서랍형",
        description: "좌우 분리 수납 가능",
        additionalPrice: 18000,
        stock: 9,
      },
    ],
  },
  {
    id: "canvas-cable-tray",
    sellerId: "atelier-goods",
    sellerName: "아틀리에 굿즈",
    name: "캔버스 케이블 트레이",
    subtitle: "하단 배선 정리에 적합한 패브릭 케이블 트레이",
    price: 34000,
    badge: "인기",
    category: "정리",
    description: "데스크 아래 배선과 멀티탭을 부드럽게 감추는 패브릭 트레이",
    shippingNote: "오후 3시 이전 주문 시 당일 출고",
    imageTone: "sand",
    storyTitle: "보이지 않아야 집중이 쉬운 것들이 있습니다.",
    story:
      "캔버스 케이블 트레이는 멀티탭과 충전 어댑터처럼 눈에 계속 걸리는 물건들을 부드럽게 정리하는 제품입니다. 패브릭 질감을 사용해 책상 하부가 지나치게 공업적으로 보이지 않도록 조정했습니다.",
    highlights: [
      "책상 하부를 가볍게 정리하는 패브릭 구조",
      "멀티탭과 충전기 수납에 적합한 깊이",
      "밝은 샌드 톤으로 다양한 데스크와 조화",
    ],
    options: [
      {
        id: "sand",
        name: "샌드 컬러",
        description: "밝은 우드 톤 책상과 조화로운 색상",
        additionalPrice: 0,
        stock: 23,
      },
      {
        id: "charcoal",
        name: "차콜 컬러",
        description: "어두운 책상과 어울리는 대비감",
        additionalPrice: 0,
        stock: 17,
      },
    ],
  },
];

export const cartItems: CartItem[] = [
  {
    id: "cart-item-1",
    productId: "keyboard-copper-frame",
    name: "코퍼 프레임 키보드",
    optionName: "레드 스위치",
    quantity: 1,
    price: 189000,
    sellerName: "셀러 원 아틀리에",
    shippingNote: "무료배송 · 48시간 내 출고",
    imageTone: "copper",
  },
  {
    id: "cart-item-2",
    productId: "canvas-cable-tray",
    name: "캔버스 케이블 트레이",
    optionName: "샌드 컬러",
    quantity: 2,
    price: 34000,
    sellerName: "아틀리에 굿즈",
    shippingNote: "당일 출고 가능",
    imageTone: "sand",
  },
];

export const productSummaries = productDetails.map(({ options: _options, story, storyTitle, highlights, ...summary }) => summary);

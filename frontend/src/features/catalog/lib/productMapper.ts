import type {
  ProductDetailResponse,
  ProductStatus,
  ProductSummaryResponse,
} from "@/features/catalog/types/productApi";
import type { ProductDetail, ProductSummary } from "@/shared/types/commerce";

const tones: ProductSummary["imageTone"][] = ["copper", "forest", "sand", "charcoal"];
const categories = ["키보드", "디스플레이", "정리", "데스크 액세서리"];

function resolveTone(index: number) {
  return tones[index % tones.length];
}

function resolveCategory(index: number) {
  return categories[index % categories.length];
}

function resolveBadge(status: ProductStatus, index: number) {
  if (status === "SOLD_OUT") return "품절 임박";
  if (status === "HIDDEN") return "비공개 예정";

  return ["추천", "신규", "인기", "에디터 픽"][index % 4];
}

function resolveShippingNote(status: ProductStatus) {
  if (status === "SOLD_OUT") {
    return "재입고 알림 신청 가능";
  }

  return "오늘 주문 시 48시간 내 출고";
}

export function mapProductSummary(
  product: ProductSummaryResponse,
  index: number,
): ProductSummary {
  return {
    id: product.id,
    sellerId: product.sellerId,
    sellerName: product.sellerName,
    name: product.name,
    subtitle: `${product.sellerName}의 ${resolveCategory(index)} 추천 상품`,
    price: product.price,
    badge: resolveBadge(product.status, index),
    category: resolveCategory(index),
    description: `${product.name}은(는) 현재 프론트 API 연동을 통해 조회된 실데이터입니다.`,
    shippingNote: resolveShippingNote(product.status),
    imageTone: resolveTone(index),
  };
}

export function mapProductDetail(
  product: ProductDetailResponse,
  index: number,
): ProductDetail {
  const summary = mapProductSummary(
    {
      id: product.id,
      sellerId: product.sellerId,
      sellerName: product.sellerName ?? "알 수 없는 셀러",
      name: product.name,
      price: product.price,
      status: product.status,
      createdAt: new Date().toISOString(),
    },
    index,
  );

  return {
    ...summary,
    description:
      product.description ?? `${product.name}에 대한 상세 설명이 아직 등록되지 않았습니다.`,
    storyTitle: `${product.name}을(를) 더 자세히 살펴보세요`,
    story:
      product.description ??
      "상세 설명 데이터가 아직 비어 있어 기본 안내 문구를 사용하고 있습니다.",
    highlights: [
      `${summary.sellerName}에서 제공하는 상품`,
      `${summary.shippingNote}`,
      `옵션 ${product.options.length}개 제공`,
    ],
    options: product.options.map((option) => ({
      id: option.id,
      name: option.name,
      description: option.description ?? "옵션 설명이 아직 등록되지 않았습니다.",
      additionalPrice: option.additionalPrice,
      stock: option.stockQuantity,
    })),
  };
}

import { requestJson } from "@/shared/api/http";
import type {
  ProductCreateRequest,
  ProductDetailResponse,
  ProductListPageResponse,
  ProductListQuery,
} from "@/features/catalog/types/productApi";

function toQueryString(query: ProductListQuery) {
  const searchParams = new URLSearchParams();

  if (query.keyword) searchParams.set("keyword", query.keyword);
  if (typeof query.page === "number") searchParams.set("page", String(query.page));
  if (typeof query.size === "number") searchParams.set("size", String(query.size));
  if (query.sortBy) searchParams.set("sortBy", query.sortBy);
  if (query.direction) searchParams.set("direction", query.direction);

  const result = searchParams.toString();

  return result ? `?${result}` : "";
}

export function fetchProducts(query: ProductListQuery) {
  return requestJson<ProductListPageResponse>(`/products${toQueryString(query)}`);
}

export function fetchProduct(productId: number) {
  return requestJson<ProductDetailResponse>(`/products/${productId}`);
}

export function createProduct(request: ProductCreateRequest) {
  return requestJson<ProductDetailResponse>("/products", {
    method: "POST",
    body: JSON.stringify(request),
  });
}

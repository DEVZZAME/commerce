import type { PageResponse } from "@/shared/types/api";

export type ProductStatus = "ON_SALE" | "SOLD_OUT" | "HIDDEN";

export type ProductOptionResponse = {
  id: number;
  name: string;
  additionalPrice: number;
  stockQuantity: number;
  description: string | null;
  optionOrder: number;
};

export type ProductSummaryResponse = {
  id: number;
  sellerId: number;
  sellerName: string;
  name: string;
  price: number;
  status: ProductStatus;
  createdAt: string;
};

export type ProductDetailResponse = {
  id: number;
  sellerId: number;
  sellerName: string | null;
  name: string;
  price: number;
  description: string | null;
  status: ProductStatus;
  options: ProductOptionResponse[];
};

export type ProductListPageResponse = PageResponse<ProductSummaryResponse>;

export type ProductListQuery = {
  keyword?: string;
  page?: number;
  size?: number;
  sortBy?: "createdAt" | "price" | "name";
  direction?: "ASC" | "DESC";
};

export type ProductCreateRequest = {
  sellerId: number;
  name: string;
  price: number;
  description: string;
};

import { productDetails } from "@/features/catalog/data/mockProducts";

export function findProductDetail(productId: number) {
  return productDetails.find((product) => product.id === productId);
}

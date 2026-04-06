import { productDetails } from "@/features/catalog/data/mockProducts";

export function findProductDetail(productId: string) {
  return productDetails.find((product) => product.id === productId) ?? productDetails[0];
}

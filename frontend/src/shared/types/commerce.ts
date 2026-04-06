export type ProductOption = {
  id: string;
  name: string;
  description: string;
  additionalPrice: number;
  stock: number;
};

export type ProductSummary = {
  id: string;
  sellerId: string;
  sellerName: string;
  name: string;
  subtitle: string;
  price: number;
  badge: string;
  category: string;
  description: string;
  shippingNote: string;
  imageTone: "copper" | "forest" | "sand" | "charcoal";
};

export type ProductDetail = ProductSummary & {
  storyTitle: string;
  story: string;
  highlights: string[];
  options: ProductOption[];
};

export type CartItem = {
  id: string;
  productId: string;
  name: string;
  optionName: string;
  quantity: number;
  price: number;
  sellerName: string;
  shippingNote: string;
  imageTone: ProductSummary["imageTone"];
};

export type NavigationItem = {
  to: string;
  label: string;
};

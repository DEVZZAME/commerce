export type ProductOption = {
  id: number;
  name: string;
  description: string;
  additionalPrice: number;
  stock: number;
};

export type ProductSummary = {
  id: number;
  sellerId: number;
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
  productId: number;
  name: string;
  optionName: string;
  optionId: number;
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

export type CheckoutFormValues = {
  recipientName: string;
  phone: string;
  address: string;
  detailAddress: string;
  deliveryRequest: string;
};

export type CheckoutFormErrors = Partial<Record<keyof CheckoutFormValues, string>>;

export type OrderReceipt = {
  orderNumber: string;
  submittedAt: string;
  totalPrice: number;
};

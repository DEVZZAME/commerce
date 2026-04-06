import {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useReducer,
  type PropsWithChildren,
} from "react";
import type { CartItem, ProductDetail } from "@/shared/types/commerce";

type CartState = {
  items: CartItem[];
};

type AddItemPayload = {
  product: ProductDetail;
  optionId: number;
  quantity: number;
};

type CartAction =
  | { type: "ADD_ITEM"; payload: AddItemPayload }
  | { type: "REMOVE_ITEM"; payload: { cartItemId: string } }
  | { type: "CLEAR" };

type CartContextValue = {
  items: CartItem[];
  totalPrice: number;
  addItem: (payload: AddItemPayload) => void;
  removeItem: (cartItemId: string) => void;
  clear: () => void;
};

const STORAGE_KEY = "commerce-front-cart";

const CartContext = createContext<CartContextValue | null>(null);

function readInitialState(): CartState {
  const stored = window.localStorage.getItem(STORAGE_KEY);

  if (!stored) {
    return { items: [] };
  }

  try {
    const parsed = JSON.parse(stored) as CartState;
    return { items: parsed.items ?? [] };
  } catch {
    return { items: [] };
  }
}

function cartReducer(state: CartState, action: CartAction): CartState {
  switch (action.type) {
    case "ADD_ITEM": {
      const { product, optionId, quantity } = action.payload;
      const option = product.options.find((item) => item.id === optionId);

      if (!option) {
        return state;
      }

      const existingItem = state.items.find(
        (item) => item.productId === product.id && item.optionId === optionId,
      );

      if (existingItem) {
        return {
          items: state.items.map((item) =>
            item.id === existingItem.id
              ? { ...item, quantity: item.quantity + quantity }
              : item,
          ),
        };
      }

      return {
        items: [
          ...state.items,
          {
            id: `${product.id}-${optionId}`,
            productId: product.id,
            name: product.name,
            optionId,
            optionName: option.name,
            quantity,
            price: product.price + option.additionalPrice,
            sellerName: product.sellerName,
            shippingNote: product.shippingNote,
            imageTone: product.imageTone,
          },
        ],
      };
    }
    case "REMOVE_ITEM":
      return {
        items: state.items.filter((item) => item.id !== action.payload.cartItemId),
      };
    case "CLEAR":
      return { items: [] };
    default:
      return state;
  }
}

export function CartProvider({ children }: PropsWithChildren) {
  const [state, dispatch] = useReducer(cartReducer, undefined, readInitialState);

  useEffect(() => {
    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
  }, [state]);

  const value = useMemo<CartContextValue>(
    () => ({
      items: state.items,
      totalPrice: state.items.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0,
      ),
      addItem(payload) {
        dispatch({ type: "ADD_ITEM", payload });
      },
      removeItem(cartItemId) {
        dispatch({ type: "REMOVE_ITEM", payload: { cartItemId } });
      },
      clear() {
        dispatch({ type: "CLEAR" });
      },
    }),
    [state.items],
  );

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCart() {
  const context = useContext(CartContext);

  if (!context) {
    throw new Error("useCart must be used within CartProvider");
  }

  return context;
}

import { Navigate, RouterProvider, createBrowserRouter } from "react-router-dom";
import { CartProvider } from "@/features/cart/state/CartContext";
import { CheckoutPage } from "@/pages/checkout/CheckoutPage";
import { AppLayout } from "@/widgets/layout/AppLayout";
import { CartPage } from "@/pages/cart/CartPage";
import { HomePage } from "@/pages/home/HomePage";
import { ProductDetailPage } from "@/pages/product-detail/ProductDetailPage";
import { ProductListPage } from "@/pages/product-list/ProductListPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      { index: true, element: <HomePage /> },
      { path: "products", element: <ProductListPage /> },
      { path: "products/:productId", element: <ProductDetailPage /> },
      { path: "cart", element: <CartPage /> },
      { path: "checkout", element: <CheckoutPage /> },
      { path: "publishing", element: <Navigate to="/products" replace /> },
    ],
  },
]);

function App() {
  return (
    <CartProvider>
      <RouterProvider router={router} />
    </CartProvider>
  );
}

export default App;

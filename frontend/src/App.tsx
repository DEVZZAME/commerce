import { Navigate, RouterProvider, createBrowserRouter } from "react-router-dom";
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
      { path: "publishing", element: <Navigate to="/products" replace /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;

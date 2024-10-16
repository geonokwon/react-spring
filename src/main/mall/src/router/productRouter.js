import {Navigate} from "react-router-dom";
import {lazy, Suspense} from "react";

const productRouter = () => {
  const Loading = <div>Loading....</div>
  const ProductList = lazy(() => import("../pages/products/ListPage"))
  const ProductAdd = lazy(() => import("../pages/products/AddPage"))

  return [
    {
      path: "",
      element: <Navigate to="/products/list" />
    },
    {
      path: "list",
      element: <Suspense fallback={Loading}><ProductList /></Suspense>
    },
    {
      path: "add",
      element: <Suspense fallback={Loading}><ProductAdd /></Suspense>
    }
  ]
}

export default productRouter;
import {createBrowserRouter} from "react-router-dom";
//지연로딩을 위해 사용
import {lazy, Suspense} from "react";
import todoRouter from "./todoRouter";
import productRouter from "./productRouter";


//아직 컴포넌트 처리가 끝나지 않았다면 화면에 간단히 Loading... 출력
const Loading = <div>Loading...</div>

//Main Page
const Main = lazy(() => import("../pages/MainPage"));

//About Page
const About = lazy(() => import("../pages/AboutPage"));

//Todo(IndexPage)
const TodoIndex = lazy(() => import("../pages/todo/IndexPage"));

//ProductIndexPage
const ProductIndex = lazy(() => import("../pages/products/IndexPage"));


// 라우터를 통해 어떤 경로(path)에는 어떤 컴포넌트를 보여줄 것인지를 결정!
const root = createBrowserRouter([
    {
        path: "",
        element: <Suspense fallback={Loading}><Main /></Suspense>
    },
    {
        path: "about",
        element: <Suspense fallback={Loading}><About /></Suspense>
    },
    {
        //children 속성을 이용해서 하위로 중첩적인 경로 설정 가능
        path: "todo",
        element: <Suspense fallback={Loading}><TodoIndex /></Suspense>,
        children: todoRouter()
    },
    {
        path: "products",
        element: <Suspense fallback={Loading}><ProductIndex /></Suspense>,
        children: productRouter()
    }

])
export default root;
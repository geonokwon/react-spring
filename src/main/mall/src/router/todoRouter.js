//중첩라우팅 분리 root 에 children 속성을 이용해서 중첩적인 라우팅 설정을 적용할수 있지만 페이지가 많아지면
//root.js 파일이 복잡해지는 단점을 해결하고자 별도의 함수에서 children 속성값에 해당하는 설정을 반환하도록 하는 방식

import {Suspense, lazy} from "react";
// todo/ 의 경로로 접근하는 경우 자동으로 todo/list 를 바라볼 수 있도록 처리해주면 <Outlet> 설정을 유지한 상태에서 사용이 가능
// Navigate 의 replace 속성을 사용해 특정 경로로 진입 시에 자동으로 리다이렉션 처리
import {Navigate} from "react-router-dom";

const Loading = <div>Loading....</div>
//Todo 의 하위 페이지
const TodoList = lazy(() => import("../pages/todo/ListPage"));
const TodoRead = lazy(() => import("../pages/todo/ReadPage"));
const TodoAdd = lazy(() => import("../pages/todo/AddPage"));
const TodoModify = lazy(() => import("../pages/todo/ModifyPage"));

const todoRouter = () => {
  return [
    {
      path: "list",
      element: <Suspense fallback={Loading}><TodoList /></Suspense>
    },
    {
      path: "add",
      element: <Suspense fallback={Loading}><TodoAdd /></Suspense>

    },
    {
      //URL Params 특정한 번호의 Todo를 조회할 경우 '/todo/read/33' 과같은 경로로 이동하는 방식
      //경로에 필요한 데이터가 있을때 ':' 을 활용
      path: "read/:tno",
      element: <Suspense fallback={Loading}><TodoRead /></Suspense>,
    },
    {
      path: "modify/:tno",
      element: <Suspense fallback={Loading}><TodoModify /></Suspense>
    },

    {
      // todo/이하의 경로가 지정되지 않았을 때 동작하는 빈 경로의 설정을 추가
      path: "",
      element: <Navigate replace to="list" />
    }
  ]
}
export default todoRouter;
//Outlet 사용시 중첩적인 라우팅 설정 시 레이아웃 유지 할 수 있음

import {useSearchParams} from "react-router-dom";
import ListComponent from "../../components/todo/ListComponent";

const ListPage = () => {
  /*
  '?' 이후에 나오는 쿼리스트링은 useSearchParams 를 이용해서 가져올수 있음
  const [queryParams] = useSearchParams();

  //쿼리 스트링으로 page 가 없으면 1 출력 , size 가 없으면 10 출력
  const page = queryParams.get('page') ? parseInt(queryParams.get('page')) : 1;
  const size = queryParams.get('size') ? parseInt(queryParams.get('size')) : 10;
  */
  return(
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">
        Todo List Page Component
      </div>

      <ListComponent />

    </div>
  )
}
export default ListPage;
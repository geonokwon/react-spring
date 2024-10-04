//목록 데이터 처리(페이지 처리, 링크 추가)
//페이지 번호/사이즈에 따라서 서버를 호출하고 결과 출력
//useEffect() 사용


import useCustomMove from "../../hooks/useCustomMove";
import {useEffect, useState} from "react";
import {getList} from "../../api/todoApi";
import PageComponent from "../common/PageComponent";

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  tototalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0
}
const ListComponent = () => {
  const {page, size, refresh, moveToList, moveToRead} = useCustomMove();

  const [serverData, setServerData] = useState(initState);

  useEffect(() => {

    getList({page, size}).then(data => {
      console.log(data);
      setServerData(data);
    })

  }, [page, size, refresh]);
  return (
    <div className="border-2 border-blue-500 mt-10 mr-2 ml-2">
      <div className="flex flex-wrap mx-auto justify-center p-6">
        {serverData.dtoList.map(todo =>
          <div key={todo.tno}
               className="w-full min-w-[400px] p-2 m-2 rounded shadow-md"
               onClick={() => moveToRead(todo.tno)}>
            <div className="flex">
              <div className="font-extrabold text-2xl p-2 w-1/12">
                {todo.tno}
              </div>
              <div className="text-1-xl m-1 p-2 w-8/12 font-extrabold">
                {todo.title}
              </div>
              <div className="text-1-xl m-1 p-2 w-2/10 font-medium">
                {todo.dueDate}
              </div>
            </div>
          </div>
        )}
      </div>
      <PageComponent serverData={serverData} movePage={moveToList}></PageComponent>
    </div>
  );
}
export default ListComponent;
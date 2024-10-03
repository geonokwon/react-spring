import {createSearchParams, useNavigate, useParams, useSearchParams} from "react-router-dom";
import {useCallback} from "react";
import ReadComponent from "../../components/todo/ReadComponent";

const ReadPage = () => {
  //특정 번호의 경로를 사용하는 useParams 를 이용해서 지정된 변수를 추출할 수 있음.
  const {tno} = useParams();

  /* useCustomMove() 에서 처리하므로 navigate 로 처리할 필요가 없어짐!
  //수정/삭제가 가능 한 /todo/modify/번호 경로로 이동할 수 있는 기능 추가
  const navigate = useNavigate();
  //쿼리 스트링도 함께 가지고 가야하는데 그렇지 못하다 useSearchParams 를 이용해 데이터 확인하고
  //createSearchParams 함수를 사용해 이동시에 필요한 쿼리스트링을 만들어내서 navigate 를 이용한 이동 시에 활용
  const [queryParam] = useSearchParams();
  const page = queryParam.get("page") ? parseInt(queryParam.get("page")) : 1;
  const size = queryParam.get("size") ? parseInt(queryParam.get("size")) : 10;

  const queryStr = createSearchParams({page, size}).toString();

  const moveToModify = useCallback((tno) => {
    navigate({
      pathname: `/todo/modify/${tno}`,
      search: queryStr
    })
  },[tno, page, size]);

  //조회화면에서 다시 목록화면으로 이동
  const moveToList = useCallback(() => {
    navigate({
      pathname: `/todo/list`,
      search: queryStr
    })
  }, [page, size]);
  */

  return (
    <div className="font-extrabold w-full bg-white mt-6">
      <div className="text-2xl">
        Todo Read Page Component {tno}
      </div>
      <ReadComponent tno={tno}/>
    </div>
  )
}
export default ReadPage;
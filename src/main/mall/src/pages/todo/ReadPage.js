import {useParams, useSearchParams} from "react-router-dom";

const ReadPage = () => {
  //특정 번호의 경로를 사용하는 useParams 를 이용해서 지정된 변수를 추출할 수 있음.
  // const {tno} = useParams();

  //'?' 이후에 나오는 쿼리스트링은 useSearchParams 를 이용해서 가져올수 있음
  const [queryParams] = useSearchParams();

  //쿼리 스트링으로 page 가 없으면 1 출력 , size 가 없으면 10 출력
  const page = queryParams.get('page') ? parseInt(queryParams.get('page')) : 1;
  const size = queryParams.get('size') ? parseInt(queryParams.get('size')) : 10;


  return (
    <div className="text-3xl font-extrabold">
      Todo Read Page Component {page} --- {size}
    </div>
  )
}
export default ReadPage;
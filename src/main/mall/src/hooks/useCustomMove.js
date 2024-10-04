//커스텀훅 제작(조회하면에서 다시 목록 화면으로 이동해야하는 기능) 나중에 등록/수정/삭제 페이지에서 사용하기 위해서 커스텀 훅사용)

import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import {useState} from "react";

const getNum = (param, defaultValue) => {
  if (!param){
    return defaultValue;
  }
  return parseInt(param);
}

const useCustomMove = () => {
  const navigate = useNavigate();

  const [refresh, setRefresh] = useState(false);

  const [queryParams] = useSearchParams();

  const page = getNum(queryParams.get("page"), 1);
  const size = getNum(queryParams.get("size"), 10);

  const queryDefault = createSearchParams({page, size}).toString();

  //List Page 의 목록 불러오기
  const moveToList = (pageParam) => {
    let queryStr = "";

    if (pageParam){
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);
      queryStr = createSearchParams({page: pageNum, size: sizeNum}).toString();
    }
    else {
      queryStr = queryDefault;
    }
    navigate({pathname: `../list`, search: queryStr});
    setRefresh(!refresh);
  }

  const moveToModify = (num) => {
    console.log(queryDefault);

    navigate({
      pathname: `../modify/${num}`,
      search: queryDefault //수정시에 기존의 쿼리 스트링 유지를 위해
    })
  }

  //리스트의 목록을 클릭시 읽기 페이지 이동
  const moveToRead = (num) => {

    console.log(queryDefault);

    navigate({
      pathname: `../read/${num}`,
      search: queryDefault
    });

  }

  return {moveToList, moveToModify, moveToRead, page, size, refresh} //moveToList, moveToModify
}

export default useCustomMove;
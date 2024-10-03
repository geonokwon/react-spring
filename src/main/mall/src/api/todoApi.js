//Axios 이용하는 부분은 컴포넌트에서 쉽게 사용할 수 있도록 별도의 함수들로 제작
import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/todo`;
export const getOne = async (tno) => {
  const res = await axios.get(`${prefix}/${tno}`);
  return res.data;
}

export const getList = async (pageParam) => {
  const {page,size} = pageParam;
  const res = await axios.get(`${prefix}/list`,{params:{
    page: page,
    size: size
  }})
  return res.data;
}
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

//등록페이지 Axios 함수
export const postAdd = async (todoObj) => {
  const res = await axios.post(`${prefix}/`, todoObj);
  return res.data
}

//삭제 Axios 함수
export const deleteOne = async (tno) => {
  const res = await axios.delete(`${prefix}/${tno}`)
  return res.data
}

//수정 Axios 함수
export const putOne = async (todo) => {
  const res = await axios.put(`${prefix}/${todo.tno}`, todo)
  return res.data
}




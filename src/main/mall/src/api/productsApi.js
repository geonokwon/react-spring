import {API_SERVER_HOST} from "./todoApi";
import axios from "axios";

const host = `${API_SERVER_HOST}/api/products`;

export const postAdd = async (product) => {
  //multipart/form-data 를 해더에 선언해야함 !
  //기본이 application/json 을 이용함
  const header = {headers: {'Content-Type': 'multipart/form-data'}};

  const res = await axios.post(`${host}/`, product, header)

  return res;
}
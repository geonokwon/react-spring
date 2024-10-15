//상품의 AddComponent
import {useRef, useState} from "react";
import {postAdd} from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
  pname: "",
  pdesc: "",
  price: "",
  files: []
}

const AddComponent = () => {

  const [product, setProduct] = useState({...initState})
  const uploadRef = useRef()

  //등록중 지연시간 때 모여줄 모달을 useState 로 관리해서 데이터를 저장완료하면 사라지게 만듬!
  const [fetching, setFetching] = useState(false);
  //등록/수정/삭제 의경우 처리 결과를 보여줄 모달
  const [result, setResult] = useState(null);
  //이동을 위한 함수
  const {moveToList} = useCustomMove()

  const handleChangeProduct = (e) => {
    product[e.target.name] = e.target.value
    setProduct({...product})
  }

  const handleClickAdd = (e) => {
    const files = uploadRef.current.files
    const formData = new FormData()

    for (let i = 0; i < files.length; i++){
      formData.append("files", files[i]);
    }

    //other data
    formData.append("pname", product.pname)
    formData.append("pdesc", product.pdesc)
    formData.append("price", product.price)

    console.log(formData)

    setFetching(true)

    postAdd(formData).then(response => {
      console.log(response)
      setFetching(false)
      //반환 객체 Result 에 담아서 등록 완료 모달 보여주기
      setResult(response.data.result)
    })
  }

  const closeModal = () => {
    setResult(null);
    //모달창 닫히면 리스트 페이지로 이동
    moveToList({page:1})
  }

  return(
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {fetching ? <FetchingModal/> : <></>}
      {result ? <ResultModal title={'Product Add Result'}
                             content={`${result}번 등록 완료`}
                             callbackFn={closeModal} /> : <></>
      }
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">
            Product Name
          </div>
          <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                 name="pname"
                 type={"text"}
                 onChange={handleChangeProduct}
                 value={product.name}
          />
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">
            Desc
          </div>
          <textarea className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
                    name="pdesc"
                    rows="4"
                    onChange={handleChangeProduct}
                    value={product.pdesc}
          >
            {product.pdesc}
          </textarea>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">
            Price
          </div>
          <input className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                 name="price"
                 type={"number"}
                 value={product.price}
                 onChange={handleChangeProduct}
          />
        </div>
      </div>

      <div className="flex justify-end">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">
            Files
          </div>
          <input ref={uploadRef}
                 className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-me"
                 type={"file"}
                 multiple={true}
          />
        </div>
      </div>

      <div className="flex justify-end">
        <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
          <button type="button"
                  className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
                  onClick={handleClickAdd}
          >
            ADD
          </button>
        </div>
      </div>

    </div>
  )
}

export default AddComponent;
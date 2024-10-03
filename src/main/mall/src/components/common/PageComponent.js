//페이징 처리
//페이징의 경우 모든 목록 관련된 기능에서 사용해야 하므로 공통의 컴포넌트로 제작해서 사용하는것이 유용하다.
//PageComponent 는 ListComponent 가 받아오는 서버의 데이터와 useCustomMove() 에서 만들어진 moveToList() 를
//movePage 속성으로 전달받도록 구성하고 이를 활용

const PageComponent = ({serverData, movePage}) => {
  return (
    <div className="m-6 flex justify-center">
      {serverData.prev ?
        <div className="m-2 p-2 w-16 text-center font-bold text-blue-400" onClick={() => movePage( {page:serverData.prevPage})}>
          Prev
        </div> : <></>
      }
      {serverData.pageNumList.map(pageNum =>
        <div key={pageNum}
             className={`m-2 p-2 w-12 text-center rounded shadow-md text-white ${serverData.current === pageNum ? 'bg-gray-500' : 'bg-blue-400'}`}
             onClick={() => movePage({page: pageNum})}>
          {pageNum}
        </div>
      )}
      {serverData.next ?
        <div className="m-2 p-2 w-16 text-center font-bold text-blue-400" onClick={() => movePage({page: serverData.nextPage})}>
          Next
        </div> : <></>
      }
    </div>
  );
}
export default PageComponent;
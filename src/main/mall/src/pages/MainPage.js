import BasicLayout from "../layouts/BasicLayout";

const MainPage = () => {
  return (
    <BasicLayout>
      {/*
      기존의 HTML a태그는 브로우저 주소창을 변경하면서 애플리케이션 자체의 로딩부터 새로 시작하기때문에
      React-router 에서는 사용하지 않도록 주의해야함 (SPA) -> 경로에 대한 링크는 Link 사용
      <div className="flex">
          <Link to="/about">About</Link>
      </div>
      */}
      <div className="text-3xl">
          <div>Main Page</div>
      </div>
    </BasicLayout>
  );
}
export default MainPage;

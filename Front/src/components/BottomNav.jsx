import React from "react";
import "../CSS/BottomNav.css";
// FontAwesomIcon 컴포넌트를 사용하기 위해 import
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { faBowlFood } from "@fortawesome/free-solid-svg-icons";
import { faCartPlus } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from 'react-router-dom';

const BottomNav = () => {
  const navigate = useNavigate();

  const handleNavigationMainPage = () => {
    navigate('/');
  };

  const handleNavigationAllDietPage = () => {
    navigate('/alldiet');
  };

  return (
    <nav className="wrapper">
      {/* 하단 네비게이션 최상위 태그 */}
      <div className="button-naming">
        <FontAwesomeIcon icon={faBars} onClick={handleNavigationAllDietPage} />
        전체식단
      </div>

      <div className="button-naming">
        <FontAwesomeIcon icon={faCartPlus} />
        주문하기
      </div>
      <div className="button-naming" onClick={handleNavigationMainPage}>
        <FontAwesomeIcon icon={faHouse} />
        홈
      </div>
      <div className="button-naming">
        <FontAwesomeIcon icon={faBowlFood} />
        내 식단
      </div>
      <div className="button-naming">
        <FontAwesomeIcon icon={faUser} />
        내 정보
      </div>

    </nav>
  );
};

export default BottomNav;
import React from "react";
import "../CSS/BottomNav.css";
// FontAwesomIcon 컴포넌트를 사용하기 위해 import
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse } from "@fortawesome/free-solid-svg-icons";
import { faBowlFood } from "@fortawesome/free-solid-svg-icons";
import { faCartPlus } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faBars } from "@fortawesome/free-solid-svg-icons";

const BottomNav = () => {
  return (
    <nav className="wrapper">
      {/* 하단 네비게이션 최상위 태그 */}
      <div className="button-naming">
        <FontAwesomeIcon icon={faBars} size="lg" />
        <span className="buttom-text">
          전체식단
        </span>
      </div>

      <div className="button-naming">
        <FontAwesomeIcon icon={faCartPlus} size="lg" />
        <span className="buttom-text">
          주문하기
        </span>
      </div>
      <div className="button-naming">
        <FontAwesomeIcon icon={faHouse} size="lg" />
        <span className="buttom-text">
          홈
        </span>
      </div>
      <div className="button-naming">
        <FontAwesomeIcon icon={faBowlFood} size="lg" />
        <span className="buttom-text">
          내 식단
        </span>
      </div>
      <div className="button-naming">
        <FontAwesomeIcon icon={faUser} size="lg" />
        <span className="buttom-text">
          마이페이지
        </span>
      </div>
    </nav>
  );
};

export default BottomNav;
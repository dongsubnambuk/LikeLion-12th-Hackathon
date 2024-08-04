import React, { useEffect, useState } from "react";
import "../CSS/BottomNav.css";
// FontAwesomeIcon 컴포넌트를 사용하기 위해 import
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouse, faBowlFood, faCartPlus, faUser, faBars } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from 'react-router-dom';

const BottomNav = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, []);

  const handleNavigationMainPage = () => {
    navigate('/');
  };

  const handleNavigationAllDietPage = () => {
    if (isLoggedIn) {
      navigate('/alldiet');
    } else {
      navigate('/login');
    }
     
  };


  const handleNavigationMyPage = () => {
    if (isLoggedIn) {
      navigate('/mypage');
    } else {
      navigate('/login');
    }
  };

  const handleNavigationMyFood = () => {
    if (isLoggedIn) {
      navigate('/weeklyfoodmenu');
    } else {
      navigate('/login');
    }
  };

  const handleNavigationOrder = () => {
    if (isLoggedIn) {
      navigate('/dietselection');
    } else {
      navigate('/login');
    }
  };

  return (
    <nav className="wrapper">
      {/* 하단 네비게이션 최상위 태그 */}
      <div className="button-naming" onClick={handleNavigationAllDietPage}>
        <FontAwesomeIcon icon={faBars} />
        전체식단
      </div>

      <div className="button-naming" onClick={handleNavigationOrder}>
        <FontAwesomeIcon icon={faCartPlus} />
        주문하기
      </div>
      <div className="button-naming" onClick={handleNavigationMainPage}>
        <FontAwesomeIcon icon={faHouse} />
        홈
      </div>
      <div className="button-naming" onClick={handleNavigationMyFood}>
        <FontAwesomeIcon icon={faBowlFood} />
        내 식단
      </div>
      <div className="button-naming" onClick={handleNavigationMyPage}>
        <FontAwesomeIcon icon={faUser} />
        내 정보
      </div>
    </nav>
  );
};

export default BottomNav;

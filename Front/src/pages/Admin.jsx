import React, { useState, useEffect } from "react";
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import '../CSS/Admin.css';
import { useNavigate } from 'react-router-dom';

function Admin() {
  const [price, setPrice] = useState('');
  const [loading, setLoading] = useState(false);
  const [item, setItem] = useState({
    name: "",
    main1: "",
    main2: "",
    price: "",
    side1: "",
    side2: "",
    side3: "",
    calories: "",
    carbohydrate: "",
    protein: "",
    fat: "",
    sugar: "",
    sodium: "",
    image: ""
  });
  const [itemVisible, setItemVisible] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    navigate("/");
  };

  const handlePriceChange = (e) => {
    const selectedPrice = e.target.value;
    setPrice(selectedPrice);
    console.log(selectedPrice);
  };

  const handleButtonClick = async () => {
    setLoading(true);
    try {
        console.log(price)
      const response = await fetch(`http://15.165.192.29:12000/api/aiDiet/newDiet?price=${price}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        // body: JSON.stringify({ price: price })
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setItem({
        name: data.name,
        main1: data.main1,
        main2: data.main2,
        price: `${price}원`,
        side1: data.side1,
        side2: data.side2,
        side3: data.side3,
        calories: data.calories,
        carbohydrate: data.carbohydrate,
        protein: data.protein,
        fat: data.fat,
        sugar: data.sugar,
        sodium: data.sodium,
        image: data.image
      });
      setItemVisible(true);
    } catch (error) {
      console.error('Fetch error:', error);
    }
    setLoading(false);
  };

  useEffect(() => {
    const fetchInitialData = async () => {
      const token = localStorage.getItem("token");
      const email = localStorage.getItem("email");

      try {
        const response = await fetch(`http://3.37.64.39:8000/users?email=${email}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
          }
        });

        const result = await response.json();

        if (response.status === 200) {
          // 로그인 성공 시 필요한 처리
        } else {
          console.log("로그인 실패");
          alert("로그인 실패: " + result.message);
        }
      } catch (error) {
        console.error('Fetch error:', error);
      }
    };

    fetchInitialData();
  }, []);



const style = {
  height: '100vh',
  overflowY: 'auto', // 내용이 많을 경우 스크롤 가능하게 설정
};

const imageContainerStyle = {
    width: '100%', // 컨테이너 너비를 100%로 설정
    height: '50vh', // 컨테이너 높이를 적절히 늘림
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    overflow: 'hidden', // 이미지를 잘라내기 위해 오버플로 숨김
    marginBottom: '10px' // 이미지와 이름 사이의 여백
  };
  
  const imageStyle = {
    width: '100%', // 컨테이너 너비를 100%로 설정
    height: '30vh', // 컨테이너 높이를 적절히 늘림

    objectFit: 'contain' // 이미지를 컨테이너에 맞게 조정
  };
const titleStyle = {
  fontSize: '14px' // 원하는 크기로 변경
};


  // 식단서버 post fetch
  const handleApply = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch('http://3.37.64.39:8000/diet', { // 서버 URL을 실제 API 엔드포인트로 변경하세요
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: item.name,
          main1: item.main1,
          main2: item.main2,
          price: item.price,
          side1: item.side1,
          side2: item.side2,
          side3: item.side3,
          calories: item.calories,
          carbohydrate: item.carbohydrate,
          protein: item.protein,
          fat: item.fat,
          sugar: item.sugar,
          sodium: item.sodium,
          image: item.image
        }),
      });

      const result = await response.json();

      if (response.status === 201) {
        console.log("적용 성공");
      } else {
        console.log("적용 실패");
        alert("적용하기 실패: " + result.message);
      }
    } catch (error) {
      console.error('Fetch error:', error);
    }
  };

  return (
    <>
      <Header />
      <div className="price-input">
        <select
          id="price"
          value={price}
          className="admin-price"
          onChange={handlePriceChange}
        >
          <option value="">가격을 선택해주세요</option>
          <option value="4000">4000원</option>
          <option value="5500">5500원</option>
          <option value="7000">7000원</option>
        </select>
        <div className="price-btn">
          <button onClick={handleButtonClick} className="admin-btn">생성하기</button>
        </div>
      </div>

      {loading && (
        <div className="spinner-container">
          <div className="spinner"></div>
        </div>
      )}
      {itemVisible && !loading && (
        <>
          <div className="DIPcontainer" style={style}>
            <div className="DIPlistContainer">
            <div className="admin-DIPheader" style={style}>
                {item.image && (
                    <div style={imageContainerStyle}>
                    <img src={item.image} className="admin-DIPtitleImage" alt="menu" style={imageStyle} />
                    </div>
                )} {/* 이미지 추가 */}
</div>
<div className="admin-DIPtitle" style={titleStyle}>{item.name}</div> {/* 메뉴 이름 추가 */}
              <div className="DIPmenu">
                <span style={{ fontWeight: 600 }}>&lt; 메인 메뉴 &gt;</span>
                <br /><br />
                {item.main1}<br />
                {item.main2}
              </div>
              <div className="DIPmenu">
                <span style={{ fontWeight: 600 }}>&lt; 반찬 &gt;</span>
                <br /><br />
                {item.side1}<br />
                {item.side2}<br />
                {item.side3}
              </div>
              <div className="DIPnutrient">
                <span style={{ fontWeight: 600 }}>&lt; 영양성분 &gt;</span>
                <br /><br />
                칼로리: {item.calories}<br />
                탄수화물: {item.carbohydrate}<br />
                단백질: {item.protein}<br />
                지방: {item.fat}<br />
                당: {item.sugar}<br />
                나트륨: {item.sodium}<br />
              </div>
              <div className="DIPprice">
                <span style={{ fontSize: 18, fontWeight: 600 }}>가격 : {item.price}</span>
              </div>
            </div>
          </div>
          <div className="post-all">
            <button className="post-all-btn" onClick={handleApply}>적용하기</button>
          </div>
        </>
      )}
      <div className="admin-logout">
        <span onClick={handleLogout}>로그아웃</span>
      </div>
    </>
  );
}

export default Admin;

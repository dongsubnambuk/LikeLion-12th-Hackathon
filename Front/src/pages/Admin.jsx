import React, { useState, useEffect } from "react";
import '../CSS/Admin.css';
import '../CSS/DietInfoPage.css';
import { useNavigate } from 'react-router-dom';
import { Card, Typography, Divider } from 'antd';
const { Title, Paragraph } = Typography;

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

  //생성하기 fetch
  const handleButtonClick = async () => {
    const token = localStorage.getItem("token");
    setLoading(true);
    try {
        console.log(price)
      const response = await fetch(`http://3.37.64.39:8000/api/meal/food-menu?price=${price}
      `, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        },
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

  //user정보 가져오는 fetch
  useEffect(() => {
    const fetchInitialData = async () => {
      const token = localStorage.getItem("token");
      const email = localStorage.getItem("email");

      try {
        const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
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
  height: '70vh',
  overflowY: 'auto', // 내용이 많을 경우 스크롤 가능하게 설정
};





  return (
    <>
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
                <Card className="DIPlistContainer">
                    <div className="DIPimageContainer">
                    <img src={item.image} className="DIPtitleImage" alt="foodimage" />
                    </div>
                    <Title level={4} className="DIPtitle">{item.name}</Title>
                   
                    <Divider/>
                    <Title level={4} className="DIPsectionTitle">메뉴 소개</Title>
                    <Paragraph>메인 메뉴1: {item.main1}</Paragraph>
                    <Paragraph>메인 메뉴2: {item.main2}</Paragraph>
                    <Paragraph>사이드 메뉴1: {item.side1}</Paragraph>
                    <Paragraph>사이드 메뉴2: {item.side2}</Paragraph>
                    <Paragraph>사이드 메뉴3: {item.side3}</Paragraph>
                    <Divider />
                    <Title level={4} className="DIPsectionTitle">영양성분</Title>
                    <div class="nutrientContainer">
                        <div class="nutrientListContainer">
                            <div class="nutrientList">
                                <div class="nutrientItem carbohydrates">탄수화물 {item.carbohydrate}</div>
                                <div class="nutrientItem protein">단백질 {item.protein}</div>
                                <div class="nutrientItem fat">지방 {item.fat}</div>
                                <div class="nutrientItem sugar">당류 {item.sugar}</div>
                                <div class="nutrientItem sodium">나트륨 {item.sodium}</div>
                            </div>
                        </div>
                    </div>
                    <Divider />
                    <div className="DIPfooter">
                    <span className="calories-icon-left">🔥</span>
                        <div className="DIPcalories">칼로리: {item.calories}</div>
                        <span className="calories-icon-right">🔥</span>
                        <div className="DIPprice">가격: {item.price}</div>
                    </div>
                </Card>
            </div>
            <div className="post-all">
            <button className="post-all-btn" >적용하기</button>
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

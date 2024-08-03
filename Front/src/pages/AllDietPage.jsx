import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

function AllDietPage() {
    const navigate = useNavigate();

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };

    const [mealData, setMealData] = useState([]);

    useEffect(() => {
        const handleGet = async () => {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://3.37.64.39:8000/api/meal/meal-plans/weekly`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json();

            if (response.status === 200) {
                setMealData(result.dailyMealPlans);
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleGet();
    }, []);

    // 데이터가 로딩 중일 때 보여줄 화면
    if (mealData.length === 0) {
        return <div>Loading...</div>;
    }

    // mealData에서 원하는 형식으로 item 배열을 생성
    const items = mealData.flatMap(day =>
        day.mealOptions.flatMap(option =>
            option.foodMenus.slice(0, 1) // foodMenus의 첫 번째 항목만 사용
        )
    );

    return (
        <>
            <Header />

            <div className="ADPcontainer">
                <div className="ADPlistContainer">
                    {items.map((item, index) => (
                        <div key={index} className="ADPitemCard" onClick={() => handleItemClick(item)}>
                            <div className="ADPitemTitle">{item.name}</div>
                            <div className="ADPitemImage">
                                <img src={logo} style={{ width: '100%', height: '100%' }} alt="logo" />
                            </div>
                            <div className="ADPitemPrice">{item.price}</div>
                        </div>
                    ))}
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default AllDietPage;

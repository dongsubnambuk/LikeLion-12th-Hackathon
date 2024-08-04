import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Card, Spin } from 'antd';
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';

const { Meta } = Card;

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
                console.log(result);
            } else {
                console.log("실패");
                alert("실패: " + result.message);
            }
        };
        handleGet();
    }, []);

    if (mealData.length === 0) {
        return <Spin size="large" tip="Loading..." />;
    }

    const items = mealData.flatMap(day =>
        day.mealOptions.flatMap(option =>
            option.foodMenus.slice(0, 1)
        )
    );

    return (
        <>
            <Header />

            <div className="ADPcontainer">
                <div className="ADPlistContainer">
                    {items.map((item, index) => (
                        <Card
                            key={index}
                            hoverable
                            className="ADPitemCard"
                            onClick={() => handleItemClick(item)}
                        >
                            <div className="ADPitemImageContainer">
                                <img src={logo} alt="logo" className="ADPitemImage" />
                            </div>
                            <Meta title={<div className="ADPitemTitle">{item.name}</div>} description={`가격: ${item.price}`} className="ADPitemMeta" />
                        </Card>
                    ))}
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default AllDietPage;

import React from "react";
import { useNavigate } from "react-router-dom";
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function AllDietPage() {
    const items = Array(30).fill({ title: "김치찌개 정식", price: "3,840원" });
    const navigate = useNavigate();

    const handleItemClick = (item) => {
        navigate(`/dietinfo`, { state: { item } });
    };

    return (
        <>
            <Header />

            <div className="ADPcontainer">
                <div className="ADPlistContainer">
                    {items.map((item, index) => (
                        <div key={index} className="ADPitemCard" onClick={() => handleItemClick(item)}>
                            <div className="ADPitemTitle">{item.title}</div>
                            <div className="ADPitemImage" />
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

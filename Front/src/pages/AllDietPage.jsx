import React from "react";
import '../CSS/AllDietPage.css';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';

function AllDietPage() {
    const items = Array(30).fill({ title: "김치찌개 정식", price: "3,840원" });

    return (
        <>
            <Header />

            <div className="container">
                <div className="listContainer">
                    {items.map((item, index) => (
                        <div key={index} className="itemCard">
                            <div className="itemTitle">{item.title}</div>
                            <div className="itemImage"/>
                            <div className="itemPrice">{item.price}</div>
                        </div>
                    ))}
                </div>
            </div>

            <BottomNav />
        </>
    );
}

export default AllDietPage;

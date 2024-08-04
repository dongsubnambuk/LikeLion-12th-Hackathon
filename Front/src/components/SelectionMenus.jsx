import React, { useState, useEffect } from 'react';
import SelectionMenuCard from './SelectionMenuCard';
import '../CSS/SelectionMenus.css';

const SelectionMenus = ({ mealCardData, optionIndex, dateIndex }) => {
    // Use a state to track if the data is loaded
    const [dataLoaded, setDataLoaded] = useState(false);

    useEffect(() => {
        // Check if data is loaded and indices are valid
        if (mealCardData[dateIndex] && mealCardData[dateIndex].mealOptions[optionIndex]) {
            setDataLoaded(true);
        } else {
            setDataLoaded(false);
        }
        //console.log(mealCardData[dateIndex]);
        //console.log(mealCardData[dateIndex]?.mealOptions[optionIndex]);
    }, [mealCardData, dateIndex, optionIndex]);

    if (!dataLoaded) {
        return <div>Loading...</div>; // Or any placeholder for loading state
    }

    return (
        <div className="selection-menu-container">
            <p className="selection-menu-date">메뉴를 선택해주세요.</p>
            {mealCardData[dateIndex].mealOptions[optionIndex].foodMenus.map((mealData, index) => (
                <SelectionMenuCard
                    meals={mealData}
                    key={index}
                    index={index + 1}
                    dateIndex={dateIndex}
                    optionIndex={optionIndex}
                // onChangeMenu={(delta) => handleChangeMenu(index, delta)}
                />
            ))}
        </div>
    );
};

export default SelectionMenus;

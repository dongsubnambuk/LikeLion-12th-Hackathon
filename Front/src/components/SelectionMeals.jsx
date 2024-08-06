import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import SelectionMealCard from './SelectionMealCard';
import '../CSS/SelectionMeals.css';

const SelectionMeals = ({ mealCardData, dateIndex , updateMealData}) => {
    // const [defaultMenuIndex, setDefaultMenuIndex] = useState(0);
    // const [defaultDateIndex, setDefaultDateIndex] = useState(0);
    // const [defaultoptionIndex, setDefaultOptionIndex] = useState(0);

    // const [localMealCardData, setLocalMealCardData] = useState(mealCardData);

    // const location = useLocation();

    // const swapArrayElements = (arr, index1, index2) => {
    //     const newArr = [...arr]; // 원본 배열을 복사해서 새로운 배열을 만듦
    //     const temp = newArr[index1]; // 첫 번째 요소를 임시 변수에 저장
    //     newArr[index1] = newArr[index2]; // 첫 번째 위치에 두 번째 요소를 할당
    //     newArr[index2] = temp; // 두 번째 위치에 첫 번째 요소를 할당
    //     return newArr;
    // }

    // useEffect(() => {
    //     // location.state에서 전달된 값을 확인하고 상태를 설정합니다.
    //     if (location.state) {
    //         const { dateIndex: stateDateIndex, optionIndex: stateOptionIndex, menuIndex: stateMenuIndex } = location.state;
    //         console.log('전달값 : ', location.state);
    //         setDefaultMenuIndex(stateMenuIndex); // Ex) 아침의 메뉴 세개번호
    //         setDefaultOptionIndex(stateOptionIndex);

    //         const updatedFoodMenus = swapArrayElements(localMealCardData.mealOptions[stateOptionIndex].foodMenus, 0, stateMenuIndex);

    //         const updatedMealCardData = {
    //             ...localMealCardData,
    //             mealOptions: localMealCardData.mealOptions.map((option, idx) => 
    //                 idx === stateOptionIndex ? { ...option, foodMenus: updatedFoodMenus } : option
    //             )
    //         };

    //         setLocalMealCardData(updatedMealCardData);

    //         // 상위 컴포넌트의 상태 업데이트 함수 호출
    //         updateMealData(updatedMealCardData);

    //         setDefaultDateIndex(stateDateIndex);
    //         setDefaultOptionIndex(stateOptionIndex);
    //         //console.log('밀 데이터', mealCardData.mealOptions[defaultMenuIndex].foodMenus[defaultoptionIndex])
    //         console.log('밀 데이터', mealCardData);


    //     }
    // }, [location.state]);

    return (
        <div className="selection-meals-container">
            <p className="selection-meals-date">{mealCardData.day}</p>
            {mealCardData.mealOptions.map((mealData, index) => (
                <SelectionMealCard
                    meals={mealData.foodMenus[0]}
                    mealType={mealData.mealType}
                    key={index}
                    optionIndex={index}
                    dateIndex={dateIndex}
                />
            ))}
        </div>

    );
};

export default SelectionMeals;

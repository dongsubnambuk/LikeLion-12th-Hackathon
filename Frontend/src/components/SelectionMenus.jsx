import SelectionMenuCard from './SelectionMenuCard';
import '../CSS/SelectionMenus.css';

const SelectionMenus = ({ mealCardData, optionIndex, dateIndex }) => {

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
                />
            ))}
        </div>
    );
};

export default SelectionMenus;

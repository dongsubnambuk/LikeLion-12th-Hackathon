package Service;

import DTO.FoodMenuDTO;

import java.util.List;

public interface FoodMenuService {
    public FoodMenuDTO createFoodMenu(FoodMenuDTO foodMenuDTO);

    public FoodMenuDTO getFoodMenuById(Long id);

    public List<FoodMenuDTO> getFoodMenusByUserId(Long userId);

    public FoodMenuDTO updateFoodMenu(FoodMenuDTO foodMenuDTO);

    public void deleteFoodMenu(Long id);
}

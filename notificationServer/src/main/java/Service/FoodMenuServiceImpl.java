package Service;

import DAO.FoodMenuDAO;
import DTO.FoodMenuDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuDAO foodMenuDAO;

    public FoodMenuServiceImpl(FoodMenuDAO foodMenuDAO) {
        this.foodMenuDAO = foodMenuDAO;
    }

    @Override
    public FoodMenuDTO createFoodMenu(FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.create(foodMenuDTO);
    }

    @Override
    public FoodMenuDTO getFoodMenuById(Long id) {
        return foodMenuDAO.findById(id);
    }

    @Override
    public List<FoodMenuDTO> getFoodMenusByUserId(Long userId) {
        return foodMenuDAO.findByUserId(userId);
    }

    @Override
    public FoodMenuDTO updateFoodMenu(FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.update(foodMenuDTO);
    }

    @Override
    public void deleteFoodMenu(Long id) {
        foodMenuDAO.delete(id);
    }
}

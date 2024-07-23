package Service;

import DAO.FoodMenuDAO;
import DTO.FoodMenuDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuDAO foodMenuDAO;
    private final RestTemplate restTemplate;

    public FoodMenuServiceImpl(FoodMenuDAO foodMenuDAO, RestTemplate restTemplate) {
        this.foodMenuDAO = foodMenuDAO;
        this.restTemplate = restTemplate;
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

    //자동 저장
    @Scheduled(cron = "0 0 7")// 이게 7시 발송이라던데
    public void fetchAndSendFoodMenuNotification(){
        String url = "http://diet/user/diets"; //예시임
        FoodMenuDTO foodMenuDTO = restTemplate.getForObject(url, FoodMenuDTO.class);
    }
    //알림 보내기
    public void sendFoodMenuNotifications() {
        // 식단 알림 보내기 구현해야됨
    }
}

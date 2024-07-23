package Controller;

import DTO.FoodMenuDTO;
import Service.FoodMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification/food-menu")
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @Autowired
    public FoodMenuController(FoodMenuService foodMenuService) {
        this.foodMenuService = foodMenuService;
    }

    @PostMapping
    public ResponseEntity<FoodMenuDTO> createFoodMenu(@RequestBody FoodMenuDTO foodMenuDTO) {
        FoodMenuDTO createdFoodMenu = foodMenuService.createFoodMenu(foodMenuDTO);
        return ResponseEntity.ok(createdFoodMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodMenuDTO> getFoodMenuById(@PathVariable Long id) {
        FoodMenuDTO foodMenuDTO = foodMenuService.getFoodMenuById(id);
        return ResponseEntity.ok(foodMenuDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodMenuDTO>> getFoodMenusByUserId(@PathVariable Long userId) {
        List<FoodMenuDTO> foodMenuDTOs = foodMenuService.getFoodMenusByUserId(userId);
        return ResponseEntity.ok(foodMenuDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodMenuDTO> updateFoodMenu(@PathVariable Long id, @RequestBody FoodMenuDTO foodMenuDTO) {
        foodMenuDTO.setId(id);
        FoodMenuDTO updatedFoodMenu = foodMenuService.updateFoodMenu(foodMenuDTO);
        return ResponseEntity.ok(updatedFoodMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodMenu(@PathVariable Long id) {
        foodMenuService.deleteFoodMenu(id);
        return ResponseEntity.noContent().build();
    }
}

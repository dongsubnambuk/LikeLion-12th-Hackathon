package DAO;

import DTO.FoodMenuDTO;

import java.util.List;

public interface FoodMenuDAO {
    public FoodMenuDTO create(FoodMenuDTO foodMenuDTO);
    public FoodMenuDTO findById(Long id);
    public List<FoodMenuDTO> findByUserId(Long userId);
    public FoodMenuDTO update(FoodMenuDTO foodMenuDTO);
    public void delete(Long id);
}

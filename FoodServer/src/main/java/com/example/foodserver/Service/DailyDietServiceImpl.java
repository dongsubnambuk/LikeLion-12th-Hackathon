package com.example.foodserver.Service;

import com.example.foodserver.DAO.DailyDietDAO;
import com.example.foodserver.DTO.DailyDietDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyDietServiceImpl implements DailyDietService {

    private final DailyDietDAO dailyDietDAO;

    @Autowired
    public DailyDietServiceImpl(DailyDietDAO dailyDietDAO) {
        this.dailyDietDAO = dailyDietDAO;
    }

    @Override
    public DailyDietDTO createDailyDiet(DailyDietDTO dailyDietDTO) {
        return dailyDietDAO.create(dailyDietDTO);
    }

    @Override
    public Optional<DailyDietDTO> getDailyDietById(Long id) {
        return dailyDietDAO.getById(id);
    }

    @Override
    public List<DailyDietDTO> getAllDailyDiets() {
        return dailyDietDAO.getAll();
    }

    @Override
    public void deleteDailyDiet(Long id) {
        dailyDietDAO.delete(id);
    }
}

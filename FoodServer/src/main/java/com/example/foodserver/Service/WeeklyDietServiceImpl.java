package com.example.foodserver.Service;

import com.example.foodserver.DAO.WeeklyDietDAO;
import com.example.foodserver.DTO.WeeklyDietDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyDietServiceImpl implements WeeklyDietService {

    private final WeeklyDietDAO weeklyDietDAO;

    @Autowired
    public WeeklyDietServiceImpl(WeeklyDietDAO weeklyDietDAO) {
        this.weeklyDietDAO = weeklyDietDAO;
    }

    @Override
    public WeeklyDietDTO createWeeklyDiet(WeeklyDietDTO weeklyDietDTO) {
        return weeklyDietDAO.create(weeklyDietDTO);
    }

    @Override
    public Optional<WeeklyDietDTO> getWeeklyDietById(Long id) {
        return weeklyDietDAO.getById(id);
    }

    @Override
    public List<WeeklyDietDTO> getAllWeeklyDiets() {
        return weeklyDietDAO.getAll();
    }

    @Override
    public void deleteWeeklyDiet(Long id) {
        weeklyDietDAO.delete(id);
    }
}

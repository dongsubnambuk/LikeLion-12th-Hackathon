package Repository;

import Entity.FoodMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenuEntity, Long> {
    List<FoodMenuEntity> findByUserId(Long userId);
}

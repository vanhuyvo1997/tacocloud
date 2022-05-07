package tacos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.entity.TacoOrder;
import tacos.entity.User;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
	List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}

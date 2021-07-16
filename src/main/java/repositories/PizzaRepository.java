package repositories;

import com.example.pizzadelivery.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PizzaRepository extends JpaRepository< Pizza, Integer >, JpaSpecificationExecutor< Pizza > {

}
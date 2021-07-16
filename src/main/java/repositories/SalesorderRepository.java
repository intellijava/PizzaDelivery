package repositories;

import com.example.pizzadelivery.entities.Salesorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SalesorderRepository extends JpaRepository< Salesorder, Integer >, JpaSpecificationExecutor< Salesorder > {

}
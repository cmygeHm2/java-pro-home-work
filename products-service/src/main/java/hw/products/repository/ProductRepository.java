package hw.products.repository;

import hw.products.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Product> findByUserId(Long id);

    @Query(value = "SELECT * FROM products WHERE id = :id AND balance = :balance FOR UPDATE", nativeQuery = true)
    Optional<Product> selectForUpdate(Long id, BigDecimal balance);
}









package com.example.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	
	// http http://localhost:8081/products/search/findByName\?name\=TV
	// http http://localhost:8081/products/search/findByName name==TV
	List<Product> findByName(String name);
	
	// http http://localhost:8081/products/search/findByStockAndName name==TV stock==10
	List<Product> findByStockAndName(int stock, String name);
	
	// http http://localhost:8081/products/search/getName\?name\=TV
	@Query(
            value = "SELECT * FROM Product u WHERE u.name = ?1 ",
            nativeQuery = true)
    List<Product> getName(String name);
}

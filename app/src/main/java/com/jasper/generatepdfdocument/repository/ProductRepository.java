package com.jasper.generatepdfdocument.repository;

import com.jasper.generatepdfdocument.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

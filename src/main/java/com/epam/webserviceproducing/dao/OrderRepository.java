package com.epam.webserviceproducing.dao;

import com.epam.webserviceproducing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.fooddelivery.fooddeliveryapp.repositories;

import com.fooddelivery.fooddeliveryapp.entities.DeliveryPersonLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPersonLocationRepository extends JpaRepository<DeliveryPersonLocation, Long> {
    Optional<DeliveryPersonLocation> findByOrderId(Long orderId);
}

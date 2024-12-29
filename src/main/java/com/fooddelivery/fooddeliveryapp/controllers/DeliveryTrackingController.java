package com.fooddelivery.fooddeliveryapp.controllers;

import com.fooddelivery.fooddeliveryapp.entities.DeliveryPersonLocation;
import com.fooddelivery.fooddeliveryapp.entities.DeliveryPersonLocationUpdate;
import com.fooddelivery.fooddeliveryapp.repositories.DeliveryPersonLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
public class DeliveryTrackingController {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private DeliveryPersonLocationRepository locationRepository ;

    // Mettre à jour la position du livreur
    @MessageMapping("/updateLocation")
    public void updateLocation(@Payload DeliveryPersonLocationUpdate update) {
        // Logique pour mettre à jour la position du livreur dans la base de données
        // et notifier les clients abonnés
        template.convertAndSend("/topic/delivery/" + update.getOrderId(), update);
    }
    @GetMapping("/{orderId}")
    public DeliveryPersonLocation getLocation(@PathVariable Long orderId) {
        // Retrieve the delivery person's location by order ID
        return locationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}

package com.backend.picpaysimplificado.desafiopicpay.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;
import com.backend.picpaysimplificado.desafiopicpay.dto.notification.NotificationDTO;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(UsersEntity user, String message) throws Exception {
        String email = user.getEmail();
    
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
    
        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
    
        if (notificationResponse.getStatusCode() != HttpStatus.OK) {
            System.out.println("Erro ao enviar a notificação: " + notificationResponse.getBody());
            throw new Exception("Serviço de notificação está fora do ar");
        }
        System.out.println("Notificação enviada para o usuário: " + email);
    }
}

package com.backend.picpaysimplificado.desafiopicpay.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.picpaysimplificado.desafiopicpay.domain.dto.notification.NotificationDTO;
import com.backend.picpaysimplificado.desafiopicpay.domain.users.entity.UsersEntity;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(UsersEntity user, String message) throws Exception{
        String email = user.getEmail();

        NotificationDTO notificationResquest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationResquest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            System.out.println("Erro ao enviar a notificação");
            throw new Exception("Serviço ed notificação esta fora do ar");
        }
    }
}

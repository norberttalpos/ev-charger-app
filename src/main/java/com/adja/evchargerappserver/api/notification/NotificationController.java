package com.adja.evchargerappserver.api.notification;

import com.adja.evchargerappserver.api.abstracts.AbstractController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@Api(value = "/api/notification", tags = "Notification")
public class NotificationController extends AbstractController<Notification, NotificationFilter, NotificationService> {
}

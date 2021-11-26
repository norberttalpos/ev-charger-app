package com.adja.evchargerappserver.api.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFilter {
    private Long personId;
    private Long chargerId;
}

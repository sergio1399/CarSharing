package sergio.com.carsharing.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckService {

    public boolean isInTime(LocalDateTime end) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(end)) {
            return false;
        }
        return true;
    }
}

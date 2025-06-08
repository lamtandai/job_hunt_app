package application.project.util.AuditUtil;

import java.util.Optional;

import org.springframework.stereotype.Service;

import application.project.util.SecurityUtil.SecurityUtil;

@Service
public final class AuditUtil {
    
    public Long currentUserId() {
        try {
            Optional<String> valueOpt = SecurityUtil.getCurrentUserLogin();
            if (valueOpt.isPresent()){
                return Long.valueOf(valueOpt.get());
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

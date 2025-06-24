package application.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InsertDummyRowListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private boolean hasRun = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Ngăn chạy lại nếu context refresh nhiều lần (ví dụ trong DevTools)
        if (hasRun) return;

        try {
            // Tạm thời tắt strict mode
            jdbcTemplate.execute("SET SESSION sql_mode = ''");

            // Chèn hàng dummy
            jdbcTemplate.update("INSERT INTO user_accounts(us_id) VALUES (0)");
            jdbcTemplate.update("INSERT INTO industries(idt_id) VALUES (0)");
            jdbcTemplate.update("INSERT INTO companies(cpn_id) VALUES (0)");

            // Khôi phục strict mode
            jdbcTemplate.execute("SET SESSION sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION'");

            hasRun = true; // Đánh dấu đã chạy
        } catch (Exception e) {
            System.err.println("⚠️ Failed to insert dummy rows: " + e.getMessage());
        }
    }
}

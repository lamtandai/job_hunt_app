package application.project.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private int sk_id;
    private String sk_name;

    
    private boolean sk_deleted;
    private long sk_createdBy;
    private long sk_updatedBy;

   private Instant sk_createdAt;
   private Instant sk_updatedAt;
}

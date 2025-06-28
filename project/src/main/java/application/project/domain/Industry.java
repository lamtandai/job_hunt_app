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
public class Industry {
    private int idt_id;
    private String idt_name;

    private boolean idt_deleted;
    private long idt_createdBy;
    private long idt_updatedBy;

   private Instant idt_createdAt;
   private Instant idt_updatedAt;
}

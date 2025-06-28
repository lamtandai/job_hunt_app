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
public class Company {

    private int cpn_id;
    private String cpn_name;
    private String cpn_description;

    private short cpn_idt_id;
    private int cpn_number_of_follower;
    private int cpn_size;

    private String cpn_logo;
    private String cpn_location;
    private String cpn_website_url;
    private boolean cpn_verified;

    private boolean cpn_deleted;

    private Long cpn_created_by;
    private Long cpn_updated_by;

    private Instant cpn_createdAt;
    private Instant cpn_updatedAt;

}

package Oracle.Partner.Tracker.utils.userenum;

import Oracle.Partner.Tracker.utils.companyEnum.IngestionOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBuilder {
    private String name;
    private String email;
    private String password;
    private RoleEnum role;
    private IngestionOperation ingestionOperation;
    private Status userStatus;
    private MembershipEnum memberShipType;

    // getters and setters for all fields
    // ...
}


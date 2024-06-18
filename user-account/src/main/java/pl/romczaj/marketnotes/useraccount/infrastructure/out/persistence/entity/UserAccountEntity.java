package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.common.persistance.UserAccountExternalIdDatabaseConverter;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Convert(converter = UserAccountExternalIdDatabaseConverter.class)
    @Column(unique = true)
    private UserAccountExternalId externalId;

    private String username;
    private String email;

    public static UserAccountEntity fromDomain(UserAccount userAccount) {
        return new UserAccountEntity(
                userAccount.id(),
                userAccount.externalId(),
                userAccount.username(),
                userAccount.email()
        );
    }

    public UserAccount toDomain() {
        return new UserAccount(
                id,
                externalId,
                username,
                email
        );
    }

}

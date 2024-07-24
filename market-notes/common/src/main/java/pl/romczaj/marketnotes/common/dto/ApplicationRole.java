package pl.romczaj.marketnotes.common.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum ApplicationRole {
    USER("default-roles-market-notes"),
    ADMIN("admin-market-notes");

    private final String keycloakValue;


    public static List<ApplicationRole> fromKeycloakValues(Collection<String> roles) {
        return Stream.of(values())
                .filter(r -> roles.contains(r.keycloakValue))
                .toList();
    }

    public SimpleGrantedAuthority toSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this);
    }

}

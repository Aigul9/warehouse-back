package com.warehouse.roles;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.warehouse.roles.Permission.APPROVE_INVENTORY_PERMISSION;
import static com.warehouse.roles.Permission.INVENTORY_PERMISSION;
import static com.warehouse.roles.Permission.ORDER_PERMISSION;
import static com.warehouse.roles.Permission.READ_ONLY_PERMISSION;
import static com.warehouse.roles.Permission.SUPPLY_PERMISSION;
import static com.warehouse.roles.Role.ADMIN;
import static com.warehouse.roles.Role.DEFAULT_USER;
import static com.warehouse.roles.Role.SELLER;
import static com.warehouse.roles.Role.SUPERVISOR;
import static com.warehouse.roles.Role.WAREHOUSEMAN;

@NoArgsConstructor
public class RolesPermissions {

    private static final Set<String> ALL_PERMISSIONS = Arrays
            .stream(Permission.values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    private static final Set<String> DEFAULT_USER_PERMISSION = Arrays
            .stream(Permission.values())
            .filter(permission -> permission.equals(READ_ONLY_PERMISSION))
            .map(Permission::name)
            .collect(Collectors.toSet());

    private static final Set<String> SELLER_PERMISSION = Arrays
            .stream(Permission.values())
            .filter(permission -> permission.equals(READ_ONLY_PERMISSION) ||
                    permission.equals(ORDER_PERMISSION))
            .map(Permission::name)
            .collect(Collectors.toSet());

    private static final Set<String> WAREHOUSEMAN_PERMISSION = Arrays
            .stream(Permission.values())
            .filter(permission -> permission.equals(READ_ONLY_PERMISSION) ||
                    permission.equals(SUPPLY_PERMISSION) ||
                    permission.equals(INVENTORY_PERMISSION))
            .map(Permission::name)
            .collect(Collectors.toSet());

    private static final Set<String> SUPERVISOR_PERMISSION = Arrays
            .stream(Permission.values())
            .filter(permission -> permission.equals(READ_ONLY_PERMISSION) ||
                    permission.equals(ORDER_PERMISSION) ||
                    permission.equals(SUPPLY_PERMISSION) ||
                    permission.equals(INVENTORY_PERMISSION) ||
                    permission.equals(APPROVE_INVENTORY_PERMISSION))
            .map(Permission::name)
            .collect(Collectors.toSet());

    private static final Map<Role, Set<String>> PERMISSIONS_BY_ROLE;

    static {
        PERMISSIONS_BY_ROLE = new EnumMap<>(Role.class);
        PERMISSIONS_BY_ROLE.put(ADMIN, ALL_PERMISSIONS);
        PERMISSIONS_BY_ROLE.put(DEFAULT_USER, DEFAULT_USER_PERMISSION);
        PERMISSIONS_BY_ROLE.put(SELLER, SELLER_PERMISSION);
        PERMISSIONS_BY_ROLE.put(WAREHOUSEMAN, WAREHOUSEMAN_PERMISSION);
        PERMISSIONS_BY_ROLE.put(SUPERVISOR, SUPERVISOR_PERMISSION);
    }

    public static Set<String> getPermissionsByRole(Role role) {
        Set<String> rolePermissions = PERMISSIONS_BY_ROLE.get(role);
        return rolePermissions == null ? new HashSet<>() : rolePermissions;
    }

    public static Set<String> getFrontPermissionsByRole(Set<String> permissions) {
        return permissions
                .stream()
                .filter(ALL_PERMISSIONS::contains)
                .collect(Collectors.toSet());
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getGrantedAuthorities(getPermissionsByRole(role));
    }

    public static List<GrantedAuthority> getGrantedAuthorities(Set<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }
}
package com.project.bookstorage.models.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    
    ADMIN(Arrays.asList(
        RolePermission.READ_ALL,
        RolePermission.CREATE_ONE_BOOK,
        RolePermission.UPDATE_ONE_BOOK,
        RolePermission.DELETE_ONE_BOOK,
        RolePermission.READ_MY_PROFILE
    )),
    USER(Arrays.asList(
        RolePermission.READ_MY_PROFILE
    ));

    private List<RolePermission> permissions;

    private Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    

}

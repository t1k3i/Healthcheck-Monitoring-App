package com.springboot.healthcheck.exceptions.notfound;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException() {
        super("Role not found");
    }

}

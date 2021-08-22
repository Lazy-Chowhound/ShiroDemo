package com.example.shirodemo.Entity;

import lombok.Data;

/**
 * @author Nakano Miku
 */

@Data
public class Permission {

    private int id;

    private String permissionName;

    private int moduleId;

    private int actionId;
}

package com.vinteo.inventory.model;

import lombok.Getter;

@Getter
public class ContactAddRequest

{
    protected String name;
    protected String phone;
    protected String email;
    protected int location;
}

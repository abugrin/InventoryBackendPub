package com.vinteo.inventory.model;

import lombok.Getter;

@Getter
public class ItemAddRequest
{
    protected String name;
    protected String description;
    protected String pn;
    protected String serial;
    protected int typeId;
    protected int locationId;
}

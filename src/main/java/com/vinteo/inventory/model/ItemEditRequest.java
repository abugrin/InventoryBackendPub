package com.vinteo.inventory.model;

import lombok.Getter;

@SuppressWarnings("unused")
@Getter
public class ItemEditRequest extends ItemAddRequest
{
    private int id;
    private int statusId;
}

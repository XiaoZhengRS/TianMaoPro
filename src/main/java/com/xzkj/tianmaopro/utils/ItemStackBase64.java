package com.xzkj.tianmaopro.utils;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemStackBase64 {
    public static String getItemStacktoBase64(ItemStack itemstak){
        ItemStack[] itemStacks = new ItemStack[]{itemstak};
        return nmslibrary.nmslibrary.util.serializer.ItemSerializerUtils.toBase64(itemStacks);
    }
    public static ItemStack getBase64toItemStack(String str){
        for (ItemStack is: nmslibrary.nmslibrary.util.serializer.ItemSerializerUtils.fromBase64(str)){
            return is;
        }
        return null;
    }

    //ORG -> NMS

    public static Object getNMSItemTM(ItemStack itemStack){
        return nmslibrary.nmslibrary.util.nms.NMSUtils.getNMSItem(itemStack);
    }



    //NMS -> ORG

    public static ItemStack getBukkitItemTM(Object nmsItem){
        return (ItemStack) nmslibrary.nmslibrary.util.nms.NMSUtils.getBukkitItem(nmsItem);
    }



}

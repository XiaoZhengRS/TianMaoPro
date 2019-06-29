package com.xzkj.tianmaopro.HashMap;

import com.xzkj.tianmaopro.api.Api;
import com.xzkj.tianmaopro.api.ShopApi;
import com.xzkj.tianmaopro.api.View;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TMHashMap {
    public static HashMap<String, Api> apiData = new HashMap<String, Api>();
    public static HashMap<String, View> viewData = new HashMap<String, View>();
    public static HashMap<String, ShopApi> ShopData = new HashMap<String, ShopApi>();
    public Api getData(Player player) {
        if (!apiData.containsKey(player.getName())) {
            apiData.put(player.getName(), new Api());
            return apiData.get(player.getName());
        } else {
            return apiData.get(player.getName());
        }
    }
    public View getDataView(Player player) {
        if (!viewData.containsKey(player.getName())) {
            viewData.put(player.getName(), new View());
            return viewData.get(player.getName());
        } else {
            return viewData.get(player.getName());
        }
    }

    public ShopApi getShopData(Player player) {
        if (!ShopData.containsKey(player.getName())) {
            ShopData.put(player.getName(), new ShopApi());
            return ShopData.get(player.getName());
        } else {
            return ShopData.get(player.getName());
        }
    }
}

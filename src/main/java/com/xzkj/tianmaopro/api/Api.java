package com.xzkj.tianmaopro.api;

import com.xzkj.tianmaopro.data.CoreData;
import com.xzkj.tianmaopro.utils.Mes;
import com.xzkj.tianmaopro.utils.NBT;
import com.xzkj.tianmaopro.utils.NBTUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class Api {
    //创建店铺

    public void setPlayerShop(Player p, String ShopName) {
        File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        if (!PlayerShopFile.exists()) {
            if (CoreData.TMEcecon.bankHas(p.getName(), Double.valueOf(CoreData.TM_Create)).transactionSuccess()) {
                try {
                    PlayerShopFile.createNewFile();//创建这个文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CoreData.TMEcecon.bankWithdraw(p.getName(), CoreData.TM_Create);
                Mes.logPlayer(">>>创建天猫店铺完成", p);
            } else {
                Mes.logPlayer(">>>金钱不足!无法创建店铺!", p);
                return;
            }
        } else {
            Mes.logPlayer(">>您已经创建过天猫店铺!", p);
            return;
        }
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        playerShopYml.set("商店名称", ShopName);
        playerShopYml.set("商店玩家名称", p.getName());
        playerShopYml.set("商店积分", 0);
        playerShopYml.set("商店历史销量", 0);
        playerShopYml.set("商店商品数量", 0);
        try {
            playerShopYml.save(PlayerShopFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//上架手中商品

    public void setShopSale(Player p, String price) {
        File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        if (!PlayerShopFile.exists()) {
            Mes.logPlayer(">>>占时没有创建任何天猫商店!", p);
            return;
        }
        if (p.getItemInHand() == null) {
            Mes.logPlayer(">>>当前手中没有任何物品!", p);
            return;
        }
        //创建NBT
        NBT dataNBT = new NBT(p.getItemInHand());

        //获取可存储的NBTC
        String dataStringCompound = NBTUtils.NBTtoBase54(dataNBT.compound);
        org.bukkit.inventory.ItemStack ShopSale = p.getItemInHand();
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        int ShopShop = playerShopYml.getInt("商店商品数量");
        playerShopYml.set("商店商品数量", ShopShop + 1);
        String saleName = ShopSale.getData().toString();
        Integer ShopShopItem = playerShopYml.getInt(ShopSale.getData().toString() + ".数量");
        playerShopYml.set("商品." + saleName + ".数量", ShopSale.getAmount() + ShopShopItem);
        playerShopYml.set("商品." + saleName + ".价格", price);
        playerShopYml.set("商品." + saleName + ".唯一标识", saleName);
        playerShopYml.set("商品." + saleName + ".ItemStack", ShopSale);
        playerShopYml.set("商品." + saleName + ".Base64ItemStack", dataStringCompound);
        try {
            playerShopYml.save(PlayerShopFile);
            p.getItemInHand().setAmount(0);
            Mes.logPlayer(">>>上架完成!", p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

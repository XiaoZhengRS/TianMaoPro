package com.xzkj.tianmaopro.api;

import com.xzkj.tianmaopro.data.CoreData;
import com.xzkj.tianmaopro.data.TianMaoShop;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShopApi {

    //遍历全部店铺文件

    public List<File> getTianMaoShopList(){
        File[] fs = CoreData.PlayerShop.listFiles();
        List<File> refl = new ArrayList<>();
        for (File datafl: fs){
            if(!datafl.isDirectory()){
                refl.add(datafl);
            }
        }

        TianMaoShop tempData;
        int sizeFile = refl.size();
        List<TianMaoShop> data = new ArrayList<>();
        for (int i = 0; i < sizeFile; i++) {
            YamlConfiguration datayml = YamlConfiguration.loadConfiguration(refl.get(i));
            data.add(new TianMaoShop(datayml.getInt("商店积分"), datayml.getString("商店玩家名称")));
        }
        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i).Integral > data.get(j).Integral){
                    tempData = data.get(i);
                    data.set(i,data.get(j));
                    data.set(j,tempData);
                }
            }
        }

        List<File> dataFe = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            File dataFiledata = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + data.get(i).ShopPlayerName + ".yml");
            dataFe.add(dataFiledata);
        }
        return dataFe;
    }

    //获取税收

    public int getTianMaoShopTax(Player p){
        return CoreData.Config.getInt("等级." + getTianMaoShopSegment(p) + ".税收");
    }

    //获取店铺等级

    public String getTianMaoShopSegment(Player p){
        File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        int integral = playerShopYml.getInt("商店积分");
        String Segment = CoreData.TM_Shop_Name.get(0);
        for (int i = 0; i < CoreData.TM_Shop_Name.size(); i++) {
            if (integral >= CoreData.TM_Shop_Integral.get(i)){
                Segment = CoreData.TM_Shop_Name.get(i);
            }
        }
        return Segment;
    }
}

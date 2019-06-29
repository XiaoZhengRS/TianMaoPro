package com.xzkj.tianmaopro.data;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoreData {
    public static Boolean DeBug;
    public static String ClassPath;
    public static File PluginPath;
    public static File PlayerShop;
    public static FileConfiguration Config;
    public static File Message;
    public static File Mysql;
    public static String PluginGameName;
    public static Economy TMEcecon = null;
    //店铺名称集

    public static List<String> TM_Help = new ArrayList<String>();
    //创建店铺所需手续费

    public static int TM_Create;

    //更改店铺名称手续费

    public static int TM_Change_Name;
    //店铺名称不可使用

    public static List<String> TM_Shop_Name_NO = new ArrayList<String>();
    //店铺名称集

    public static List<String> TM_Shop_Name = new ArrayList<String>();
    //店铺税收

    public static List<Integer> TM_Shop_Tax = new ArrayList<Integer>();
    //最高上架商品种类

    public static List<Integer> TM_Shop_Max_Commodity = new ArrayList<Integer>();
    //激活积分

    public static List<Integer> TM_Shop_Integral = new ArrayList<Integer>();
    //是否开启点券交易模式

    public static List<Boolean> TM_Shop_Ticket_Mode = new ArrayList<Boolean>();
}

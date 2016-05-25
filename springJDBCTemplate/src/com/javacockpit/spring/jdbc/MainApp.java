package com.javacockpit.spring.jdbc;

import com.javacockpit.spring.jdbc.bean.AssetPojo;
import com.javacockpit.spring.jdbc.dao.IAssetDAO;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAHMUT BAS on 25.5.2016.
 */
public class MainApp
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-conf-file.xml");

        IAssetDAO assetDAO = context.getBean("assetDao", IAssetDAO.class);

        System.out.println("-------------------- GET ALL ----------------");
        List<AssetPojo> assetItems = assetDAO.getAssets();

        for (AssetPojo assetItem : assetItems)
        {
            System.out.println(assetItem);
        }

        System.out.println("-------------------- GET ONLY ONE BY ID ----------------");
        System.out.println(assetDAO.getAsset("8723481K"));

        System.out.println("-------------------- UPDATE ONLY ONE BY ID ----------------");
        System.out.println(" update is " + assetDAO.update(new AssetPojo("9DU3R9", "ItemMahmut", "DescMahmut", "LocMahmut")) + " for 9DU3R9 ");

        System.out.println("-------------------- GET ONLY ONE BY ID ----------------");
        System.out.println(assetDAO.getAsset("9DU3R9"));
/*
        System.out.println("-------------------- INSERT ONLY ONE BY ID ----------------");
        System.out.println(" insert is " + assetDAO.insert(new AssetPojo("SERINOO3", "ITEMNOOO", "DESCPARTT", "LocNOOO")) + " for SERINOO3 ");

        System.out.println("-------------------- GET ONLY ONE BY ID ----------------");
        System.out.println(assetDAO.getAsset("SERINOO3"));*/

        System.out.println("-------------------- DELETE ONLY ONE BY ID ----------------");
        System.out.println(" delete is " + assetDAO.delete("SERINOO") + " for SERINOO ");

        System.out.println("-------------------- GET ALL ----------------");
        assetItems = assetDAO.getAssets();

        for (AssetPojo assetItem : assetItems)
        {
            System.out.println(assetItem);
        }

        System.out.println("-------------------- INSERT LIST  ----------------");
        AssetPojo assetFirst = new AssetPojo("OSOKD", "ITEMMM", "DESCCCCC", "LOCCCCC");
        AssetPojo assetSecond = new AssetPojo("odjd", "dddITEMMM", "dddDESCCCCC", "dddLOCCCCC");
        AssetPojo assetThird = new AssetPojo("sdsd", "aaaITEMMM", "aaaDESCCCCC", "aaaLOCCCCC");
        ArrayList<AssetPojo> listAsset = new ArrayList<>();
        listAsset.add(assetFirst);
        listAsset.add(assetSecond);
        listAsset.add(assetThird);

        int rows[] = assetDAO.insert(listAsset);
        for (int a = 1; a <= rows.length; a++)
        {
            System.out.println(a + " row inserted");
        }
        System.out.println("-------------------- GET ALL ----------------");
        assetItems = assetDAO.getAssets();
        for (AssetPojo assetItem : assetItems)
        {
            System.out.println(assetItem);
        }

        context.close();


    }
}

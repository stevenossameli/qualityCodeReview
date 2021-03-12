package com.ossasteven.desafiospring.util;

import com.ossasteven.desafiospring.model.ArticleDTO;

import java.awt.datatransfer.FlavorListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetArticles {


    public static List<ArticleDTO> getFourMocks() {

        List<ArticleDTO> list = new ArrayList<>();

        ArticleDTO martillo = new ArticleDTO();
        martillo.setId(1L);
        martillo.setName("Martillo de hierro");
        martillo.setTradeMark("HomeCenter");
        martillo.setCategory("herramientas");
        martillo.setQuantity(4);
        martillo.setPrice(800D);
        martillo.setPrestige(5);
        martillo.setSendFree(true);

        ArticleDTO llaveInglesa = new ArticleDTO();
        llaveInglesa.setId(2L);
        llaveInglesa.setName("Llave Inglesa roja");
        llaveInglesa.setTradeMark("HomeCenter");
        llaveInglesa.setCategory("herramientas");
        llaveInglesa.setQuantity(4);
        llaveInglesa.setPrice(800D);
        llaveInglesa.setPrestige(5);
        llaveInglesa.setSendFree(false);

        ArticleDTO nintendoSwitch = new ArticleDTO();
        nintendoSwitch.setId(3L);
        nintendoSwitch.setName("Nintendo Switch Pro");
        nintendoSwitch.setTradeMark("Nintendo");
        nintendoSwitch.setCategory("Gaming");
        nintendoSwitch.setQuantity(4);
        nintendoSwitch.setPrice(800D);
        nintendoSwitch.setPrestige(5);
        nintendoSwitch.setSendFree(true);

        ArticleDTO macPro = new ArticleDTO();
        macPro.setId(4L);
        macPro.setName("Mac Pro 13");
        macPro.setTradeMark("Apple");
        macPro.setCategory("Computing");
        macPro.setQuantity(4);
        macPro.setPrice(2000D);
        macPro.setPrestige(5);
        macPro.setSendFree(false);

        list.add(martillo);
        list.add(llaveInglesa);
        list.add(nintendoSwitch);
        list.add(macPro);

        return list;
    }

    public static List<ArticleDTO> getEightMocks() {
        List<ArticleDTO> list = getFourMocks();

        ArticleDTO taladro = new ArticleDTO();
        taladro.setId(5L);
        taladro.setName("Taladro 3.0");
        taladro.setTradeMark("Makita");
        taladro.setCategory("herramientas");
        taladro.setQuantity(4);
        taladro.setPrice(2000D);
        taladro.setPrestige(5);
        taladro.setSendFree(true);

        ArticleDTO playStation = new ArticleDTO();
        playStation.setId(6L);
        playStation.setName("PlayStation 5");
        playStation.setTradeMark("Sony");
        playStation.setCategory("gaming");
        playStation.setQuantity(3);
        playStation.setPrice(3500D);
        playStation.setPrestige(5);
        playStation.setSendFree(false);

        ArticleDTO xbox = new ArticleDTO();
        xbox.setId(7L);
        xbox.setName("Xbox Series X");
        xbox.setTradeMark("Microsoft");
        xbox.setCategory("gaming");
        xbox.setQuantity(2);
        xbox.setPrice(3000D);
        xbox.setPrestige(5);
        xbox.setSendFree(false);

        ArticleDTO monitorSamsung = new ArticleDTO();
        monitorSamsung.setId(8L);
        monitorSamsung.setName("Monitor led");
        monitorSamsung.setTradeMark("Samsung");
        monitorSamsung.setCategory("Electrodomestico");
        monitorSamsung.setQuantity(4);
        monitorSamsung.setPrice(2000D);
        monitorSamsung.setPrestige(5);
        monitorSamsung.setSendFree(false);

        return list;

    }

    public static List<ArticleDTO> getByCategory(String cat, List<ArticleDTO> list) {
        return list.stream().filter(a -> a.getCategory().equalsIgnoreCase(cat)).collect(Collectors.toList());
    }

    public static List<ArticleDTO> getBySendFree(boolean cat, List<ArticleDTO> list) {

        if (cat)
            return list.stream().filter(ArticleDTO::getSendFree).collect(Collectors.toList());

        return list;

    }

    public static void sortList(List<ArticleDTO> toSort, Comparator<ArticleDTO> c) {
        toSort.sort(c);
    }


}
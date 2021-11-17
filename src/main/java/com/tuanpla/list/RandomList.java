/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author TUANPLA
 */
public class RandomList {

    public static void main(String[] args) {
        ArrayList list1 = new ArrayList();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        list1.add("D");
        list1.add("E");
        list1.add("F");
        System.out.println(ranDomFromCache(list1, 3));
//        Collections.shuffle(list1);
//        System.out.println("Ran dom 1:" + list1);
//        System.out.println("---------------");
//        Collections.shuffle(list1);
//        System.out.println(list1);
//        List a1 = new ArrayList();
//
//        Collections.copy(list1, a1);
//        System.out.println(":=>" + a1);
//        
//        ArrayList listCopy = (ArrayList) list1.clone();
//        System.out.println("Copy:"+listCopy);
//        System.out.println("clear list 1: ");
//        list1.clear();
//        System.out.println("copy After Clear Source:"+listCopy);
                
    }
    
    public static ArrayList ranDomFromCache(ArrayList source, int showItem) {
        ArrayList result = new ArrayList<>();
        try {
            if (source != null && source.size() > showItem) {
                Collections.shuffle(source);
                for (int i = 0; i < showItem; i++) {
                    result.add(source.get(i));
                }
            } else {
                return source;
            }
//            ArrayList<Advertise> listCopy = (ArrayList<Advertise>) source.clone();
////            Tool.Debug("listCopy:" + listCopy.size());
//            // Neu tong so phan tu cacha > so phan tu hien thi
//            if (listCopy.size() > showItem) {
//                for (int i = 0; i < showItem; i++) {
//                    int ranInt = getRandomInt(listCopy.size());
//                    Advertise oneItem = listCopy.get(ranInt);
//                    result.add(oneItem);
//                    listCopy.remove(oneItem);
//                }
//            } else {
//                return source;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

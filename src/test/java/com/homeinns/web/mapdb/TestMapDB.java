package com.homeinns.web.mapdb;
import org.mapdb.*;
import org.junit.Test;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by ytzhou on 2014/7/22.
 * DEMO: https://github.com/jankotek/MapDB/tree/master/src/test/java/examples
 */
public class TestMapDB {

    //文件路径
    private static String filePath="D:\\idea\\Java\\homeinns-web\\cache\\HomeinnsCache";

    @Test
    public void init(){
        //configure and open database using builder pattern.
        //all options are available with code auto-completion.
        DB db = DBMaker.newFileDB(new File("HotelCache"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();
        //open existing an collection (or create new)
        ConcurrentNavigableMap map = db.getTreeMap("HotelMap");

        map.put(1, "one");
        map.put(2, "two");
        //map.keySet() is now [1,2]
        db.commit();   //persist changes into disk
        map.put(3, "three");
        //map.keySet() is now [1,2,3]
        db.rollback(); //revert recent changes
        //map.keySet() is now [1,2]
        db.close();
    }

    @Test
    public void create(){
        DB db = DBMaker.newFileDB(new File(filePath)).make();
        Map map = db.getHashMap("HotelData");
        map.put("hotelName", "上海徐汇和颐酒店");
        db.commit();
        db.close();
    }

    @Test
    public void get(){
        DB db = DBMaker.newFileDB(new File(filePath)).make();
        Map map = db.getHashMap("HotelData");
        System.out.print(map.get("hotelName"));
    }
}

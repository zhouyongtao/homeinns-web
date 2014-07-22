package com.homeinns.web.mapdb;
import org.mapdb.*;
import org.junit.Test;
import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by ytzhou on 2014/7/22.
 */
public class TestMapdb {

   @Test
   public void test(){
       //configure and open database using builder pattern.
       //all options are available with code auto-completion.
       DB db = DBMaker.newFileDB(new File("testdb"))
               .closeOnJvmShutdown()
               .encryptionEnable("password")
               .make();
       //open existing an collection (or create new)
       ConcurrentNavigableMap map = db.getTreeMap("collectionName");
       map.put(1, "one");
       map.put(2, "two");
       //map.keySet() is now [1,2]
       //persist changes into disk
       db.commit();
       map.put(3, "three");
       //map.keySet() is now [1,2,3]
       db.rollback(); //revert recent changes
       //map.keySet() is now [1,2]
       db.close();
   }
}

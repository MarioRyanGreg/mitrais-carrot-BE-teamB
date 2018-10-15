package com.mitrais.carrot.dummy;

import java.util.Arrays;

import com.mitrais.carrot.models.Barn;
import com.mitrais.carrot.models.BarnSetting;

/**
 * dummy class of Barn data dummy
 *
 * @author Febri_MW251
 *
 */
public class BarnDummy {

    /**
     * dummy barn data
     *
     * @return Barn object
     */
    public static Barn barnObject() {
        Barn b = new Barn();
        b.setId(1);
        b.setName("barn nana");
        b.setOwner("Pak Max");        
        b.setStartPeriode(DateDummy.myLocalDateTime("2018-12-01 10:30"));
        b.setEndPeriode(DateDummy.myLocalDateTime("2018-12-30 10:30"));        
        b.setStatus(1);
        b.setIsReleased(Boolean.TRUE);;
        b.setCarrotPerEmployee(2);
        b.setTotalCarrot(10);
        b.setDeleted(Boolean.FALSE);
        b.setCreatedBy(1);
        return b;
    }

    /**
     * dummy barn request body
     * 
     * @return Barn object
     */
    public static Barn barnRequest() {
    	Barn b = new Barn();
        b.setName("BCNDSDADSND");
        b.setOwner("John");
        return b;
    }

    /**
     * dummy for list of barns
     * 
     * @return Array list of barn object
     */
    public static Iterable<Barn> listOfBarns() {
        Barn bo = BarnDummy.barnObject();
        return Arrays.asList(new Barn[]{bo, bo, bo, bo});
    }

    /**
     * dummy barn data
     *
     * @return Barn object
     */
    public static BarnSetting barnSettingObject() {
        BarnSetting b = new BarnSetting();
        b.setId(1);
        b.setName("Barn Barn tut");
        b.setBarn(BarnDummy.barnObject());
        b.setCarrot(120);
        b.setRewards(null);
        b.setIsReleased(1);
        b.setDeleted(Boolean.FALSE);
        b.setCreatedBy(1);
        b.setCreatedTime(DateDummy.myLocalDateTime(null));
        b.setLastModifiedTime(DateDummy.myLocalDateTime(null));
        return b;
    }

    /**
     * dummy data for post request body
     * @return
     */
    public static BarnSetting barnSettingRequest() {
    	BarnSetting b = new BarnSetting();
        b.setName("Barn Barn tut");
        b.setCarrot(120);
        b.setIsReleased(1);
        b.setCreatedBy(1);
        return b;
    }

    public static Iterable<BarnSetting> listOfBarnSettings() {
        BarnSetting bo = BarnDummy.barnSettingObject();
        return Arrays.asList(new BarnSetting[]{bo, bo, bo, bo});
    }
}

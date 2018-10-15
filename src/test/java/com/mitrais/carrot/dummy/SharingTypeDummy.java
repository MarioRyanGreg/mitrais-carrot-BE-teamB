package com.mitrais.carrot.dummy;

import java.util.Arrays;

import com.mitrais.carrot.models.ShareType;

/**
 * dummy class of Sharing Type data dummy
 *
 * @author Febri_MW251
 *
 */
public class SharingTypeDummy {

    /**
     * dummy date
     *
     * @return ShareType object
     */
    public static ShareType shareTypeObject() {
        ShareType st = new ShareType();
        st.setId(1);
        st.setSharingType("MMM");
        st.setCarrot(21);
        st.setDeleted(Boolean.FALSE);
        st.setCreatedBy(1);
        st.setCreatedTime(DateDummy.myLocalDateTime(null));
        return st;
    }

    /**
     * ShareType list
     *
     * @return Iterable of ShareType object
     */
    public static Iterable<ShareType> listOfShareTypes() {
        ShareType st = SharingTypeDummy.shareTypeObject();
        return Arrays.asList(new ShareType[]{st, st, st, st});
    }
}

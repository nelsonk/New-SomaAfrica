package com.love.soma.somaafrica.adapter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nelson.Kanyali on 22/06/2016.
 */
public class ExpandableListData extends Activity {



    public static HashMap<String, List<String>> getInfo() {
        HashMap<String, List<String>> sch_details = new HashMap<String, List<String>>();
        List<String> lubiri = new ArrayList<String>();
        lubiri.add("MEG");
        lubiri.add("BCM");
        List<String> macos = new ArrayList<String>();
        macos.add("HEG");
        macos.add("PCM");
        List<String> mengo = new ArrayList<String>();
        mengo.add("LEG");
        mengo.add("BCP");
        List<String> kitende = new ArrayList<String>();
        kitende.add("MEG");
        kitende.add("BCM");

        sch_details.put("Lubiri SS", lubiri);
        sch_details.put("Makerere College", macos);
        sch_details.put("Mengo SS", mengo);
        sch_details.put("ST Marys Kitende", kitende);

        return sch_details;
    }

}

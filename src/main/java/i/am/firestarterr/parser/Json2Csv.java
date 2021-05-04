//package com.firestarterr.parser;
//
//import java.io.*;
//import java.lang.reflect.Type;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Json2Csv {
//
//    class ETSALESMAN {
//
//        @SerializedName("WIN_USER")
//        @Expose
//        public String wINUSER;
//        @SerializedName("SAP_USER")
//        @Expose
//        public String sAPUSER;
//        @SerializedName("COUNTRY")
//        @Expose
//        public String cOUNTRY;
//        @SerializedName("LANGUAGE")
//        @Expose
//        public String lANGUAGE;
//        @SerializedName("SEGMENT")
//        @Expose
//        public String sEGMENT;
//        @SerializedName("SALES_OFFICE")
//        @Expose
//        public String sALESOFFICE;
//
//    }
//
//    class EXPORT {
//
//        @SerializedName("ET_SALESMAN")
//        @Expose
//        public List<ETSALESMAN> eTSALESMAN = new ArrayList<ETSALESMAN>();
//
//    }
//
//    class Response {
//
//        @SerializedName("EXPORT")
//        @Expose
//        public EXPORT eXPORT;
//
//    }
//
//    public static void main(String[] args) throws FileNotFoundException {
//        Gson gson = new Gson();
//        File fileDir = new File("C:\\Users\\Gebbasoft\\Desktop\\workspace\\weking-web\\WeKing\\src\\ehue\\ehue-gettechlist.json");
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(
//                        new FileInputStream(fileDir), StandardCharsets.UTF_8));
//        JsonReader reader = new JsonReader(in);
//        Type responseType = new TypeToken<BaseSapResponse<GetTechnicianListResponseData>>() {
//        }.getType();
//        BaseSapResponse<GetTechnicianListResponseData> response = gson.fromJson(reader, responseType);
//        System.out.println("bpno, " +
//                " name, " +
//                " orgunit, " +
//                " org short, " +
//                " org, " +
//                " username, " +
//                " comp code, " +
//                " comp, " +
//                " country");
//        for (EtTech tech : response.getData().getEtTeches()) {
//            System.out.println(
//                    "'" + tech.getTechNo() + "'" + ", " +
//                            "'" + tech.getTechFullName() + "'" + ", " +
//                            "'" + tech.getOrgUnit() + "'" + ", " +
//                            "'" + tech.getOrgShort() + "'" + ", " +
//                            "'" + tech.getOrgText() + "'" + ", " +
//                            "'" + tech.getUserName() + "'" + ", " +
//                            "'" + tech.getCompCode() + "'" + ", " +
//                            "'" + tech.getCompCodeTxt() + "'" + ", " +
//                            "'" + tech.getCountry() + "'");
//        }
//    }
//
//}
//

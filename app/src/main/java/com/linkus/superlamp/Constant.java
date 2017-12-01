package com.linkus.superlamp;

/**
 * Created by jizi.chen on 2017/8/28.
 */

public class Constant {

    public static String URL_HOST = "http://175.153.153.3:8011";

    public static String URL_Start_API = URL_HOST + "/api/image/List/DZDST/HYYM";

    public static String PortMain = ":8090";

    public static String URL_Main = "http://175.153.153.3" + PortMain;






    ////////////////////////Message ID here begin///////////////////////////////////////////////////
    public static final int PAY_MESSAGE = 0xEE;
    public static final int MESSAGE_PAY_REUSLT = PAY_MESSAGE + 0x00;
    public static final int MESSAGE_PAY_SUCCESS = PAY_MESSAGE + 0x01;
    public static final int MESSAGE_PAY_FAILED = PAY_MESSAGE + 0x02;
    public static final int MESSAGE_PAY_CANCEL = +PAY_MESSAGE + 0x03;


    public static final int MESSAGE_NETWORK = +PAY_MESSAGE + 0x04;


    ////////////////////////Message ID here end///////////////////////////////////////////////////
    public static final String METHOD_NAME = "@method_name";
    public static final int MESSAGE_CHOOSE_METHOD = PAY_MESSAGE + 0x05;
    public static final int MESSAGE_REQUEST_METHOD = PAY_MESSAGE + 0x06;
    /////////////////////////////////////////////////////

    public static final String GROUP_WHITE="@group_white";
    public static final String GROUP_COLOUR="@group_colour";
    public static final String GROUP_SWITCH="@group_switch";
    public static final String GROUP_NONE="@none";


    static {

        switch (0){
            case MESSAGE_PAY_REUSLT:
                break;
            case MESSAGE_PAY_SUCCESS:
                break;
            case MESSAGE_PAY_FAILED:
                break;
            case MESSAGE_PAY_CANCEL:
                break;
            case MESSAGE_NETWORK:
                break;
            case MESSAGE_CHOOSE_METHOD:
                break;

        }
    }


    public enum TRIGGER {
        TRIGGER_FREQUENCE_UNDEFINED("未定义", -1),
        TRIGGER_FREQUENCE_ONCE("单次", 1),
        TRIGGER_FREQUENCE_PERTIME("每次", 2),
        TRIGGER_FREQUENCE_PERHOUR("每小时", 3),
        TRIGGER_FREQUENCE_EVERYDAY("每天", 4),
        TRIGGER_ACTION_OPEN("开", 5),
        TRIGGER_ACTION_CLOSE("关", 6);
        private String name;
        private int value;

        TRIGGER(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public static TRIGGER valueT(String name) {
            TRIGGER[] values = TRIGGER.values();
            for (TRIGGER trigger :
                    values) {
                if (trigger.getName().equals(name))
                    return trigger;
            }
            return TRIGGER_FREQUENCE_UNDEFINED;
        }

    }

    public static  int mainStatusBarHeight = 0;
    public static  int mainBottomHeight = 0 ;
}

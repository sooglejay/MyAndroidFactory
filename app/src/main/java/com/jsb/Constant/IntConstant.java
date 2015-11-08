package com.jsb.constant;

/**
 * Created by JammyQtheLab on 2015/10/22.
 */
public class IntConstant {
    public final static int SEX_MAN = 1;
    public final static int SEX_WOMEN = 0;

    public static final int cancelPauseType_LimitPause =0; //限行停保
    public static final int cancelPauseType_ReservePause =1;//预约停保



    //历史报价  记录 删除 标志
    public static final int DELETE = 0;
    public static final int UNDELETE = 1;

    //我的钱包》》》》提现
    public static final int accountType_bank  = 0;//银联
    public static final int accountType_zhifubao  = 1;//支付宝
    public static final int accountType_weixing  = 2;//微信


    public final  static long milliSecondInADay = 1000*24L*60L*60L;//一天的毫秒数


}

package com.jsb.widget.TimePicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jsb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author baiyuliang
 *
 */
public class MyCalendar extends LinearLayout {

    private static Context context;
    
    private Date inDay;
    private String indayString ="", outdayString ="";
    public static View inView;
    public static View outView;
    public static String positionInString;
    public static String positionOutString;
    
    static long milliSecondInADay = 1000*24L*60L*60L;//一天的毫秒数
    
    private  List<String> daysList;//存放天

    private OnDaySelectListener callBack;//回调函数
    
	private static String nowdayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) ;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");//日期格式化
    
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");//日期格式化

    /**
     * 构造函数
     * @param context
     */
    public MyCalendar(Context context) {
        super(context);
        MyCalendar.context = context;
    }

    /**
     * 构造函数
     * @param context
     */
    public MyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyCalendar.context = context;
    }
    
    public void setInDay(String inday){
    	this.indayString =inday;
    }
    
    public void setOutDay(String outday){
    	this.outdayString =outday;
    }
    
    public void setTheDay(Date dateIn){
    	this.inDay =dateIn;
    	init();
    }

    /**
     * 初始化日期以及view等控件
     */
    private void init() {
    	 daysList = new ArrayList<String>();//存放天
        final Calendar cal = Calendar.getInstance();//获取日历实例
        cal.setTime(inDay);//cal设置为当天的
        cal.set(Calendar.DATE, 1);//cal设置当前day为当前月第一天
        int weekThAtTheFirstDayInTheMonth = countNeedHowMuchEmpety(cal);//获取当前月第一天为星期几
        int daysCountAtTheMonth = getDayNumInMonth(cal);//获取当前月有多少天
        setGvListData(weekThAtTheFirstDayInTheMonth, daysCountAtTheMonth,cal.get(Calendar.YEAR)+"-"+getMonth((cal.get(Calendar.MONTH)+1)));



        View view = LayoutInflater.from(context).inflate(R.layout.view_calendar, this, true);//获取布局，开始初始化
        TextView tv_year= (TextView) view.findViewById(R.id.tv_year);

        tv_year.setVisibility(View.VISIBLE);
        tv_year.setText(cal.get(Calendar.YEAR)+"年");

        TextView tv_month= (TextView) view.findViewById(R.id.tv_month);
        tv_month.setText(String.valueOf(inDay.getMonth()+1));
        MyGridView gv = (MyGridView) view.findViewById(R.id.gv_calendar);
        calendarGridViewAdapter gridViewAdapter = new calendarGridViewAdapter(daysList, indayString, outdayString);

        gv.setAdapter(gridViewAdapter);
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1,int position, long arg3) {
                String choiceDay = (String) adapterView.getAdapter().getItem(position);
                String[] date=choiceDay.split(",");
                String day=date[1];
                if (!" ".equals(day)) {
                    if (Integer.parseInt(day) < 10) {
                    	day = "0" + date[1];
                    }
                    choiceDay = date[0] +"-"+ day;
                    if (callBack != null) {//调用回调函数回调数据
                        callBack.onDaySelectListener(arg1,choiceDay);
                    }
                }
            }
        });
    }


    /**
     * 为gridview中添加需要展示的数据
     * @param weekThOnTheFirstDayInTheMonth
     * @param dayNumInMonth
     */
    private void setGvListData(int weekThOnTheFirstDayInTheMonth, int dayNumInMonth,String YM) {
        daysList.clear();
        for (int i = 0; i < weekThOnTheFirstDayInTheMonth; i++) {
            daysList.add(" , ");
        }
        for (int j = 1; j <= dayNumInMonth; j++) {
            daysList.add(YM + "," + String.valueOf(j));
        }
    }

    /**
     *
     * @param month
     * @return
     */
    private String getMonth(int month) {
    	String mon="";
    	if(month<10){
    		mon="0"+month;
    	}else{
    		mon=""+month;
    	}
    	return mon;
    }

    /**
     * 获取当前月的总共天数
     * @param cal
     * @return
     */
    private int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前月第一天在第一个礼拜的第几天，得出第一天是星期几
     * @param cal
     * @return
     */
    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }


    /**
     * gridview中adapter的viewholder
     * @author Administrator
     *
     */
    static class GrideViewHolder {
        TextView tv_up, tv_bottom;
    }

    /**
     * gridview的adapter
     * @author Administrator
     *
     */
    static class calendarGridViewAdapter extends BaseAdapter {
    	
    	List<String> gvList ;//存放天
    	String inday,outday;
    	
    	public calendarGridViewAdapter(List<String> gvList,String inday,String outday){
    		super();
    		this.gvList=gvList;
    		this.inday=inday;
    		this.outday=outday;
    	}

        @Override
        public int getCount() {
            return gvList.size();
        }

        @Override
        public String getItem(int position) {
            return gvList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GrideViewHolder holder;
            if (convertView == null) {
                holder = new GrideViewHolder();
                convertView = inflate(context,R.layout.item_calendar_gridview, null);
                holder.tv_bottom = (TextView) convertView.findViewById(R.id.tv_bottom);
                holder.tv_up = (TextView) convertView.findViewById(R.id.tv_up);
                convertView.setTag(holder);
            } else {
                holder = (GrideViewHolder) convertView.getTag();
            }
            String[] date=getItem(position).split(",");
            holder.tv_up.setText(date[1]);
            if((position+1)%7==0||(position)%7==0){
            	holder.tv_up.setTextColor(context.getResources().getColor(R.color.light_red));
            }
            if(!date[1].equals(" ")){
            	 String day=date[1];
                 if(Integer.parseInt(date[1])<10){
                 	day="0"+date[1];
                 }
                 if((date[0]+"-"+day).equals(nowdayString)){
                 	holder.tv_up.setTextColor(context.getResources().getColor(R.color.orange_color));
                 	holder.tv_up.setText("今天");
                 }
                 if(!"".equals(inday)&&(date[0]+"-"+day).equals(inday)){
                	 convertView.setBackgroundColor(context.getResources().getColor(R.color.calendar_color_inDay));
                	 holder.tv_up.setTextColor(Color.WHITE);
                	 holder.tv_up.setText(date[1]);
                	 holder.tv_bottom.setText("入住");
                	 inView =convertView;//
                	 positionInString =date[1];
                 }
                 if(!"".equals(outday)&&(date[0]+"-"+day).equals(outday)){
                	 convertView.setBackgroundColor(context.getResources().getColor(R.color.calendar_color_inDay));
                	 holder.tv_up.setTextColor(Color.WHITE);
                	 holder.tv_up.setText(date[1]);
                	 holder.tv_bottom.setText("离开");
                	 outView =convertView;
                	 positionOutString =date[1];
                 }
                 try {
                	 //若日历日期<当前日期，则不能选择
					if(dateFormat2.parse(date[0]+"-"+day).getTime()<dateFormat2.parse(nowdayString).getTime()){
						holder.tv_up.setTextColor(Color.parseColor("#999999"));
					 }
//					//若日历日期-当前日期>90天，则不能选择
//					long dayxc=(dateFormat2.parse(date[0]+"-"+day).getTime()-dateFormat2.parse(nowdayString).getTime())/ milliSecondInADay;
//					if(dayxc>90){
//						holder.tv_up.setTextColor(Color.parseColor("#999999"));
//					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }
            
            return convertView;
        }
    }

    /**
     * 自定义监听接口
     * @author Administrator
     *
     */
    public interface OnDaySelectListener {
        void onDaySelectListener(View view, String date);
    }

    /**
     * 自定义监听接口设置对象
     * @param o
     */
    public void setOnDaySelectListener(OnDaySelectListener o) {
        callBack = o;
    }
}

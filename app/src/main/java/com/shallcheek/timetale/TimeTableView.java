package com.shallcheek.timetale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 课表显示View
 *
 * @author shallcheek
 */
public class TimeTableView extends LinearLayout {
    /**
     * 配色数组
     */
    public static int colors[] = {R.drawable.select_label_san,
            R.drawable.select_label_er, R.drawable.select_label_si,
            R.drawable.select_label_wu, R.drawable.select_label_liu,
            R.drawable.select_label_qi, R.drawable.select_label_ba,
            R.drawable.select_label_jiu, R.drawable.select_label_sss,
            R.drawable.select_label_se, R.drawable.select_label_yiw,
            R.drawable.select_label_sy, R.drawable.select_label_yiwu,
            R.drawable.select_label_yi, R.drawable.select_label_wuw};
    private final static int START = 0;
    //最大节数
    public final static int MAXNUM = 10;
    //显示到星期几
    public final static int WEEKNUM = 7;
    /**
     * 单个View高度
     */
    private final static int TIME_TABLE_HEIGHT = 60;
    /**
     * 线的高度
     */
    private final static int TIME_TABLE_LINE_HEIGHT = 2;
    private final static int LEFT_TITLE_WIDTH = 20;
    private final static int WEEK_TITLE_HEIGHT = 30;
    /**
     * 第一行的星期显示
     */
    private LinearLayout mHorizontalWeekLayout;
    private LinearLayout mVerticalWeekLaout;
    private String[] mWeekTitle = {"一", "二", "三", "四", "五", "六", "七"};
    public static String[] colorStr = new String[20];
    int colorNum = 0;
    private List<TimeTableModel> mListTimeTable = new ArrayList<TimeTableModel>();
    private Context mContext;
    private int weekNum;

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 横的分界线
     *
     * @return
     */
    private View getWeekHorizontalLine() {
        View line = new View(getContext());
        line.setBackgroundColor(getResources().getColor(R.color.view_line));
        line.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, TIME_TABLE_LINE_HEIGHT));
        return line;
    }

    /**
     * 竖向分界线
     *
     * @return
     */
    private View getWeekVerticalLine() {
        View line = new View(mContext);
        line.setBackgroundColor(getResources().getColor(R.color.view_line));
        line.setLayoutParams(new ViewGroup.LayoutParams((TIME_TABLE_LINE_HEIGHT), dip2px(WEEK_TITLE_HEIGHT)));
        return line;
    }


    private void initView(int WNumber) {
        weekNum=WNumber;
        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(HORIZONTAL);
        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(HORIZONTAL);

        //表格
        for (int i = 0; i <= WEEKNUM; i++) {
            if (i == 0) {
                layoutLeftNumber();
            } else {
                layoutWeekTitleView(i);
                layoutContentView(i);
            }

            mVerticalWeekLaout.addView(createTableVerticalLine());
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekHorizontalLine());
        addView(mVerticalWeekLaout);
    }

    @NonNull
    private View createTableVerticalLine() {
        View l = new View(getContext());
        l.setLayoutParams(new ViewGroup.LayoutParams(TIME_TABLE_LINE_HEIGHT, dip2px(TIME_TABLE_HEIGHT * MAXNUM) + (MAXNUM - 2) * TIME_TABLE_LINE_HEIGHT));
        l.setBackgroundColor(getResources().getColor(R.color.view_line));
        return l;
    }

    private void layoutContentView(int week) {
        List<TimeTableModel> weekClassList = findWeekClassList(week);
        //添加
        LinearLayout mLayout = createWeekTimeTableView(weekClassList, week);
        mLayout.setOrientation(VERTICAL);
        mLayout.setLayoutParams(new ViewGroup.LayoutParams((getViewWidth() - dip2px(20)) / WEEKNUM, LayoutParams.MATCH_PARENT));
        mLayout.setWeightSum(1);
        mVerticalWeekLaout.addView(mLayout);
    }

    /**
     * 遍历出星期1~7的课表
     * 再进行排序
     *
     * @param week 星期
     */
    @NonNull
    private List<TimeTableModel> findWeekClassList(int week) {
        List<TimeTableModel> list = new ArrayList<>();
        for (TimeTableModel timeTableModel : mListTimeTable) {
            String Num=timeTableModel.getWeeknum();
            String weekNumStart=Num.substring(0,Num.indexOf("-"));
            String weekNumEnd=Num.substring(Num.indexOf("-")+1,Num.length());
            if (timeTableModel.getWeek() == week && Integer.parseInt(weekNumStart)<=weekNum &&Integer.parseInt(weekNumEnd)>=weekNum) {
                list.add(timeTableModel);
            }
        }

        Collections.sort(list, new Comparator<TimeTableModel>() {
            @Override
            public int compare(TimeTableModel o1, TimeTableModel o2) {
                return o1.getStartnum() - o2.getStartnum();
            }
        });

        return list;
    }

    private void layoutWeekTitleView(int weekNumber) {
        TextView weekText = new TextView(getContext());
        weekText.setTextColor(getResources().getColor(R.color.text_color));
        weekText.setWidth(((getViewWidth() - dip2px(LEFT_TITLE_WIDTH))) / WEEKNUM);
        weekText.setHeight(dip2px(WEEK_TITLE_HEIGHT));
        weekText.setGravity(Gravity.CENTER);
        weekText.setTextSize(16);
        weekText.setText(mWeekTitle[weekNumber - 1]);
        mHorizontalWeekLayout.addView(weekText);
    }


    private void layoutLeftNumber() {
        //课表出的0,0格子 空白的
        TextView mTime = new TextView(mContext);
        mTime.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(WEEK_TITLE_HEIGHT)));
        mHorizontalWeekLayout.addView(mTime);

        //绘制1~MAXNUM
        LinearLayout numberView = new LinearLayout(mContext);
        numberView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(MAXNUM * TIME_TABLE_HEIGHT) + MAXNUM * 2));
        numberView.setOrientation(VERTICAL);
        for (int j = 1; j <= MAXNUM; j++) {
            TextView number = createNumberView(j);
            numberView.addView(number);
            numberView.addView(getWeekHorizontalLine());
        }
        mVerticalWeekLaout.addView(numberView);
    }

    @NonNull
    private TextView createNumberView(int j) {
        TextView number = new TextView(getContext());
        number.setLayoutParams(new ViewGroup.LayoutParams(dip2px(LEFT_TITLE_WIDTH), dip2px(TIME_TABLE_HEIGHT)));
        number.setGravity(Gravity.CENTER);
        number.setTextColor(getResources().getColor(R.color.text_color));
        number.setTextSize(14);
        number.setText(String.valueOf(j));
        return number;
    }

    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 绘制空白
     *
     * @param count 数量
     * @param week  星期
     * @param start 用着计算下标
     */
    private View addBlankView(int count, final int week, final int start) {
        LinearLayout blank = new LinearLayout(getContext());
        blank.setOrientation(VERTICAL);
        for (int i = 1; i < count; i++) {
            View classView = new View(getContext());
            classView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(TIME_TABLE_HEIGHT)));
            blank.addView(classView);
            blank.addView(getWeekHorizontalLine());
            final int num = i;
            //这里可以处理空白处点击添加课表
            classView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "星期" + week + "第" + (start + num) + "节", Toast.LENGTH_LONG).show();
                }
            });

        }
        return blank;
    }

    /**
     * 星期一到星期天的课表
     *
     * @param weekList 每天的课程列表
     * @param week     周
     */
    private LinearLayout createWeekTimeTableView(List<TimeTableModel> weekList, int week) {
        LinearLayout weekTableView = new LinearLayout(getContext());
        weekTableView.setOrientation(VERTICAL);
        int size = weekList.size();
        if (weekList.isEmpty()) {
            weekTableView.addView(addBlankView(MAXNUM + 1, week, 0));
        } else {
            for (int i = 0; i < size; i++) {
                TimeTableModel tableModel = weekList.get(i);
                if (i == 0) {
                    //添加的0到开始节数的空格
                    weekTableView.addView(addBlankView(tableModel.getStartnum(), week, 0));
                    weekTableView.addView(createClassView(tableModel));
                } else if (weekList.get(i).getStartnum() - weekList.get(i - 1).getEndnum() > 0) {
                    //填充
                    weekTableView.addView(addBlankView(weekList.get(i).getStartnum() - weekList.get(i - 1).getEndnum(), week, weekList.get(i - 1).getEndnum()));
                    weekTableView.addView(createClassView(weekList.get(i)));
                }
                //绘制剩下的空白
                if (i + 1 == size) {
                    weekTableView.addView(addBlankView(MAXNUM - weekList.get(i).getEndnum() + 1, week, weekList.get(i).getEndnum()));
                }
            }
        }
        return weekTableView;
    }

    /**
     * 获取单个课表View 也可以自定义我这个
     *
     * @param model 数据类型
     * @return
     */
    @SuppressWarnings("deprecation")
    private View createClassView(final TimeTableModel model) {
        final LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        int num = (model.getEndnum() - model.getStartnum());
        mTimeTableView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px((num + 1) * TIME_TABLE_HEIGHT) + (num + 1) * TIME_TABLE_LINE_HEIGHT));

        TextView mTimeTableNameView = new TextView(getContext());
        mTimeTableNameView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px((num + 1) * TIME_TABLE_HEIGHT) + (num) * TIME_TABLE_LINE_HEIGHT));

        mTimeTableNameView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        mTimeTableNameView.setTextSize(12);
        mTimeTableNameView.setGravity(Gravity.CENTER);
        mTimeTableNameView.setText(model.getName()+"\n" + model.getTeacher()+"\n" +model.getWeeknum()+"周"+"\n"+ model.getClassroom());

        mTimeTableView.addView(mTimeTableNameView);
        mTimeTableView.addView(getWeekHorizontalLine());

        mTimeTableView.setBackgroundDrawable(getContext().getResources()
                .getDrawable(colors[getColorNum(model.getName())]));
        mTimeTableView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                customDialog(model);
                // Toast.makeText(getContext(), model.getName() + "@" + model.getClassroom(), Toast.LENGTH_LONG).show();
            }
        });
        return mTimeTableView;
    }

    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    public void setTimeTable(List<TimeTableModel> mlist,int Number) {

        this.mListTimeTable = mlist;
        for (TimeTableModel timeTableModel : mlist) {
            addTimeName(timeTableModel.getName());
        }

        initView(Number);
        invalidate();
    }

    /**
     * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorSt[20]数组
     *
     * @param name
     */
    private void addTimeName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colorNum] = name;
            colorNum++;
        }
    }

    /**
     * 获取数组中的课程名
     *
     * @param name
     * @return
     */
    public static int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }

    /**
     * 自定义添加课表框
     */
    public void customDialog(TimeTableModel timeTableModel) {
        final TimeTableModel tableModel = new TimeTableModel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom, null);
        dialog.setView(dialogView);
        dialog.show();
        final Spinner weekS = dialogView.findViewById(R.id.week);
        final Spinner numberS = dialogView.findViewById(R.id.number);
        final EditText className = dialogView.findViewById(R.id.className);
        final EditText classTeacher = dialogView.findViewById(R.id.classTeacher);
        final EditText weekStart = dialogView.findViewById(R.id.weekStart);
        final EditText weekEnd = dialogView.findViewById(R.id.weekend);
        final EditText classRoom = dialogView.findViewById(R.id.classRoom);
        final Button btn_login = dialogView.findViewById(R.id.btn_login);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);
        btn_login.setText("删除");
        className.setText(timeTableModel.getName());
        classTeacher.setText(timeTableModel.getTeacher());
        classRoom.setText(timeTableModel.getClassroom());
        String Num=timeTableModel.getWeeknum();
        String weekNumStart=Num.substring(0,Num.indexOf("-"));
        String weekNumEnd=Num.substring(Num.indexOf("-")+1,Num.length());
        int classStartNum = 0;
        if(timeTableModel.getStartnum()==1){
             classStartNum=timeTableModel.getStartnum()-1;
        }else if(timeTableModel.getStartnum()==3) {
            classStartNum = timeTableModel.getStartnum() - 2;
        }else if(timeTableModel.getStartnum()==5) {
            classStartNum = timeTableModel.getStartnum() - 3;
        }else if(timeTableModel.getStartnum()==7) {
            classStartNum = timeTableModel.getStartnum() - 4;
        }else if(timeTableModel.getStartnum()==9) {
            classStartNum = timeTableModel.getStartnum() - 5;
        }

        Log.d("myLog",String.valueOf(classStartNum)+"_"+String.valueOf(timeTableModel.getStartnum()));
        
        weekStart.setText(weekNumStart);
        weekEnd.setText(weekNumEnd);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.week, android.R.layout.simple_spinner_item);
        weekS.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.number, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberS.setAdapter(adapter1);
        weekS.setSelection(timeTableModel.getWeek()-1, true);
        numberS.setSelection(classStartNum, true);
        weekS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的元素，注意还要传入R.array.weekdata文件
                String week = getContext().getResources().getStringArray(R.array.week)[position];     //获取选择的item内容
                //输出看看
              //  Toast.makeText(getContext(), "获取：" + String.valueOf(getInt(week)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的元素，注意还要传入R.array.weekdata文件
                String number = getContext().getResources().getStringArray(R.array.number)[position];     //获取选择的item内容
                String startNum = number.substring(0, number.indexOf("–"));
                //获取结果值
                String endNum = number.substring(number.indexOf("–") + 1, number.length());
                tableModel.setStartnum(Integer.parseInt(startNum));
                tableModel.setEndnum(Integer.parseInt(endNum));
                Toast.makeText(getContext(), "获取：" + number, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "无删除功能", Toast.LENGTH_SHORT).show();
//                String name = className.getText().toString();
//                String teacher = classTeacher.getText().toString();
//                String room = classRoom.getText().toString();
//                String weekS = weekStart.getText().toString();
//                String weekE = weekEnd.getText().toString();
//                String weekNum = weekS + "-" + weekE;
//                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(teacher) || TextUtils.isEmpty(room)) {
//                    Toast.makeText(getContext(), "课程名称或任课老师或上课教室不能为空!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                tableModel.setId(0);
//                tableModel.setName(name);
//                tableModel.setTeacher(teacher);
//                tableModel.setClassroom(room);
//                tableModel.setWeeknum(weekNum);
//                TimeTableModelDao timeTableModelViewModel = null;
//                timeTableModelViewModel.deleteTimeTableModel(tableModel);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}

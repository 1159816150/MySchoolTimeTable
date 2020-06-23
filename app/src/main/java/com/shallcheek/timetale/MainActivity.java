package com.shallcheek.timetale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    private Button addClassButton;
    private int weekNumber = 2;
    boolean ifFirst = true;
    TimeTableModelViewModel timeTableModelViewModel;
    String myLog = "logg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTableModelViewModel = new ViewModelProvider(this).get(TimeTableModelViewModel.class);
        addClassButton = findViewById(R.id.addClass);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        getWeekNumber();
        timeTableModelViewModel.getAllTimeTableModelLive().observe(this, new Observer<List<TimeTableModel>>() {
            @Override
            public void onChanged(List<TimeTableModel> timeTableModels) {
                mList = timeTableModels;
                mTimaTableView.removeAllViews();
                mTimaTableView.setTimeTable(mList, weekNumber);
            }
        });
        if (ifFirst) {
            addList();
            for (TimeTableModel t : mList) {
                timeTableModelViewModel.insertTimeTableModel(t);
            }
            ifFirst = false;
        }
        mTimaTableView.setTimeTable(mList, weekNumber);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    void getWeekNumber() {
        final Spinner spinnerWeek = findViewById(R.id.spinnerWeek);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.weekNum, android.R.layout.simple_spinner_item);
        spinnerWeek.setAdapter(adapter2);
        spinnerWeek.setSelection(weekNumber, true);
        spinnerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的元素，注意还要传入R.array.weekdata文件
                String weekNum = MainActivity.this.getResources().getStringArray(R.array.weekNum)[position];     //获取选择的item内容
                //输出看看
                String Num = weekNum.substring(1, weekNum.length() - 1);
                weekNumber = getWeekNumInt(Num);
                mTimaTableView.removeAllViews();
                mTimaTableView.setTimeTable(mList, weekNumber);
                Toast.makeText(MainActivity.this, "获取：" + String.valueOf(getWeekNumInt(Num)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void addList() {
        mList.add(new TimeTableModel(1, 2, 1, "计算机操作系统",
                "文勇", "逸夫楼504", "2-13"));
        mList.add(new TimeTableModel(3, 4, 1, "计算机英语",
                "刘美玲", "逸夫楼504", "2-13"));
        mList.add(new TimeTableModel(5, 6, 1, "移动软件开发",
                "周卫", "逸夫楼506", "2-13"));
        mList.add(new TimeTableModel(7, 8, 2, "Linux",
                "靳庆庚", "逸夫楼506", "2-13"));
        mList.add(new TimeTableModel(9, 10, 2, "计算机操作系统",
                "文勇", "逸夫楼506", "2-13"));
        mList.add(new TimeTableModel(1, 2, 3, "计算机英语",
                "刘美玲", "学友楼104", "2-13"));
        mList.add(new TimeTableModel(5, 6, 3, "软件设计模式",
                "张纲强", "学友楼504", "2-13"));
        mList.add(new TimeTableModel(7, 8, 4, "软件设计模式",
                "张纲强", "校友楼504", "2-13"));
        mList.add(new TimeTableModel(3, 4, 4, "Linux",
                "靳庆庚", "校友楼401", "2-13"));
        mList.add(new TimeTableModel(5, 6, 5, "C#",
                "谢宁新", "校友楼401", "2-13"));
        mList.add(new TimeTableModel(3, 4, 5, "课程设计III",
                "李熹", "校友楼401", "2-13"));
        mList.add(new TimeTableModel(9, 10, 3, "就业指导",
                "潘艳艳", "学友楼402", "13-15"));

    }

    /**
     * 自定义添加课表框
     */
    public void customDialog() {
        final TimeTableModel tableModel = new TimeTableModel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_custom, null);
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
        btn_login.setText("添加");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.week, android.R.layout.simple_spinner_item);
        weekS.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.number, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberS.setAdapter(adapter1);
        weekS.setSelection(0, true);
        numberS.setSelection(0, true);
        weekS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的元素，注意还要传入R.array.weekdata文件
                String week = MainActivity.this.getResources().getStringArray(R.array.week)[position];     //获取选择的item内容
                //输出看看

                tableModel.setWeek(getInt(week));

                Toast.makeText(MainActivity.this, "获取：" + String.valueOf(getInt(week)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的元素，注意还要传入R.array.weekdata文件
                String number = MainActivity.this.getResources().getStringArray(R.array.number)[position];     //获取选择的item内容
                String startNum = number.substring(0, number.indexOf("–"));
                //获取结果值
                String endNum = number.substring(number.indexOf("–") + 1, number.length());
                Log.d(myLog, startNum + "/" + endNum);
                tableModel.setStartnum(Integer.parseInt(startNum));
                tableModel.setEndnum(Integer.parseInt(endNum));
                Toast.makeText(MainActivity.this, "获取：" + number, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = className.getText().toString();
                String teacher = classTeacher.getText().toString();
                String room = classRoom.getText().toString();
                String weekS = weekStart.getText().toString();
                String weekE = weekEnd.getText().toString();
                String weekNum = weekS + "-" + weekE;
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(teacher) || TextUtils.isEmpty(room)) {
                    Toast.makeText(MainActivity.this, "课程名称或任课老师或上课教室不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                tableModel.setId(0);
                tableModel.setName(name);
                tableModel.setTeacher(teacher);
                tableModel.setClassroom(room);
                tableModel.setWeeknum(weekNum);
                timeTableModelViewModel.insertTimeTableModel(tableModel);
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

    private void popFromBottom(Dialog dialog) {
        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM);   // 这里控制弹出的位置
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawable(null);
        win.setAttributes(lp);
    }

    // 设置屏幕背景变暗
    private void setScreenBgDarken() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
    }

    // 设置屏幕背景变亮
    private void setScreenBgLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 1.0f;
        getWindow().setAttributes(lp);
    }

    public static int getInt(String str) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("日", 7);

        char a[] = str.toCharArray();
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            String s = Character.toString(a[i]);
            b.append(map.get(s));
        }
        int i = Integer.parseInt(b.toString());
        return i;
    }

    public static int getWeekNumInt(String str) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("十", 10);
        map.put("十一", 11);
        map.put("十二", 12);
        map.put("十三", 13);
        map.put("十四", 14);
        map.put("十五", 15);
        map.put("十六", 16);
        map.put("十七", 17);
        map.put("十八", 18);
        map.put("十九", 19);
        char a[] = str.toCharArray();
        String b = new String();
        for (int i = 0; i < a.length; i++) {
            String s = Character.toString(a[i]);
            b += map.get(s);
        }
        if (b.length() == 3) {
            b = b.substring(0, 1) + b.substring(2, 3);
        }
        int i = Integer.parseInt(b.toString());

        return i;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_screenshot:
                customDialog();

                //List<TimeTableModel> L = null;
                // mTimaTableView.setTimeTable(L);

                break;
        }
        return true;
    }
}

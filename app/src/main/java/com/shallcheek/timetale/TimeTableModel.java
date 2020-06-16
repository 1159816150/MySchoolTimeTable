package com.shallcheek.timetale;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TimeTableModel {
	@PrimaryKey(autoGenerate = true)
	private int id;
	private int startnum;
	private int endnum;
	private int week;
	private String name="";
	private String teacher="";
	private String classroom="";
	private String weeknum="";
	public boolean isWeenNum;

	@Override
	public String toString() {
		return "TimeTableModel{" +
				"id=" + id +
				", startnum=" + startnum +
				", endnum=" + endnum +
				", week=" + week +
				", name='" + name + '\'' +
				", teacher='" + teacher + '\'' +
				", classroom='" + classroom + '\'' +
				", weeknum='" + weeknum + '\'' +
				", isWeenNum=" + isWeenNum +
				'}';
	}

	@Ignore
	public TimeTableModel() {
		super();
	}
	public TimeTableModel(int startnum, int endnum, int week, String name, String teacher, String classroom, String weeknum) {

		this.startnum = startnum;
		this.endnum = endnum;
		this.week = week;
		this.name = name;
		this.teacher = teacher;
		this.classroom = classroom;
		this.weeknum = weeknum;
	}

	public boolean isWeenNum() {
		return isWeenNum;
	}

	public void setWeenNum(boolean weenNum) {
		isWeenNum = weenNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStartnum() {
		return startnum;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getWeeknum() {
		return weeknum;
	}

	public void setWeeknum(String weeknum) {
		this.weeknum = weeknum;
	}
}

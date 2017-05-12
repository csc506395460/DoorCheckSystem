package com.csc.cm.doorchecksystem.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.data.api.BaseApi;
import com.csc.cm.doorchecksystem.data.model.CheckResult;
import com.csc.cm.doorchecksystem.data.tool.TimeTranUtil;
import com.csc.cm.doorchecksystem.ui.activity.MainActivity;
import com.csc.cm.doorchecksystem.ui.activity.StudentInfoActivity;
import com.csc.cm.doorchecksystem.ui.adapter.AttendanceAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/1/16.
 */

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, AbsListView.OnScrollListener{

    private Context mContext;
    private View RootView;
    private ListView mLvAttendanceView;
    private CheckResult checkResult;
    private List<CheckResult.CheckInfo> adapterList;
    private AttendanceAdapter mAdapter;
    private ImageButton ivMainMenu, ibtMainSelectDate;

    private TextView tvMainDate;
    private View llTotal, llSign, llUnsign;
    private TextView tvMainTotal, tvMainTotalName;
    private TextView tvMainSign, tvMainSignName;
    private TextView tvMainUnsign, tvMainUnsignName;

    private ProgressDialog progressDialog;

    private int uersId;
    private int mOffset = 0;
    private int mNumber = 5;
    private int mCount = 0;

    private int mYear, mMonth, mDay;
    private String mWay;
    private Calendar ca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);

        RootView = inflater.inflate(R.layout.layout_main, container, false);

        initView();
        initData();

        return RootView;
    }

    private void initView() {
        mLvAttendanceView = (ListView) RootView.findViewById(R.id.lv_attendance);
        ivMainMenu = (ImageButton) RootView.findViewById(R.id.iv_main_menu);
        ibtMainSelectDate = (ImageButton) RootView.findViewById(R.id.ibt_main_select_date);
        tvMainTotal = (TextView) RootView.findViewById(R.id.tv_main_total);
        tvMainSign = (TextView) RootView.findViewById(R.id.tv_main_sign);
        tvMainUnsign = (TextView) RootView.findViewById(R.id.tv_main_unsign);
        tvMainTotalName = (TextView) RootView.findViewById(R.id.tv_main_total_name);
        tvMainSignName = (TextView) RootView.findViewById(R.id.tv_main_sign_name);
        tvMainUnsignName = (TextView) RootView.findViewById(R.id.tv_main_unsign_name);
        //llShowAll = RootView.findViewById(R.id.ll_show_all);
        llTotal = RootView.findViewById(R.id.ll_main_total);
        llSign = RootView.findViewById(R.id.ll_main_sign);
        llUnsign = RootView.findViewById(R.id.ll_main_unsign);
        tvMainDate = (TextView) RootView.findViewById(R.id.tv_main_date);

        adapterList = new ArrayList<>();
        mAdapter = new AttendanceAdapter(getActivity(),adapterList);
        mLvAttendanceView.setAdapter(mAdapter);

        mLvAttendanceView.setOnItemClickListener(this);
        mLvAttendanceView.setOnScrollListener(this);
        ivMainMenu.setOnClickListener(this);
        ibtMainSelectDate.setOnClickListener(this);
        //llShowAll.setOnClickListener(this);
        llTotal.setOnClickListener(this);
        llSign.setOnClickListener(this);
        llUnsign.setOnClickListener(this);
    }

    private void initData() {
        ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mWay = String.valueOf(ca.get(Calendar.DAY_OF_WEEK));
        display();

        uersId = ((MainActivity)getActivity()).getUserInfo().id;
        //findCheckInfo(0);
        findCheckInfo(1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), StudentInfoActivity.class);
        intent.putExtra("empId", adapterList.get(position).employeeId);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_menu:
                ((MainActivity)getActivity()).OpenLeftMenu();
                break;
//            case R.id.ll_show_all:
//                findCheckInfo(1);
//                showMode(0);
//                break;
            case R.id.ll_main_total:
                showMode(0);
                break;
            case R.id.ll_main_sign:
                showMode(1);
                break;
            case R.id.ll_main_unsign:
                showMode(2);
                break;
            case R.id.ibt_main_select_date:
                showDialog();
                break;
        }
    }

    private void showMode(int type) {
        if (checkResult == null)
            return;
        adapterList.clear();
        switch (type) {
            case 0:
                llTotal.setBackgroundResource(R.color.main_green_color);
                llSign.setBackgroundResource(R.color.login_line_color);
                llUnsign.setBackgroundResource(R.color.login_line_color);
                tvMainTotal.setTextColor(getResources().getColor(R.color.text_white_color));
                tvMainSign.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainUnsign.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainTotalName.setTextColor(getResources().getColor(R.color.text_white_color));
                tvMainSignName.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainUnsignName.setTextColor(getResources().getColor(R.color.text_black_color));

                for (CheckResult.CheckInfo info:checkResult.list) {
                    adapterList.add(info);
                }
                mAdapter.setAdapter(adapterList);
                break;
            case 1:
                llTotal.setBackgroundResource(R.color.login_line_color);
                llSign.setBackgroundResource(R.color.main_green_color);
                llUnsign.setBackgroundResource(R.color.login_line_color);
                tvMainTotal.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainSign.setTextColor(getResources().getColor(R.color.text_white_color));
                tvMainUnsign.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainTotalName.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainSignName.setTextColor(getResources().getColor(R.color.text_white_color));
                tvMainUnsignName.setTextColor(getResources().getColor(R.color.text_black_color));
                for (CheckResult.CheckInfo info: checkResult.list) {
                    if (info.eventName != null) {
                        if (info.eventName.equals("进门")) {
                            adapterList.add(info);
                        }
                    }
                }
                mAdapter.setAdapter(adapterList);
                break;
            case 2:
                llTotal.setBackgroundResource(R.color.login_line_color);
                llSign.setBackgroundResource(R.color.login_line_color);
                llUnsign.setBackgroundResource(R.color.main_green_color);
                tvMainTotal.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainSign.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainUnsign.setTextColor(getResources().getColor(R.color.text_white_color));
                tvMainTotalName.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainSignName.setTextColor(getResources().getColor(R.color.text_black_color));
                tvMainUnsignName.setTextColor(getResources().getColor(R.color.text_white_color));
                for (CheckResult.CheckInfo info: checkResult.list) {
                    if (info.eventName == null || !info.eventName.equals("进门")) {
                        adapterList.add(info);
                    }
                }
                mAdapter.setAdapter(adapterList);
                break;
        }
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        tvMainDate.setText(new StringBuffer().append(mMonth + 1).append("月").append(mDay).append("日 周").append(mWay));
    }

    private void showDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, mdateListener, mYear, mMonth, mDay);
        dialog.show();

    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            ca.set(year, monthOfYear, dayOfMonth);
            mWay = String.valueOf(ca.get(Calendar.DAY_OF_WEEK));
            display();

            //findCheckInfo(0);
            findCheckInfo(1);
            showMode(0);
        }
    };


    /**
     * 查询考勤数据
     * @param type 0：分页查询，1：查询所有
     */
    private void findCheckInfo(int type) {
        if (type == 0) {
            if (mOffset > mCount) return;
        }
        progressDialog.setMessage("正在获取考勤数据...请稍后");
        progressDialog.show();

        String date = TimeTranUtil.dateTimeToString(ca.getTimeInMillis());
        Call<CheckResult> call = BaseApi.getInstance().postCheck(uersId, date, type, mOffset, mNumber);
        dealData(call);
    }

    /**
     * 处理返回的数据
     * @param call
     */
    private void dealData(Call<CheckResult> call) {
        call.enqueue(new Callback<CheckResult>() {
            @Override
            public void onResponse(Call<CheckResult> call, Response<CheckResult> response) {
                checkResult = response.body();

                if (checkResult.list == null) {
                    Toast.makeText(mContext,"无数据！！！",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                adapterList.clear();
                for (CheckResult.CheckInfo info:checkResult.list) {
                    adapterList.add(info);
                }
                mCount = checkResult.totalNum;

                tvMainTotal.setText(String.valueOf(checkResult.totalNum));
                tvMainSign.setText(String.valueOf(checkResult.signNum));
                tvMainUnsign.setText(String.valueOf(checkResult.unSignNum));
                mAdapter.setAdapter(adapterList);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CheckResult> call, Throwable t) {
                Toast.makeText(mContext,"无法连接服务器，请检查网络！",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int lastVisiblePosition = mLvAttendanceView
                .getLastVisiblePosition();
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                if(lastVisiblePosition == adapterList.size() - 1) {
                    mOffset += mNumber;
                    //findCheckInfo(0);
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: // 触摸状态
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 惯性滑行状态
                break;

            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
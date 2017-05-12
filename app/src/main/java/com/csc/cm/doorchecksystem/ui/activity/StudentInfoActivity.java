package com.csc.cm.doorchecksystem.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.data.api.BaseApi;
import com.csc.cm.doorchecksystem.data.model.StudentResult;
import com.csc.cm.doorchecksystem.data.tool.TimeTranUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/1/16.
 */

public class StudentInfoActivity extends Activity implements View.OnClickListener{

    private ImageButton ivStudentClose;
    private Context mContext;

    private TextView tvName;
    private TextView tvCardId;
    private TextView tvAttribution;
    private TextView tvOutTime;
    private TextView tvInTime;
    private TextView tvState;
    private TextView tvCounsellor;
    private TextView tvPhone;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_student_info);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        initView();
        initData();
    }

    private void initView() {
        tvName = (TextView) findViewById(R.id.tv_name_value);
        tvCardId = (TextView) findViewById(R.id.tv_cardid_value);
        tvAttribution = (TextView) findViewById(R.id.tv_attribution_value);
        tvOutTime = (TextView) findViewById(R.id.tv_out_time_value);
        tvInTime = (TextView) findViewById(R.id.tv_in_time_value);
        tvState = (TextView) findViewById(R.id.tv_state_value);
        tvCounsellor = (TextView) findViewById(R.id.tv_counsellor_value);
        tvPhone = (TextView) findViewById(R.id.tv_phone_value);

        ivStudentClose = (ImageButton) findViewById(R.id.iv_student_close);
        ivStudentClose.setOnClickListener(this);
    }

    private void initData() {
        int empId = getIntent().getIntExtra("empId",0);

        progressDialog.show();
        Call<StudentResult> call = BaseApi.getInstance().postStudent(empId);

        call.enqueue(new Callback<StudentResult>() {
            @Override
            public void onResponse(Call<StudentResult> call, Response<StudentResult> response) {
                StudentResult studentResult = response.body();

                if (studentResult.model == null) {
                    Toast.makeText(mContext,"无数据！！！",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                tvName.setText(studentResult.model.employeeName);
                tvCardId.setText(studentResult.model.cardNo);
                tvAttribution.setText(studentResult.model.jobName + studentResult.model.deptName);
                if (studentResult.model.outDoorTime == 0) {
                    tvOutTime.setText("0");
                } else {
                    tvOutTime.setText(TimeTranUtil.dateTimeToYmdString(studentResult.model.outDoorTime));
                }
                if (studentResult.model.inDoorTime == 0) {
                    tvInTime.setText("0");
                } else {
                    tvInTime.setText(TimeTranUtil.dateTimeToYmdString(studentResult.model.inDoorTime));
                }
                tvState.setText(studentResult.model.status);
                tvCounsellor.setText("");
                tvPhone.setText(studentResult.model.phone);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<StudentResult> call, Throwable t) {
                Toast.makeText(mContext,"无法连接服务器，请检查网络！",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_student_close:
                finish();
                break;
        }
    }
}

package com.csc.cm.doorchecksystem.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.data.tool.SpUtil;
import com.csc.cm.doorchecksystem.ui.activity.MainActivity;

/**
 * Created by admin on 2017/1/16.
 */

public class PersonnalCenterFragment extends Fragment implements View.OnClickListener{

    private ImageButton ivPersonMenu;
    private View PersonView;

    private TextView tvPersonName;
    private EditText etPersonDepartment;
    private EditText etPersonPhone;
    private TextView tvPersonAcct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PersonView = inflater.inflate(R.layout.layout_person, container, false);
        initView();
        return PersonView;
    }

    @Override
    public void onPause() {
        super.onPause();
        String department = etPersonDepartment.getText().toString();
        String phone = etPersonPhone.getText().toString();
        SpUtil.put(getActivity(), "person_department", department);
        SpUtil.put(getActivity(), "person_phone", phone);
    }

    private void initView() {
        ivPersonMenu = (ImageButton) PersonView.findViewById(R.id.iv_person_menu);
        tvPersonName = (TextView) PersonView.findViewById(R.id.tv_user_name_value);
        tvPersonAcct = (TextView) PersonView.findViewById(R.id.tv_user_account_value);
        etPersonDepartment = (EditText) PersonView.findViewById(R.id.et_person_department);
        etPersonPhone = (EditText) PersonView.findViewById(R.id.et_person_phone);

        String department = (String) SpUtil.get(getActivity(), "person_department", "");
        String phone = (String) SpUtil.get(getActivity(), "person_phone", "");
        etPersonDepartment.setText(department);
        etPersonPhone.setText(phone);
        tvPersonName.setText(((MainActivity)getActivity()).getUserInfo().userName);
        tvPersonAcct.setText(((MainActivity)getActivity()).getUserInfo().userCode);

        ivPersonMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_menu:
                ((MainActivity)getActivity()).OpenLeftMenu();
                break;
        }
    }
}
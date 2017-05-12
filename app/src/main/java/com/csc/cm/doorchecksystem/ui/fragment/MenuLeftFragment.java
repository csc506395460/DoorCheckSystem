package com.csc.cm.doorchecksystem.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.ui.activity.MainActivity;

public class MenuLeftFragment extends Fragment
{

	private View view;

	private TextView textView;
	private TextView textView1;

	private TextView tvMenuName;
	private TextView tvMenuAcct;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_menu, container, false);
		initView();
		return view;
	}

	private void initView() {
		tvMenuName = (TextView) view.findViewById(R.id.tv_left_menu_name);
		tvMenuAcct = (TextView) view.findViewById(R.id.tv_left_menu_acct);

		tvMenuName.setText(((MainActivity)getActivity()).getUserInfo().userName);
		tvMenuAcct.setText(((MainActivity)getActivity()).getUserInfo().userCode);

		textView = (TextView) view.findViewById(R.id.person_center);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).jump(1);
			}
		});

		textView1 = (TextView) view.findViewById(R.id.main);
		textView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).jump(0);
			}
		});
	}
}

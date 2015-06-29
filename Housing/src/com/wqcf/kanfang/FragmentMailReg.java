package com.wqcf.kanfang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentMailReg extends Fragment implements OnClickListener{
	
	private TextView mClkTxtReg;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_reg_mail, null);
		mClkTxtReg = (TextView) view.findViewById(R.id.Txt_frag_mailreg_Register);
		initListener();
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Txt_frag_mailreg_Register:
			Activity act = getActivity();
			act.startActivity(new Intent(act,MainActivity.class));
			act.finish();
			break;
		}
	}
	private void initListener(){
		mClkTxtReg.setOnClickListener(this);
	}
}

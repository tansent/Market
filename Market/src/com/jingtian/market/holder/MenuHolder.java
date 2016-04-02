package com.jingtian.market.holder;

import com.jingtian.market.R;
import com.jingtian.market.domain.UserInfo;
import com.jingtian.market.http.HttpHelper;
import com.jingtian.market.manager.ThreadManager;
import com.jingtian.market.protocol.UserProtocol;
import com.jingtian.market.utils.UIUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuHolder extends BaseHolder<UserInfo> implements OnClickListener{

	@ViewInject(R.id.photo_layout)
	private RelativeLayout photo_layout;
	@ViewInject(R.id.image_photo)
	private ImageView image_photo;
	@ViewInject(R.id.user_name)
	private TextView user_name;
	@ViewInject(R.id.user_email)
	private TextView user_email;
	
	@Override
	protected View initView() {
		View view=UIUtil.inflate(R.layout.menu_holder);
		ViewUtils.inject(this, view); //only after ViewUtils.inject(this, view); can @ViewInject annotation work
		photo_layout.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshView(UserInfo info) {
		user_name.setText(info.getName());
		user_email.setText(info.getEmail());
		String url = info.getUrl();//image/user.png
		bitmapUtils.display(image_photo, HttpHelper.URL+"image?name="+url);
		
	}

	// for clicking on the profile picture
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.photo_layout:
			//  connect server ...login
			ThreadManager.getInstance().createLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					UserProtocol protocol=new UserProtocol();
					final UserInfo load = protocol.load(0);
					UIUtil.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							setInfo(load); // refreshView() function will be invoked 
							
							//storeInfoToLocal();
						}
					});
				}
			});
			break;
		}
		
	}

}

package com.linkus.superlamp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;

import com.linkus.superlamp.R;


/**
 * 自定义进度加载对话框
 */
public class CustomProgressDialog extends Dialog {
	private Context context = null;
		    private static CustomProgressDialog customProgressDialog = null;
	private TextView tvMsg;
	private TextView tvPoint;

	public CustomProgressDialog(Context context, int theme) {
		        super(context, theme);
		    }
		     
		    public static CustomProgressDialog createDialog(final Context context){
		        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		        customProgressDialog.setCanceledOnTouchOutside(false);
		        customProgressDialog.setContentView(R.layout.customprogressdialog);
		        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
				return customProgressDialog;
		    } 
		  
		    public void onWindowFocusChanged(boolean hasFocus){
		        
		    }
		    
		   
		    public CustomProgressDialog setTitile(String strTitle){
		        return customProgressDialog;
		    }


		    public CustomProgressDialog setMessage(String strMessage){
				tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

		        if (tvMsg != null){
		            tvMsg.setText(strMessage);
		        }
		        return customProgressDialog;
	    }


		private StringBuffer sb = new StringBuffer();
		 Handler handler = new Handler(){
			 @Override
			 public void handleMessage(Message msg) {
				 super.handleMessage(msg);
				 int what = msg.what;
				 if (what == 8){
					 if (sb.length() == 3){
						 sb.delete(0,3);
					 }
					 sb.append(".");
					 if (tvPoint != null) {
						 tvPoint.setText(sb.toString());
					 }
					 Message message = handler.obtainMessage(what);
					 sendMessageDelayed(message,500);
				 }

			 }
		 };

		public CustomProgressDialog setPointRecycle(boolean isRecycle){
			if (isRecycle){
				tvPoint = (TextView)customProgressDialog.findViewById(R.id.tv_point);
			}
			Message message = new Message();
			message.what = 8;
			handler.sendMessageDelayed(message,500);
			return customProgressDialog;
		}


}

package com.hol.general.dialog.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.hol.general.dialog.R;

import java.lang.ref.WeakReference;


/**
 * 模仿alertDialog编写。<br>
 * 可以直接使用其builder来创建自定义对话框
 * @author holmes
 *
 */
public class CustomAlertDialog extends Dialog{

    private boolean mForceFullScreenWidth = false;

	public CustomAlertDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public CustomAlertDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public CustomAlertDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

    @Override
    protected void onStart() {
        super.onStart();
        if (mForceFullScreenWidth){
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }

    /**
     * 是否强制撑满屏幕宽
     * @param full
     */
    public void setForceFullScreenWidth(boolean full){
        mForceFullScreenWidth = full;
    }

    public static class Builder{
		private Context mContext;
		private CustomAlertDialog mDialog;
		private CharSequence mTitle;
		private CharSequence mMessage;
		private Button mBtnPositive, mBtnNegative, mBtnNeutral;
		private Message mButtonPositiveMessage, mButtonNegativeMessage, mButtonNeutralMessage;
		private CharSequence mPositiveButtonText;
		private CharSequence mNegativeButtonText;
		private CharSequence mNeutralButtonText;
		private OnClickListener
			mPositiveButtonOnClickListener, mNegativeButtonOnClickListener, mNeutralButtonOnClickListener;
		private Handler mHandler;
		private OnCancelListener mOnCancelListener;
		
		private ListView mListView;
		private ListAdapter mListAdapter;
		private CharSequence[] mItemArray;
		private int mSelectedItemtIndex = 0;
		private OnClickListener mItemClickListener;
		private boolean mIsSingleChoice = false;
		private View mDialogTemplate;
		
		private View mViewContent;
		private boolean mCancelable = true;
		
		private int mLayout = 0;

        private float mDensity;

        private boolean mForceFullScreenWidth = false;
		
		public Builder (Context context){
			mContext = context;
            mDensity = mContext.getResources().getDisplayMetrics().density;
		}

        public Context getContext(){
            return mContext;
        }
		
		View.OnClickListener mButtonHandler = new View.OnClickListener() {
	        public void onClick(View v) {
	            Message m = null;
	            if (v == mBtnPositive && mButtonPositiveMessage != null) {
	                m = Message.obtain(mButtonPositiveMessage);
	            } else if (v == mBtnNegative && mButtonNegativeMessage != null) {
	                m = Message.obtain(mButtonNegativeMessage);
	            } else if (v == mBtnNeutral && mButtonNeutralMessage != null) {
	                m = Message.obtain(mButtonNeutralMessage);
	            }
	            if (m != null) {
	                m.sendToTarget();
	            }

	            // Post a message so we dismiss after the above handlers are executed
	            mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, mDialog)
	                    .sendToTarget();
	        }
	    };
		
	    private static final class ButtonHandler extends Handler {
	        // Button clicks have Message.what as the BUTTON{1,2,3} constant
	        private static final int MSG_DISMISS_DIALOG = 1;
	        
	        private WeakReference<DialogInterface> mDialog;

	        public ButtonHandler(DialogInterface dialog) {
	            mDialog = new WeakReference<DialogInterface>(dialog);
	        }

	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	                
	                case DialogInterface.BUTTON_POSITIVE:
	                case DialogInterface.BUTTON_NEGATIVE:
	                case DialogInterface.BUTTON_NEUTRAL:
	                    ((OnClickListener) msg.obj).onClick(mDialog.get(), msg.what);
	                    break;
	                    
	                case MSG_DISMISS_DIALOG:
	                    ((DialogInterface) msg.obj).dismiss();
	            }
	        }
	    }
		
		public Builder setTitle(CharSequence title){
			mTitle = title;
			return this;
		}
		
		public Builder setTitle(int resId){
			mTitle = mContext.getString(resId);
			return this;
		}
		
		public Builder setMessage(CharSequence msg){
			mMessage = msg;
			return this;
		}
		
		public Builder setMessage(int resId){
			mMessage = mContext.getString(resId);
			return this;
		}
		
		public Builder setPositiveButton(CharSequence text, OnClickListener listener){
			mPositiveButtonText = text;
			mPositiveButtonOnClickListener = listener;
			//setButton(DialogInterface.BUTTON_POSITIVE, text, listener, mButtonPositiveMessage);
			return this;
		}
		
		public Builder setPositiveButton(int resId, OnClickListener listener){
			mPositiveButtonText = mContext.getString(resId);
			mPositiveButtonOnClickListener = listener;
			//setButton(DialogInterface.BUTTON_POSITIVE, mPositiveButtonText, listener, mButtonPositiveMessage);
			return this;
		}
		
		public Builder setNegativeButton(CharSequence text, OnClickListener listener){
			mNegativeButtonText = text;
			mNegativeButtonOnClickListener = listener;
			//setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText, listener, mButtonNegativeMessage);
			return this;
		}
		
		public Builder setNegativeButton(int resId, OnClickListener listener){
			mNegativeButtonText = mContext.getString(resId);
			mNegativeButtonOnClickListener = listener;
			//setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText, listener, mButtonNegativeMessage);
			return this;
		}
		
		public Builder setNeutralButton(CharSequence text, OnClickListener listener){
			mNeutralButtonText = text;
			mNeutralButtonOnClickListener = listener;
			//setButton(DialogInterface.BUTTON_NEGATIVE, mNeutralButtonText, listener, mButtonNeutralMessage);
			return this;
		}
		
		public Builder setNeutralButton(int resId, OnClickListener listener){
			mNeutralButtonText = mContext.getString(resId);
			mNeutralButtonOnClickListener = listener;
			
			return this;
		}
		
	    public void setButton(int whichButton, 
	            OnClickListener listener, Message msg) {

	        if (msg == null && listener != null) {
	            msg = mHandler.obtainMessage(whichButton, listener);
	        }
	        
	        switch (whichButton) {

	            case DialogInterface.BUTTON_POSITIVE:
	            	//mPositiveButtonText = text;
	                mButtonPositiveMessage = msg;
	                break;
	                
	            case DialogInterface.BUTTON_NEGATIVE:
	            	//mNegativeButtonText = text;
	                mButtonNegativeMessage = msg;
	                break;
	                
	            case DialogInterface.BUTTON_NEUTRAL:
	            	//mNeutralButtonText = text;
	                mButtonNeutralMessage = msg;
	                break;
	                
	            default:
	                throw new IllegalArgumentException("Button does not exist");
	        }
	    }

        public Button getButton(int whichButton){
            switch (whichButton) {
                case DialogInterface.BUTTON_POSITIVE:
                    return mBtnPositive;
                case DialogInterface.BUTTON_NEGATIVE:
                    return mBtnNegative;
                case DialogInterface.BUTTON_NEUTRAL:
                    return mBtnNeutral;
                default:
                    return null;
            }
        }
		
		public Builder setView(View v){
			mViewContent = v;
			return this;
		}
		
		public Builder setItems(int itemsId, OnClickListener listener){
			mItemArray = mContext.getResources().getStringArray(itemsId);
			mItemClickListener = listener;
			return this;
		}
		
		public Builder setItems(CharSequence[] items, OnClickListener listener){
			mItemArray = items;
			mItemClickListener = listener;
			return this;
		}
		
        public Builder setAdapter(final ListAdapter adapter, final OnClickListener listener) {
            mListAdapter = adapter;
            mItemClickListener = listener;
            return this;
        }
        
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
            mItemArray = items;
            mItemClickListener = listener;
            mSelectedItemtIndex = checkedItem;
            mIsSingleChoice = true;
            return this;
        } 
        
        public Builder setCancelable(boolean cancelable){
        	mCancelable = cancelable;
        	return this;
        }
        
        public Builder setOnCancelListener(OnCancelListener listener){
        	mOnCancelListener = listener;
        	return this;
        }
        
        /**
         * 设置布局资源
         * @param layout
         */
        public void setLayoutResource(int layout){
        	mLayout = layout;
        }

        /**
         * 设置强制撑满屏幕
         * @return
         */
        public Builder setForceFullScreenWidth(boolean full){
            mForceFullScreenWidth = full;
            return this;
        }
        
    	@SuppressWarnings("unused")
        public CustomAlertDialog create(){
        	LayoutInflater infl = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	View layout = infl.inflate(mLayout == 0 ? R.layout.cad__custom_dialog : mLayout, null);
        	if (layout == null){
        		return null;
        	}
        	mDialogTemplate = layout;
        	
			boolean hasTitle = setupTitle();
        	boolean hasMessage = setupMessage();
        	boolean hasList = setupList();
        	boolean hasButton = setupButton();
        	boolean hasView = setupView();
        	
        	if (hasList){
        		LinearLayout content = (LinearLayout) mDialogTemplate.findViewById(R.id.contentPanel);
        		content.setVisibility(View.VISIBLE);
        		((LinearLayout.LayoutParams) mDialogTemplate.findViewById(R.id.customPanel).getLayoutParams()).weight = 0;
        	}
        	
        	if (hasView){
        		LinearLayout content = (LinearLayout) mDialogTemplate.findViewById(R.id.contentPanel);
        		content.removeAllViews();
        		mDialogTemplate.findViewById(R.id.contentPanel).setVisibility(View.GONE);
        	}else{
        		mDialogTemplate.findViewById(R.id.customPanel).setVisibility(View.GONE);
        	}
        	
        	if (!hasButton){
        		View buttonPanel = mDialogTemplate.findViewById(R.id.buttonPanel);
        		buttonPanel.setVisibility(View.GONE);
        		((ViewGroup) mDialogTemplate).removeView(buttonPanel);
        	}
        	
        	CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext, R.style.CustomDialog);
        	customAlertDialog.setContentView(mDialogTemplate);
            customAlertDialog.setForceFullScreenWidth(mForceFullScreenWidth);
        	mDialog = customAlertDialog;
        	mDialog.setCancelable(mCancelable);
        	mHandler = new ButtonHandler(mDialog);
        	setupButtonListener();
        	if (mOnCancelListener != null){
        		mDialog.setOnCancelListener(mOnCancelListener);
        	}
        	return mDialog;
        }
        
        private void setupButtonListener() {
			// TODO Auto-generated method stub
        	setButton(BUTTON_POSITIVE, mPositiveButtonOnClickListener, mButtonPositiveMessage);
        	setButton(BUTTON_NEGATIVE, mNegativeButtonOnClickListener, mButtonNegativeMessage);
        	setButton(BUTTON_NEUTRAL, mNeutralButtonOnClickListener, mButtonNeutralMessage);
		}

		private boolean setupView() {
			// TODO Auto-generated method stub
        	if (mViewContent == null){
        		return false;
        	}else{
        		FrameLayout custom = (FrameLayout) mDialogTemplate.findViewById(R.id.custom);
        		custom.addView(mViewContent, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
        	}
			return true;
		}

		private boolean setupButton() {
			// TODO Auto-generated method stub
        	int BIT_BUTTON_POSITIVE = 1;
            int BIT_BUTTON_NEGATIVE = 2;
            int BIT_BUTTON_NEUTRAL = 4;
            int whichButtons = 0;
            mBtnPositive = (Button) mDialogTemplate.findViewById(R.id.button1);
            mBtnPositive.setOnClickListener(mButtonHandler);

            if (TextUtils.isEmpty(mPositiveButtonText)) {
            	mBtnPositive.setVisibility(View.GONE);
            } else {
                mBtnPositive.setText(mPositiveButtonText);
                mBtnPositive.setVisibility(View.VISIBLE);
                whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
            }

            mBtnNegative = (Button) mDialogTemplate.findViewById(R.id.button2);
            mBtnNegative.setOnClickListener(mButtonHandler);

            if (TextUtils.isEmpty(mNegativeButtonText)) {
                mBtnNegative.setVisibility(View.GONE);
            } else {
                mBtnNegative.setText(mNegativeButtonText);
                mBtnNegative.setVisibility(View.VISIBLE);

                whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
            }

            mBtnNeutral = (Button) mDialogTemplate.findViewById(R.id.button3);
            mBtnNeutral.setOnClickListener(mButtonHandler);

            if (TextUtils.isEmpty(mNeutralButtonText)) {
                mBtnNeutral.setVisibility(View.GONE);
            } else {
                mBtnNeutral.setText(mNeutralButtonText);
                mBtnNeutral.setVisibility(View.VISIBLE);

                whichButtons = whichButtons | BIT_BUTTON_NEUTRAL;
            }

            /*
             * 如果只有一个按钮则将其充满50%的空间，并居中
             */
            if (whichButtons == BIT_BUTTON_POSITIVE) {
                centerButton(mBtnPositive);
            } else if (whichButtons == BIT_BUTTON_NEGATIVE) {
                centerButton(mBtnNeutral);
            } else if (whichButtons == BIT_BUTTON_NEUTRAL) {
                centerButton(mBtnNeutral);
            }else {
                setButtonDividers(whichButtons);
            }
            
            return whichButtons != 0;
		}
        
        private void centerButton(Button button) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.weight = 0.5f;
            button.setLayoutParams(params);
            View leftSpacer = mDialogTemplate.findViewById(R.id.leftSpacer);
            leftSpacer.setVisibility(View.VISIBLE);
            View rightSpacer = mDialogTemplate.findViewById(R.id.rightSpacer);
            rightSpacer.setVisibility(View.VISIBLE);
        }

        /**
         * set dividers between buttons
         * @param whichButtons
         */
        private void setButtonDividers(int whichButtons){
            View divider1 = mDialogTemplate.findViewById(R.id.button_divider1);
            View divider2 = mDialogTemplate.findViewById(R.id.button_divider2);
            if (whichButtons == 0){
                divider1.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
            }else if (whichButtons == 3){
                divider2.setVisibility(View.GONE);
            }else if (whichButtons == 5){
                divider1.setVisibility(View.GONE);
            }else if (whichButtons == 6){
                divider2.setVisibility(View.GONE);
            }

        }

		private boolean setupList() {
			// TODO Auto-generated method stub
			int layout = mIsSingleChoice ? R.layout.cad__select_dialog_singlechoice
					: R.layout.cad__select_dialog_item;
        	ListAdapter adapter;
        	if (mListAdapter == null){
        		if (mItemArray == null){
        			return false;
        		}
        		adapter = new ArrayAdapter<CharSequence>(mContext, layout, android.R.id.text1, mItemArray){
        			
        			@Override
        			public View getView(int position, View convertView,
        					ViewGroup parent) {
        				// TODO Auto-generated method stub
        				View v = super.getView(position, convertView, parent);
//        				if (v instanceof TextView){
//        					TextView txt = (TextView) v;
//        					//txt.setMinimumHeight((int) (18 * mDensity));
//        					//txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
//        				}
        				return v;
        			}
        		};
        	}else{
        		adapter = mListAdapter;
        	}
        	createListView(adapter);
			return true;
		}
        

		private void createListView(ListAdapter adapter) {
			// TODO Auto-generated method stub
			LinearLayout content = (LinearLayout) mDialogTemplate.findViewById(R.id.contentPanel);
			content.removeView(mDialogTemplate.findViewById(R.id.scrollView));
            LayoutInflater inflater = LayoutInflater.from(mContext);
			ListView lst = (ListView) inflater.inflate(R.layout.cad__custom_dialog_listview, null);
			mListView = lst;
			int choiceMode = mIsSingleChoice ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE;
			mListView.setChoiceMode(choiceMode);
			mListView.setCacheColorHint(Color.TRANSPARENT);
			content.addView(mListView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1.0f));
			mListView.setAdapter(adapter);
			mListView.setItemChecked(mSelectedItemtIndex, true);
			if (mItemClickListener != null){
				mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						mItemClickListener.onClick(mDialog, arg2);
						if (!mIsSingleChoice){
							mDialog.dismiss();
						}
					}
				});
					
			}
		}

		private boolean setupMessage() {
			// TODO Auto-generated method stub
        	if (!TextUtils.isEmpty(mMessage)){
        		TextView txtMessage = (TextView) mDialogTemplate.findViewById(R.id.message);
        		txtMessage.setText(mMessage);
        		return true;
        	}else{
        		View messagePanel = mDialogTemplate.findViewById(R.id.contentPanel);
        		messagePanel.setVisibility(View.GONE);
        	}
			return false;
		}

		private boolean setupTitle() {
			// TODO Auto-generated method stub
        	if (!TextUtils.isEmpty(mTitle)){
        		TextView txtTitle = (TextView) mDialogTemplate.findViewById(R.id.title_message);
        		txtTitle.setText(mTitle);
        		return true;
        	}else{
        		View titlePanel = mDialogTemplate.findViewById(R.id.title_panel);
        		titlePanel.setVisibility(View.GONE);
        	}
			return false;
		}

		public CustomAlertDialog show(){
			CustomAlertDialog dialog = create();
			dialog.show();
        	return dialog;
        }
		
	}
	
}

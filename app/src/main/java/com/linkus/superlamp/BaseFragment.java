package com.linkus.superlamp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.linkus.superlamp.utils.ToastUtils;
import com.linkus.superlamp.widget.CustomProgressDialog;

import butterknife.ButterKnife;

/**
 * <p> ProjectName： WST</p>
 * <p>
 * Description：
 * </p>
 *
 * @author cjz
 * @version 1.0
 * @createdate 2016/5/11/011
 */
public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    protected Context mContext;
    private CustomProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        beforeShow();
        View rootView = inflateView(inflater,container,savedInstanceState);
        ButterKnife.bind(this, rootView);//黄牛到绑定
        findAllViews(rootView);
        setAllListeners();
        doProgress();
        return rootView;
    }

    /**
     * 在展示布局之前   geIntent啊  等待
     */
    protected abstract void beforeShow();

    /**
     * 加载布局
     * @param inflater   The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState   If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return  碎片的布局
     */
    public abstract View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) ;
    /**
     * 查找布局
     * @param rootView  碎片的父布局
     */
    public abstract void findAllViews(View rootView) ;
    /**
     * 设置监听
     */
    public abstract void setAllListeners();
    /**
     * 处理逻辑
     */
    public abstract void doProgress() ;

    public void showToast(String msg){
        if (TextUtils.isEmpty(msg)) {
            return;
        }
       ToastUtils.showToast(mContext,msg);
    }
    public void showToast(int resId){
        String msg = mContext.getResources().getString(resId);
        ToastUtils.showToast(mContext,msg);
    }
    public void startProgressDialog() {
        if (dialog == null) {
            dialog = CustomProgressDialog.createDialog(mContext);
        }
        dialog.setCanceledOnTouchOutside(false);
        if(!getActivity().isFinishing()) {
            dialog.setPointRecycle(true);
            dialog.show();
        }
    }


    public void stopProgressDialog() {
        if (dialog != null ) {
            dialog.dismiss();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ToastUtils.cancelToast();
    }
}

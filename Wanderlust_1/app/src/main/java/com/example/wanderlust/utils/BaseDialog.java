package com.example.wanderlust.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.example.wanderlust.R;
import com.example.wanderlust.items.WheelViewDataBean;
import com.example.wanderlust.widget.IWheelViewSelectedListener;
import com.example.wanderlust.widget.MyOnClickListener;

import java.util.List;

public class BaseDialog {

    private final static BaseDialog INSTANCE = new BaseDialog();
    private BaseDialog(){

    }

    public static BaseDialog getInstance(){
        return INSTANCE;
    }

    //底部弹出的对话框
    public static Dialog showDialog(Activity activity, int layoutId) {
        View view = LayoutInflater.from(activity).inflate(layoutId, null);
        Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
       // window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
       // wl.x = 0;
       // wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();

        return dialog;
    }

    public Dialog showDialog(Activity activity, int layoutId, String flag) {
        View view = LayoutInflater.from(activity).inflate(layoutId, null);
        Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.x = 0;
//        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /***
     * 依附view弹出的window
     * @param view  依赖的viw
     * @param areaView  布局
     * @return
     */
    public static PopupWindow showPopuWindow(View view, View areaView) {
        PopupWindow popuWindow = new PopupWindow(areaView);
        popuWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popuWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popuWindow.setContentView(areaView);
        popuWindow.setOutsideTouchable(true);
        popuWindow.setFocusable(true);
        popuWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int xPos = view.getWidth() / 2 - popuWindow.getContentView().getMeasuredWidth() / 2;
        popuWindow.showAsDropDown(view, xPos, 3);
        return popuWindow;
    }

    /***
     * 销毁对话框
     * @param dialog
     */
    public static void disDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /***
     * @param popupWindow
     * 销毁对话框
     */
    public static void disPopuwindow(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /***
     * 滚轮选择器
     * @param activity
     * @return
     */
    public Dialog showWheelViewDialog(Activity activity, final List<WheelViewDataBean> mOptionsItems) {
        Dialog dialog = BaseDialog.showDialog(activity, R.layout.choose_wheelview_dialog);
        final WheelView wheelView = dialog.findViewById(R.id.wheelview);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvConfrim = dialog.findViewById(R.id.tv_confirm);
        wheelView.setLineSpacingMultiplier(2);
        wheelView.setCyclic(false);
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return mOptionsItems.size();
            }

            @Override
            public Object getItem(int index) {
                return mOptionsItems.get(index).getName();
            }

            @Override
            public int indexOf(Object o) {
                return mOptionsItems.indexOf(o);
            }
        });

        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                iWheelViewSelectedListener.onItemSelected(index,mOptionsItems);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(v);
            }
        });
        tvConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm(v, ""+wheelView.getCurrentItem());
            }
        });
        return dialog;
    }

    public Dialog showWheelViewDialogTime(Activity activity, final List<WheelViewDataBean> OptionsItemsHH, final List<WheelViewDataBean> OptionsItemsMM) {
        Dialog dialog = BaseDialog.showDialog(activity, R.layout.choose_wheelview_dialog_time);

        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvConfrim = dialog.findViewById(R.id.tv_confirm);
        // HH
        final WheelView wheelViewHH = dialog.findViewById(R.id.wheelviewHH);
        wheelViewHH.setLineSpacingMultiplier(2);
        wheelViewHH.setCyclic(false);
        wheelViewHH.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return OptionsItemsHH.size();
            }

            @Override
            public Object getItem(int index) {
                return OptionsItemsHH.get(index).getName();
            }

            @Override
            public int indexOf(Object o) {
                return OptionsItemsHH.indexOf(o);
            }
        });

        wheelViewHH.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                iWheelViewSelectedListener.onItemSelected(index,OptionsItemsHH);
            }
        });

        // MM
        final WheelView wheelViewMM = dialog.findViewById(R.id.wheelviewMM);
        wheelViewMM.setLineSpacingMultiplier(2);
        wheelViewMM.setCyclic(false);
        wheelViewMM.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return OptionsItemsMM.size();
            }

            @Override
            public Object getItem(int index) {
                return OptionsItemsMM.get(index).getName();
            }

            @Override
            public int indexOf(Object o) {
                return OptionsItemsMM.indexOf(o);
            }
        });

        wheelViewMM.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                iWheelViewSelectedListener.onItemSelected(index,OptionsItemsMM);
            }
        });


        // Cancel and confirm
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(v);
            }
        });
        tvConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm(v, wheelViewHH.getCurrentItem()+":"+wheelViewMM.getCurrentItem());
            }
        });
        return dialog;
    }

    private static MyOnClickListener listener;
    public void setOnClickListener(MyOnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    private static IWheelViewSelectedListener iWheelViewSelectedListener;
    public void setOnWheelViewSelectedListener(IWheelViewSelectedListener onWheelViewSelectedListener) {
        this.iWheelViewSelectedListener = onWheelViewSelectedListener;
    }
}

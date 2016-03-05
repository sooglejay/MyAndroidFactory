package com.jiandanbaoxian.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;
import com.jiandanbaoxian.api.callback.NetCallback;
import com.jiandanbaoxian.api.user.UserRetrofitUtil;
import com.jiandanbaoxian.bean.aaa_MyMoneyPocketBean;
import com.jiandanbaoxian.constant.IntConstant;
import com.jiandanbaoxian.constant.PreferenceConstant;
import com.jiandanbaoxian.constant.StringConstant;
import com.jiandanbaoxian.event.BusEvent;
import com.jiandanbaoxian.fragment.DialogFragmentCreater;
import com.jiandanbaoxian.model.NetWorkResultBean;
import com.jiandanbaoxian.ui.BaseActivity;
import com.jiandanbaoxian.ui.me.mymoneypocket.MyMoneyPocketActivity;
import com.jiandanbaoxian.ui.stopinsurance.PullMoneyActivity;
import com.jiandanbaoxian.util.PreferenceUtil;
import com.jiandanbaoxian.util.ProgressDialogUtil;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MyMoneyPacketListAdapter extends BaseAdapter {
    private DialogFragmentCreater dialogFragmentController;//注意清除掉 mPasswordString 才能公用
    private ProgressDialogUtil progressDialogUtil;

    public MyMoneyPacketListAdapter(final BaseActivity mContext, List<aaa_MyMoneyPocketBean> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        mDatas = datas;

        //作为Dialog的生成器
        dialogFragmentController = new DialogFragmentCreater();//涉及到权限操作时，需要临时输入密码并验证
        dialogFragmentController.setDialogContext(mContext, mContext.getSupportFragmentManager());

        progressDialogUtil = new ProgressDialogUtil(mContext);
    }

    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<aaa_MyMoneyPocketBean> mDatas = new ArrayList<aaa_MyMoneyPocketBean>();

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public aaa_MyMoneyPocketBean getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_money_pocket_list, null);
            holder.tv_money_amount = (TextView) convertView.findViewById(R.id.tv_money_amount);
            holder.tv_money_kind = (TextView) convertView.findViewById(R.id.tv_money_kind);
            holder.tv_pull_money = (TextView) convertView.findViewById(R.id.tv_pull_money);

            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            holder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    aaa_MyMoneyPocketBean obj = (aaa_MyMoneyPocketBean) v.getTag();
                    final int type = obj.getType();


                    //弹出输入密码对话框，输入密码正确才能提现
                    dialogFragmentController.setOnPasswordDialogClickListener(new DialogFragmentCreater.OnPasswordDialogClickListener() {
                        @Override
                        public void getPassword(final String psw) {
                            progressDialogUtil.show("正在验证密码...");
                            String phoneStr = PreferenceUtil.load(mContext, PreferenceConstant.phone, "");
                            UserRetrofitUtil.verifyPwd(mContext, phoneStr, psw, new NetCallback<NetWorkResultBean<Object>>(mContext) {
                                @Override
                                public void onFailure(RetrofitError error, String message) {
                                    progressDialogUtil.hide();
                                    Toast.makeText(mContext, "请检查网络设置", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void success(NetWorkResultBean<Object> stringNetWorkResultBean, Response response) {
                                    progressDialogUtil.hide();
                                    if (stringNetWorkResultBean != null) {
                                        int httpStatus = stringNetWorkResultBean.getStatus();
                                        switch (httpStatus) {
                                            case HttpURLConnection.HTTP_OK:
                                                if (stringNetWorkResultBean.getMessage().equals(StringConstant.failure)) {
                                                    Toast.makeText(mContext, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(mContext, "验证成功！", Toast.LENGTH_SHORT).show();
                                                    dialogFragmentController.dismiss();
                                                    Intent intent = new Intent(mContext, PullMoneyActivity.class);
                                                    intent.putExtra("password", psw);
                                                    intent.putExtra("type", type);
                                                    mContext.startActivityForResult(intent, MyMoneyPocketActivity.ACTION_TO_PULL_MONEY);
                                                }
                                                break;
                                            default:
                                                Toast.makeText(mContext, stringNetWorkResultBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });
                        }

                        @Override
                        public void onDialogDismiss(EditText view) {
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                    });
                    dialogFragmentController.showDialog(mContext, DialogFragmentCreater.DialogShowInputPasswordDialog);


                }
            };
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final aaa_MyMoneyPocketBean bean = getItem(position);
        holder.tv_money_kind.setText(bean.getMoneyKind());
        holder.tv_money_amount.setText(StringConstant.RMB + bean.getMoneyAmount() + "");
        holder.item.setTag(bean);
        holder.item.setOnClickListener(holder.onClickListener);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_money_amount;
        TextView tv_money_kind;
        TextView tv_pull_money;
        LinearLayout item;
        View.OnClickListener onClickListener;
    }
}

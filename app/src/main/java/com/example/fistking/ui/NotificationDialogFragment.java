package com.example.fistking.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fistking.R;
import com.example.fistking.bean.Notification;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Wachsbeere on 2018/9/20.
 */

public class NotificationDialogFragment extends DialogFragment {

    private TextView tv_testInBoard;
    private ImageView iv_ok;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_notification, null);

        tv_testInBoard = view.findViewById(R.id.tv_testInBoard);
        iv_ok = view.findViewById(R.id.iv_ok);

        BmobQuery<Notification> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", "zajbAAAP");
        query.findObjects(new FindListener<Notification>() {
            @Override
            public void done(List<Notification> list, BmobException e) {
                if (list != null) {
                    tv_testInBoard.setText(list.get(0).getMessage());
                }else {
                    tv_testInBoard.setText("加载中...");
                }
            }
        });


        iv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

//    public void setTitle(String message) {
//        this.message = message;
//    }
}

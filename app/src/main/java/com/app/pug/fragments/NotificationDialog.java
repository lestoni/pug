package com.app.pug.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pug.R;
import com.app.pug.utils.BitmapFunctions;
import com.app.pug.utils.Utils;

public class NotificationDialog extends DialogFragment {

    private ImageView userImage;
    private TextView nameLabel;
    private TextView gameLabel;
    private TextView message_label;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.screen_notification, container, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        userImage = (ImageView) root.findViewById(R.id.user_image);

        // load the profile image in a separate activity
        Utils.runTask(getActivity(), new Utils.ResultTask<Bitmap>() {
            @Override
            public Bitmap run() throws Exception {
                int size = BitmapFunctions.convertDpToPixel(getResources().getDimension(R.dimen.dimen_120), getActivity());
                return BitmapFunctions.getRoundedShape(R.drawable.ic_list_item_4, getActivity(), size, size);
            }

            @Override
            public void onFinish(Bitmap result) {
                if (result != null) {
                    userImage.setImageBitmap(result);
                }
            }
        }, "LoadImageDrawerTask");

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) return;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.notification_background);
    }
}

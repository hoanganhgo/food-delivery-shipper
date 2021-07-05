package com.hcmus.fit.shipper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.shipper.R;
import com.hcmus.fit.shipper.activities.NotificationActivity;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.network.ProfileNetwork;
import com.hcmus.fit.shipper.network.SignInNetwork;
import com.hcmus.fit.shipper.util.AppUtil;
import com.hcmus.fit.shipper.util.StorageUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView ivUserAvatar;
    private TextView tvUserName;
    private TextView tvUserId;
    private TextView tvMoney;
    private Button btnSubmitMoney;
    private TextView tvProcessing;
    private Button btnSetOrder;
    private TextView tvOrders;
    private Button btnSetDistance;
    private TextView tvDistance;
    private Button btnAmount;
    private TextView tvAmount;
    private Button btnSupport;
    private Button btnNotification;
    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ivUserAvatar = root.findViewById(R.id.iv_user_avatar);
        tvUserName = root.findViewById(R.id.tv_user_name);
        tvUserId = root.findViewById(R.id.tv_user_id);
        tvMoney = root.findViewById(R.id.tv_money);
        btnSubmitMoney = root.findViewById(R.id.btn_submit_money);
        tvProcessing = root.findViewById(R.id.tv_processing);
        btnSetOrder = root.findViewById(R.id.btn_set_order);
        tvOrders = root.findViewById(R.id.tv_orders);
        btnSetDistance = root.findViewById(R.id.btn_set_distance);
        tvDistance = root.findViewById(R.id.tv_distance);
        btnAmount = root.findViewById(R.id.btn_set_money);
        tvAmount = root.findViewById(R.id.tv_amount);
        btnSupport = root.findViewById(R.id.btn_support);
        btnLogout = root.findViewById(R.id.btn_logout);
        btnNotification = root.findViewById(R.id.btn_notification);

        ShipperInfo shipper = ShipperInfo.getInstance();

        Picasso.with(getContext()).load(shipper.getAvatar()).into(ivUserAvatar);
        tvUserName.setText(shipper.getFullName());
        tvUserId.setText("ID: " + shipper.getId());
        tvMoney.setText(AppUtil.convertCurrency(shipper.getWallet()));
        tvOrders.setText(shipper.getMaxOrder() + " " + getResources().getString(R.string.order));
        tvDistance.setText(shipper.getMaxDistance() + " km");
        tvAmount.setText(AppUtil.convertCurrency(shipper.getMaxAmount()));

        btnNotification.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NotificationActivity.class);
            startActivity(intent);
        });

        btnSetOrder.setOnClickListener(v -> {
            showPopup(R.string.max_order);
        });

        btnSetDistance.setOnClickListener(v -> {
            showPopup(R.string.max_distance);
        });

        btnAmount.setOnClickListener(v -> {
            showPopup(R.string.max_money);
        });

        btnSubmitMoney.setOnClickListener(v -> {
            if (ShipperInfo.getInstance().processWithDraw) {
                return;
            }

            showPopup(R.string.with_draws);
        });

        btnLogout.setOnClickListener(v -> {
            Log.d("logout", "Sign out shipper success");
            ShipperInfo.getInstance().clear();
            StorageUtil.deleteKey(getContext(), StorageUtil.TOKEN_KEY);
            getActivity().finish();
            Toast.makeText(getActivity(), R.string.notify_sign_out,Toast.LENGTH_LONG).show();
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ProfileNetwork.getWithDraw(this);
        SignInNetwork.getUserWallet(getContext());
        tvMoney.setText(AppUtil.convertCurrency(ShipperInfo.getInstance().getWallet()));
    }

    private void showPopup(int title) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getResources().getString(title));
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getResources().getString(R.string.confirm),
                (dialog, which) -> {
                    if (input.getText().toString().isEmpty()) {
                        return;
                    }

                    switch (title) {
                        case R.string.max_order:
                            int maxOrder = Integer.parseInt(input.getText().toString());
                            ProfileNetwork.updateSetting(getContext(), maxOrder, -1, -1);
                            ShipperInfo.getInstance().setMaxOrder(maxOrder);
                            tvOrders.setText(maxOrder + " " + getResources().getString(R.string.order));
                            Toast.makeText(getContext(), getResources().getString(R.string.modify_max_order_success), Toast.LENGTH_SHORT).show();
                            break;

                        case R.string.max_distance:
                            int maxDistance = Integer.parseInt(input.getText().toString());
                            ProfileNetwork.updateSetting(getContext(), -1, maxDistance, -1);
                            ShipperInfo.getInstance().setMaxDistance(maxDistance);
                            tvDistance.setText(maxDistance + " km");
                            Toast.makeText(getContext(), getResources().getString(R.string.modify_max_distance_success), Toast.LENGTH_SHORT).show();
                            break;

                        case R.string.max_money:
                            int maxMoney = Integer.parseInt(input.getText().toString());
                            ProfileNetwork.updateSetting(getContext(), -1, -1, maxMoney);
                            ShipperInfo.getInstance().setMaxAmount(maxMoney);
                            tvAmount.setText(AppUtil.convertCurrency(maxMoney));
                            Toast.makeText(getContext(), getResources().getString(R.string.modify_max_amount_success), Toast.LENGTH_SHORT).show();
                            break;

                        case R.string.with_draws:
                            int money = Integer.parseInt(input.getText().toString());
                            if (ShipperInfo.getInstance().getWallet() < money) {
                                Toast.makeText(getContext(), getResources().getString(R.string.notify_not_enough_money), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ProfileNetwork.postWithDraw(getContext(), money);
                            ShipperInfo.getInstance().processWithDraw = true;
                            updateProcessingWithDraw();
                            break;

                    }

                    dialog.cancel();
                });

        alertDialog.setNegativeButton(getResources().getString(R.string.cancel),
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    public void updateProcessingWithDraw() {
        if (ShipperInfo.getInstance().processWithDraw) {
            tvProcessing.setText(getResources().getString(R.string.processing));
        } else {
            tvProcessing.setText("");
        }
    }
}
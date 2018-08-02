package com.wl.appchat.model;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.tencent.TIMFriendStatus;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.wl.appchat.R;


public class AddFriendFragmentActivity extends FragmentActivity implements FriendshipManageView {

    public void addFriend(String account) {
        FriendshipManagerPresenter presenter = new FriendshipManagerPresenter(this);
        presenter.addFriend(account, "", "", "");
    }

    @Override
    public void onAddFriend(TIMFriendStatus status) {
        switch (status) {
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Log.e("TencentHelper", getString(R.string.add_friend_request_has_send));
                break;
            case TIM_FRIEND_STATUS_SUCC:
                FriendshipInfo.getInstance().refresh();
                Log.e("TencentHelper", getString(R.string.add_friend_success));
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Log.e("TencentHelper", getString(R.string.add_friend_refuse_all));
                break;
        }
    }

    @Override
    public void onDelFriend(TIMFriendStatus status) {

    }

    @Override
    public void onChangeGroup(TIMFriendStatus status, String groupName) {

    }
}

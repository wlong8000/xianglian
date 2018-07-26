package com.xianglian.love.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.tencent.TIMConversationType;
import com.wl.appchat.ChatActivity;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.BaseListActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.adapter.PersonDetailAdapter;
import com.xianglian.love.main.home.been.MessageEntity;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/9/3.
 * 详情页
 */
@Route(path = "/detail/activity")
public class PersonDetailActivity2 extends BaseListActivity {

    private PersonDetailAdapter mAdapter;

    private List<UserDetailEntity> mUserDetailEntities = new ArrayList<>();

    private int mId;

    private String mUserName;

    private TextView mChatIcon;

    private UserEntity mOwerEntity;

    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, PersonDetailActivity2.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detail);
        mId = getIntent().getIntExtra("id", 0);
        setupCommonTitle("详情");
        setupRecyclerView();
    }


    public void setupRecyclerView() {
        super.setupRecyclerView();
        mAdapter = new PersonDetailAdapter(this, mUserDetailEntities);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        mChatIcon = findViewById(R.id.icon_start_chat);
        mOwerEntity = Hawk.get(Keys.USER_INFO);
        mChatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mUserName)) return;
                ChatActivity.navToChat(PersonDetailActivity2.this, mUserName, TIMConversationType.C2C);
            }
        });
        onRefresh2(true);
    }

    private void addData(UserDetailEntity entity) {
        addDataByType(UserDetailEntity.ViewType.TOP_INFO, entity);
        if (!TextUtils.isEmpty(entity.getPerson_intro())) {
            addDataByType(UserDetailEntity.ViewType.INTRODUCE, entity);
        }
        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            addDataByType(UserDetailEntity.ViewType.ALBUM, entity);
        }
        addDataByType(UserDetailEntity.ViewType.BASE_INFO, entity);
        if (entity.getTags() != null && entity.getTags().size() > 0) {
            addDataByType(UserDetailEntity.ViewType.MARK, entity);
        }
        if (!TextUtils.isEmpty(entity.getRelationship_desc())) {
            addDataByType(UserDetailEntity.ViewType.EXPERIENCE_EMOTION, entity);
        }
//        if (entity.getInterests() != null && entity.getInterests().size() > 0) {
//            addDataByType(UserDetailEntity.ViewType.FAVORITE, entity);
//        }
//        if (entity.getMessages() != null && entity.getMessages().size() > 0) {
//            addDataByType(UserDetailEntity.ViewType.TITLE, entity);
//            addDataByType(UserDetailEntity.ViewType.LEAVE_MESSAGE, entity);
//        }

        mAdapter.notifyDataSetChanged();
    }

    private void addDataByType(int type, UserDetailEntity entity) {
        switch (type) {
            case UserDetailEntity.ViewType.LEAVE_MESSAGE:
                if (entity != null && entity.getMessages() != null && entity.getMessages().size() > 0) {
                    List<MessageEntity> msgList = entity.getMessages();
                    int count = msgList.size();
                    int i = 0;
                    for (MessageEntity msg : msgList) {
                        if (msg == null) continue;
                        UserDetailEntity info = new UserDetailEntity();
                        info.setViewType(type);
                        info.setSender_avatar(msg.getSender_avatar());
                        info.setSender_name(msg.getSender_name());
                        info.setSender_id(msg.getSender_id());
                        info.setContent(msg.getContent());
                        info.setCreate_time(msg.getCreate_time());
                        if (count >= 3 && i == count -1) {
                            info.setShow_msg_more(true);
                        } else {
                            info.setShow_msg_more(false);
                        }
                        mUserDetailEntities.add(info);
                        i++;
                    }
                }
                break;
            default:
                UserDetailEntity info = new UserDetailEntity();
                info.setViewType(type);
                info.setResult(entity);
                mUserDetailEntities.add(info);
                break;
        }
    }

    public void onRefresh2(final boolean refresh) {
        final String url = Config.PATH + "users/" + mId;
        final GetRequest<UserDetailEntity> request = OkGo.get(url);
        request.headers("Authorization", AppUtils.getToken(this));

        request.execute(new JsonCallBack<UserDetailEntity>(UserDetailEntity.class) {
            @Override
            public void onSuccess(Response<UserDetailEntity> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
                if (response != null && response.body() != null) {
                    UserDetailEntity userEntity = response.body();
                    if (userEntity == null) return;
                    setupCommonTitle(userEntity.getUsername());
                    mUserName = AppUtils.getTimName(userEntity.getUsername(), userEntity.getId());
                    if (mOwerEntity != null && !TextUtils.isEmpty(mOwerEntity.getUsername())
                            && mOwerEntity.getUsername().equals(userEntity.getUsername())) {
                        mChatIcon.setVisibility(View.GONE);
                    } else {
                        mChatIcon.setVisibility(View.VISIBLE);
                    }
                    addData(userEntity);
                }
            }

            @Override
            public void onError(Response<UserDetailEntity> response) {
                super.onError(response);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setEmptyView(errorView);
            }
        });
    }
}

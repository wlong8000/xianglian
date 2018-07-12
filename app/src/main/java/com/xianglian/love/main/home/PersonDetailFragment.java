package com.xianglian.love.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.adapter.PersonDetailAdapter;
import com.xianglian.love.main.home.been.MessageEntity;
import com.xianglian.love.main.home.been.UserDetailEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/9/3.
 */

public class PersonDetailFragment extends BaseListFragment {

    private PersonDetailAdapter mAdapter;

    private List<UserDetailEntity> mUserDetailEntities = new ArrayList<>();

    private int mId;

    public static PersonDetailFragment newInstance(int id) {
        PersonDetailFragment fragment = new PersonDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        super.setupRecyclerView(view);
        mAdapter = new PersonDetailAdapter(getContext(), mUserDetailEntities);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        doRequest(true);
    }

    private void addData(UserDetailEntity entity) {
        addDataByType(UserDetailEntity.ViewType.TOP_INFO, entity);
        if (!TextUtils.isEmpty(entity.getPerson_intro())) {
            addDataByType(UserDetailEntity.ViewType.INTRODUCE, entity);
        }
        if (entity.getAlbums() != null && entity.getAlbums().size() > 0) {
            addDataByType(UserDetailEntity.ViewType.ALBUM, entity);
        }
        addDataByType(UserDetailEntity.ViewType.BASE_INFO, entity);
        if (entity.getTags() != null && entity.getTags().size() > 0) {
            addDataByType(UserDetailEntity.ViewType.MARK, entity);
        }
        if (!TextUtils.isEmpty(entity.getRelationship_desc())) {
            addDataByType(UserDetailEntity.ViewType.EXPERIENCE_EMOTION, entity);
        }
        if (entity.getInterests() != null && entity.getInterests().size() > 0) {
            addDataByType(UserDetailEntity.ViewType.FAVORITE, entity);
        }
        if (entity.getMessages() != null && entity.getMessages().size() > 0) {
            addDataByType(UserDetailEntity.ViewType.TITLE, entity);
            addDataByType(UserDetailEntity.ViewType.LEAVE_MESSAGE, entity);
        }

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

    private void doRequest(final boolean refresh) {
        final String url = Config.PATH + "users/" + mId;
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);

                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
                                if (userEntity == null) return;
                                if (refresh) mUserDetailEntities.clear();
                                addData(userEntity);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}

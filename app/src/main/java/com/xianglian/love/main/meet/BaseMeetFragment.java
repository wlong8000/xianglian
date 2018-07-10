package com.xianglian.love.main.meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.main.meet.adapter.MeetAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.CommonLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/3/11.
 * 专刊
 */

public class BaseMeetFragment extends BaseListFragment {

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private MeetAdapter mAdapter;

    private PersonInfo mPersonInfo;

    private ItemInfo mEntity;

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildAdapterPosition(view);
            mEntity = mAdapter.getItem(position);
            if (mEntity == null) return;
            if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
                switch (mEntity.getType()) {
                    case ItemInfo.MeetType.AT_ME: {
                        Intent intent = TaMeetActivity.getIntent(getContext());
                        startActivity(intent);
                        break;
                    }
                    case ItemInfo.MeetType.ME_AT: {
                        Intent intent = MeMeetActivity.getIntent(getContext());
                        startActivity(intent);
                        break;
                    }
                    case ItemInfo.MeetType.LAST_COME: {
                        Intent intent = LastVisitActivity.getIntent(getContext());
                        startActivity(intent);
                        break;
                    }
                    case ItemInfo.MeetType.HEART_RECORD: {
                        Intent intent = HeartRecordActivity.getIntent(getContext());
                        startActivity(intent);
                        break;
                    }
                    case ItemInfo.MeetType.HEART_ADDRESS_BOOK: {
                        Intent intent = HeartContactActivity.getIntent(getContext());
                        startActivity(intent);
                        break;
                    }
                }
            }
        }
    };

    public static BaseMeetFragment newInstance() {
        BaseMeetFragment fragment = new BaseMeetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MeetAdapter(getContext(), itemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        addData();
    }

    private void addData() {
        addDataByType(ItemInfo.ViewType.PICK_SELECT);
    }

    private void addDataByType(int type) {
        switch (type) {
            case ItemInfo.ViewType.PICK_SELECT: {
                mAdapter.getInfo().addAll(getData());
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //有人想约我
        data.add(getInfo(getString(R.string.meet_me), ItemInfo.MeetType.AT_ME, null));

        //我发出去的约见
        data.add(getInfo(getString(R.string.me_meet), ItemInfo.MeetType.ME_AT, null));

        //最近来访
        data.add(getInfo(getString(R.string.last_come), ItemInfo.MeetType.LAST_COME, null));

        //心动记录
        data.add(getInfo(getString(R.string.heart_record), ItemInfo.MeetType.HEART_RECORD, null));

        //心动通讯录
        data.add(getInfo(getString(R.string.heart_address_book), ItemInfo.MeetType.HEART_ADDRESS_BOOK, null));

        return data;
    }

    public ItemInfo getInfo(String text, int type, PersonInfo info) {
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setType(type);
        data.setInfo(info);
        return data;
    }
}


package com.xianglian.love.main.meet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.main.meet.adapter.HeardRecordAdapter;
import com.xianglian.love.main.meet.model.MeetInfo;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我对谁心动、我对TA心动
 */
public class HeardRecordFragment extends BaseListFragment implements OnClickListener{
    private CommonLinearLayoutManager mLayoutManager;

    private MyRecyclerView mRecyclerView;

    private List<PersonInfo> mEntities;

    private HeardRecordAdapter mAdapter;

    private MeetInfo mEntity;


    private OnClickListener mItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEntities == null) return;
            int position = mRecyclerView.getChildAdapterPosition(v);
            mEntity = mAdapter.getItem(position);

        }
    };

    public static HeardRecordFragment newInstance() {
        HeardRecordFragment fragment = new HeardRecordFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_meet_fragment, null);
        mEntities = new ArrayList<>();
        mAdapter = new HeardRecordAdapter(getActivity(), mItemClickListener);
        this.setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        addData();
    }

    private void addData() {
        addDataByType(MeetInfo.ViewType.COMMON_INFO);

    }

    private void addDataByType(int type) {
        switch (type) {
            case MeetInfo.ViewType.COMMON_INFO: {
//                mAdapter.getInfo().addAll(getData());
                MeetInfo info = new MeetInfo();
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEntities != null) {
            mEntities.clear();
        }
    }

    @Override
    public void onClick(View v) {
    }

}

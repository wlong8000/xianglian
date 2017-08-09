package com.wl.lianba.main.meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wl.lianba.BaseActivity;
import com.wl.lianba.R;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.main.meet.adapter.MeMeetAdapter;
import com.wl.lianba.main.meet.model.MeetInfo;
import com.wl.lianba.utils.CommonLinearLayoutManager;
import com.wl.lianba.view.MyRecyclerView;

import java.util.List;

/**
 * 最近来访
 */
public class LastVisitActivity extends BaseActivity {
    private CommonLinearLayoutManager mLayoutManager;

    private MyRecyclerView mRecyclerView;

    private List<PersonInfo> mEntities;

    private MeMeetAdapter mAdapter;

    private MeetInfo mEntity;

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEntities == null) return;
            int position = mRecyclerView.getChildAdapterPosition(v);
            mEntity = mAdapter.getItem(position);

        }
    };

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LastVisitActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_visit_activity);
        setupCommonTitle(getString(R.string.last_come));
        setupViews();
    }

    private void setupViews() {
        mRecyclerView = (MyRecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MeMeetAdapter(this, mItemClickListener);
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
    protected void onDestroy() {
        super.onDestroy();
    }


}

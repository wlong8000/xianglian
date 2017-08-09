package com.jiang.android.multirecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiang.android.multirecyclerview.adapter.BaseAdapter;
import com.jiang.android.multirecyclerview.adapter.BaseViewHolder;
import com.jiang.android.multirecyclerview.inter.OnLoadMoreListener;
import com.jiang.android.multirecyclerview.inter.OtherStateBindListener;

import java.util.List;

import static com.jiang.android.multirecyclerview.R.styleable.MultiRecyclerView;

/**
 * Created by jiang on 2017/2/24.
 * you shoule not call
 * setAdapter();
 * setLayoutManager();
 * we'd better not use wrap_content to RecyclerView's scrollable orientation
 */

public class MultiRecyclerView extends RecyclerView {

    private static final String TAG = "MultiRecyclerView";

    public OnLoadMoreListener onLoadMoreListener;
    private OtherStateBindListener otherStateBindListener;
    private boolean loadMoreEnabled = true;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    private View mFootView;
    private WrapAdapter mWrapAdapter;
    private Adapter mOrginalAdapter;
    private LayoutManager mOrginalLayoutManager;

    private boolean isLoadingData;
    private int mState = STATE_COMPLETE;
    private int TYPE_FOOTER=0x0123;
    private int footerViewID;

    public MultiRecyclerView(Context context) {
        this(context, null);
    }

    public MultiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public enum ViewState {
        CONTENT,
        LOADING,
        EMPTY,
        ERROR
    }

    private int mLoadingView;

    private int mErrorView;

    private int mEmptyView;

    private ViewState mViewState = ViewState.LOADING;


    private void init(AttributeSet attrs) {
        footerViewID = R.layout.loadmore;
        TypedArray a = getContext().obtainStyledAttributes(attrs, MultiRecyclerView);
         mLoadingView = a.getResourceId(R.styleable.MultiRecyclerView_loadingView, -1);
         mEmptyView = a.getResourceId(R.styleable.MultiRecyclerView_emptyView, -1);
         mErrorView = a.getResourceId(R.styleable.MultiRecyclerView_errorView, -1);

        a.recycle();
    }

    public void setOtherStateBindListener(OtherStateBindListener otherStateBindListener) {
        this.otherStateBindListener = otherStateBindListener;
    }

    public void setViewState(ViewState state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    private void setView() {
        if (mViewState == ViewState.CONTENT) {
            if(mOrginalLayoutManager == null || mOrginalAdapter == null){
                throw new NullPointerException("before set ViewState.CONTENT, you must called config()");
            }
            setLayoutManager(mOrginalLayoutManager);
            setAdapter(mOrginalAdapter);
            return;
        }
        setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        setAdapter(new BaseAdapter() {
            @Override
            public void onBindView(BaseViewHolder holder, int position) {

                if(otherStateBindListener!= null){
                    otherStateBindListener.onBindView(holder,mViewState);
                }
            }

            @Override
            public int getLayoutID(int position) {
                return getLayoutIdByState();
            }

            @Override
            public boolean clickable() {
                if(otherStateBindListener!= null){
                    return otherStateBindListener.clickable();
                }
                return false;
            }

            @Override
            public void onItemClick(View v, int position) {
                if(otherStateBindListener!= null){
                    otherStateBindListener.onItemClick(v,mViewState);
                }
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    private int getLayoutIdByState() {
        int id = -1;
        switch (mViewState){
            case EMPTY:
                id = mEmptyView;
                break;
            case ERROR:
                id = mErrorView;
                break;
            case LOADING:
                id = mLoadingView;
                break;
        }
        return id;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setFooterView(@LayoutRes int id){
        footerViewID = id;
    }

    public void loadMoreComplete() {
        mWrapAdapter.notifyDataSetChanged();
        isLoadingData = false;
        setState(STATE_COMPLETE);

    }

    public void setLoadMoreEnabled(boolean enabled) {
        loadMoreEnabled = enabled;
        if (!enabled) {
            setState(STATE_COMPLETE);
        }
    }

    public void config(LayoutManager layoutManager,Adapter adapter){
        if(layoutManager == null){
            mOrginalLayoutManager =  new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        }else{
            mOrginalLayoutManager = layoutManager;
        }
        mOrginalAdapter = adapter;
    }
    public void config(Adapter adapter){
        config(null,adapter);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter == null)
            return;
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
    }

    @Override
    public Adapter getAdapter() {
        if(mWrapAdapter != null && mViewState == ViewState.CONTENT)
            return mWrapAdapter.getOriginalAdapter();
        else
            return mOrginalAdapter;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if(layout == null)
            return;
        if(mWrapAdapter != null){
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) layout);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (mWrapAdapter.isFooter(position) )
                                ? gridManager.getSpanCount() : 1;
                    }
                });

            }
        }
    }

    private void setState(int state) {
        mState = state;
        if(mFootView == null)
            return;
        if(mState == STATE_COMPLETE){
            mFootView.setVisibility(View.GONE);
        }else if(mState == STATE_LOADING){
            mFootView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && onLoadMoreListener != null && !isLoadingData && loadMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() > layoutManager.getChildCount()) {
                isLoadingData = true;
                setState(STATE_LOADING);
                onLoadMoreListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }




    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        public WrapAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        public Adapter getOriginalAdapter(){
            return this.adapter;
        }


        public boolean isFooter(int position) {
            if(loadMoreEnabled) {
                return position == getItemCount() - 1;
            }else {
                return false;
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(mViewState != ViewState.CONTENT){
                return adapter.onCreateViewHolder(parent, viewType);
            }
           if (viewType == TYPE_FOOTER) {
               if(footerViewID <=0 )
                   throw  new NullPointerException("footer view id can not be null");
                return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(footerViewID, parent, false));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(mViewState != ViewState.CONTENT){
                adapter.onBindViewHolder(holder, position);
                return;
            }
            int adjPosition = position;
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }
        // some times we need to override this
        @Override
        public void onBindViewHolder(ViewHolder holder, int position,List<Object> payloads) {
            if(mViewState != ViewState.CONTENT){
                adapter.onBindViewHolder(holder, position);
                return;
            }

            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (position < adapterCount) {
                    if(payloads.isEmpty()){
                        adapter.onBindViewHolder(holder, position);
                    }
                    else{
                        adapter.onBindViewHolder(holder, position,payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if(mViewState != ViewState.CONTENT)
                return 1;
            if(loadMoreEnabled) {
                if (adapter != null) {
                    return  adapter.getItemCount() + 1;
                } else {
                    return 1;
                }
            }else {
                if (adapter != null) {
                    return  adapter.getItemCount() ;
                } else {
                    return 0;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(mViewState != ViewState.CONTENT){
                return adapter.getItemViewType(position);
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (position < adapterCount) {
                    int type =  adapter.getItemViewType(position);
                    if(isReservedItemViewType(type)) {
                        throw new IllegalStateException("require itemViewType in adapter should not be "+TYPE_FOOTER );
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null ) {
                if (position < adapter.getItemCount()) {
                    return adapter.getItemId(position);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return ( isFooter(position) )
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && ( isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
                mFootView = itemView;
            }
        }
    }
    private boolean isReservedItemViewType(int itemViewType) {
        if( itemViewType == TYPE_FOOTER ) {
            return true;
        } else {
            return false;
        }
    }



}

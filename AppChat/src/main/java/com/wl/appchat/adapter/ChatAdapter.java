package com.wl.appchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.wl.appchat.R;
import com.wl.appchat.model.Message;

import java.util.List;

/**
 * 聊天界面adapter
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChatAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = view.findViewById(R.id.rightPanel);
            viewHolder.sending = view.findViewById(R.id.sending);
            viewHolder.error = view.findViewById(R.id.sendError);
            viewHolder.sender = view.findViewById(R.id.sender);
            viewHolder.rightDesc = view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = view.findViewById(R.id.systemMessage);
            viewHolder.leftAvatar = view.findViewById(R.id.leftAvatar);
            viewHolder.rightAvatar = view.findViewById(R.id.rightAvatar);
            view.setTag(viewHolder);
        }
        if (position < getCount()) {
            final Message data = getItem(position);
            data.showMessage(viewHolder, getContext());
        }
        return view;
    }


    public class ViewHolder {
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
        public SimpleDraweeView leftAvatar;
        public SimpleDraweeView rightAvatar;
    }
}

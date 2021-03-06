package com.fastaccess.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.fastaccess.data.dao.GroupedNotificationModel;
import com.fastaccess.ui.adapter.viewholder.NotificationsHeaderViewHolder;
import com.fastaccess.ui.adapter.viewholder.NotificationsViewHolder;
import com.fastaccess.ui.widgets.recyclerview.BaseRecyclerAdapter;
import com.fastaccess.ui.widgets.recyclerview.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by Kosh on 11 Nov 2016, 2:07 PM
 */

public class NotificationsAdapter extends BaseRecyclerAdapter<GroupedNotificationModel, BaseViewHolder,
        BaseViewHolder.OnItemClickListener<GroupedNotificationModel>> {

    public NotificationsAdapter(@NonNull ArrayList<GroupedNotificationModel> eventsModels) {
        super(eventsModels);
    }

    @Override protected BaseViewHolder viewHolder(ViewGroup parent, int viewType) {
        if (viewType == GroupedNotificationModel.HEADER) {
            return NotificationsHeaderViewHolder.newInstance(parent, this);
        } else {
            return NotificationsViewHolder.newInstance(parent, this);
        }
    }

    @Override protected void onBindView(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == GroupedNotificationModel.HEADER) {
            ((NotificationsHeaderViewHolder) holder).bind(getItem(position));
        } else {
            ((NotificationsViewHolder) holder).bind(getItem(position));
        }
    }

    @Override public int getItemViewType(int position) {
        return getItem(position).getType();
    }
}

package com.fastaccess.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fastaccess.data.dao.TimelineModel;
import com.fastaccess.data.dao.model.Login;
import com.fastaccess.ui.adapter.callback.OnToggleView;
import com.fastaccess.ui.adapter.callback.ReactionsCallback;
import com.fastaccess.ui.adapter.viewholder.IssueDetailsViewHolder;
import com.fastaccess.ui.adapter.viewholder.IssueTimelineViewHolder;
import com.fastaccess.ui.adapter.viewholder.PullStatusViewHolder;
import com.fastaccess.ui.adapter.viewholder.ReviewsViewHolder;
import com.fastaccess.ui.adapter.viewholder.TimelineCommentsViewHolder;
import com.fastaccess.ui.widgets.recyclerview.BaseRecyclerAdapter;
import com.fastaccess.ui.widgets.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by Kosh on 13 Dec 2016, 1:44 AM
 */

public class IssuePullsTimelineAdapter extends BaseRecyclerAdapter<TimelineModel, BaseViewHolder,
        BaseViewHolder.OnItemClickListener<TimelineModel>> {

    private final OnToggleView onToggleView;
    private final String login;
    private final boolean showEmojies;
    private final ReactionsCallback reactionsCallback;

    public IssuePullsTimelineAdapter(@NonNull List<TimelineModel> data, OnToggleView onToggleView, boolean showEmojies,
                                     ReactionsCallback reactionsCallback) {
        super(data);
        this.onToggleView = onToggleView;
        this.login = Login.getUser().getLogin();
        this.showEmojies = showEmojies;
        this.reactionsCallback = reactionsCallback;
    }

    @Override protected BaseViewHolder viewHolder(ViewGroup parent, int viewType) {
        if (viewType == TimelineModel.HEADER) {
            return IssueDetailsViewHolder.newInstance(parent, this);
        } else if (viewType == TimelineModel.EVENT) {
            return IssueTimelineViewHolder.newInstance(parent, this);
        } else if (viewType == TimelineModel.STATUS) {
            return PullStatusViewHolder.newInstance(parent);
        } else if (viewType == TimelineModel.REVIEW) {
            return ReviewsViewHolder.newInstance(parent, this);
        }
        return TimelineCommentsViewHolder.newInstance(parent, this, login, onToggleView, showEmojies, reactionsCallback);
    }

    @Override protected void onBindView(BaseViewHolder holder, int position) {
        TimelineModel model = getItem(position);
        if (model.getType() == TimelineModel.HEADER) {
            if (model.getIssue() != null) {
                ((IssueDetailsViewHolder) holder).bind(model);
            } else if (model.getPullRequest() != null) {
                ((IssueDetailsViewHolder) holder).bind(model);
            }
        } else if (model.getType() == TimelineModel.EVENT) {
            ((IssueTimelineViewHolder) holder).bind(model);
        } else if (model.getType() == TimelineModel.COMMENT) {
            ((TimelineCommentsViewHolder) holder).bind(model);
        } else if (model.getType() == TimelineModel.REVIEW) {
            ((ReviewsViewHolder) holder).bind(model);
        } else {
            if (model.getStatus() != null) ((PullStatusViewHolder) holder).bind(model.getStatus());
        }
        if (model.getType() != TimelineModel.COMMENT) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
    }

    @Override public int getItemViewType(int position) {
        return getData().get(position).getType();
    }

    @Override public void onViewDetachedFromWindow(BaseViewHolder holder) {
        if (holder instanceof TimelineCommentsViewHolder) {
            ((TimelineCommentsViewHolder) holder).pauseWebView();
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof TimelineCommentsViewHolder) {
            ((TimelineCommentsViewHolder) holder).resumeWebView();
        }
    }

    @Override public void insertItems(@NonNull List<TimelineModel> items) {
        super.insertItems(items);
    }

}


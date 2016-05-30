package com.example.vutruong.groupproject2.utilities;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vutruong.groupproject2.R;

/**
 * Created by Administrator on 30/05/2016.
 */
public class LayoutSwitcher {
    public static interface RetryButtonListener {

        public abstract void onRetry();
    }

    public static final int LOADING_MODE = 0;
    public static final int ERROR_MODE = 1;
    public static final int DATA_MODE = 2;
    public static final int BLANK_MODE = 3;

    private final View mContentLayout;
    private int mDataLayoutId;
    private final int mErrorLayoutId;
    private final Handler mHandler;
    private final int mLoadingLayoutId;
    private int mMode;
    private volatile boolean mPendingLoad;
    private final RetryButtonListener mRetryListener;
    private final View.OnClickListener retryClickListener;

    public LayoutSwitcher(View view, int dataLayoutId, int errorLayoutId,
                          int loadingLayoutId, RetryButtonListener retrybuttonlistener) {
        mHandler = new Handler();
        retryClickListener = new View.OnClickListener() {

            public void onClick(View view1) {
                switchToLoadingMode();
                mRetryListener.onRetry();
            }
        };
        mPendingLoad = false;
        mDataLayoutId = dataLayoutId;
        mErrorLayoutId = errorLayoutId;
        mLoadingLayoutId = loadingLayoutId;
        mContentLayout = view;
        mRetryListener = retrybuttonlistener;
        resetMode();
    }

    public LayoutSwitcher(View view, int dataLayoutId, int errorLayoutId,
                          int loadingLayoutId, RetryButtonListener retrybuttonlistener,
                          int mode) {
        mHandler = new Handler();
        retryClickListener = new View.OnClickListener() {

            public void onClick(View view1) {
                switchToLoadingMode();
                mRetryListener.onRetry();
            }
        };
        mPendingLoad = false;
        mDataLayoutId = dataLayoutId;
        mErrorLayoutId = errorLayoutId;
        mLoadingLayoutId = loadingLayoutId;
        mContentLayout = view;
        mRetryListener = retrybuttonlistener;
        mMode = mode;
    }

    public LayoutSwitcher(View view, int dataLayoutId,
                          RetryButtonListener retrybuttonlistener) {
        this(view, dataLayoutId, R.layout.loading_layout,
                R.layout.error_layout, retrybuttonlistener);
    }

    public boolean isDataMode() {
        return mMode != DATA_MODE ? false : true;
    }

    private void setLoadingVisible(boolean flag) {
        View view = mContentLayout.findViewById(mLoadingLayoutId);
        if (!flag)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
    }

    private void setErrorVisible(boolean flag, String s) {
        View view = mContentLayout.findViewById(mErrorLayoutId);
        if (!flag)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
        if (flag)
            ((TextView) view.findViewById(R.id.state_text)).setText(s);
        Button button = (Button) view.findViewById(R.id.retry_button);
        View.OnClickListener onclicklistener;
        if (!flag)
            onclicklistener = null;
        else
            onclicklistener = retryClickListener;
        button.setOnClickListener(onclicklistener);
    }

    private void setDataVisible(boolean flag) {
        if (mDataLayoutId > 0) {
            ViewGroup viewgroup = (ViewGroup) mContentLayout
                    .findViewById(mDataLayoutId);
            if (viewgroup != null) {
                if (!flag)
                    viewgroup.setVisibility(View.GONE);
                else
                    viewgroup.setVisibility(View.VISIBLE);
            }
        }
    }

    private void resetMode() {
        mMode = BLANK_MODE;
        setLoadingVisible(false);
        setErrorVisible(false, null);
        setDataVisible(false);
    }

    private void performSwitch(int mode, String msg) {
        mPendingLoad = false;
        if (mMode != mode) {
            switch (mMode) {
                case LOADING_MODE:
                    setLoadingVisible(false);
                    break;

                case ERROR_MODE:
                    setErrorVisible(false, null);
                    break;

                case DATA_MODE:
                    setDataVisible(false);
                    break;
            }
            switch (mode) {
                default:
                    throw new IllegalStateException(
                            (new StringBuilder())
                                    .append("Invalid mode ")
                                    .append(mode)
                                    .append("should be LOADING_MODE, ERROR_MODE, DATA_MODE, or BLANK_MODE")
                                    .toString());

                case LOADING_MODE:
                    setLoadingVisible(true);
                    break;

                case ERROR_MODE:
                    setErrorVisible(true, msg);
                    break;

                case DATA_MODE:
                    setDataVisible(true);
                    break;

                case BLANK_MODE:
                    break;
            }
            mMode = mode;
        }
    }

    public void switchToBlankMode() {
        performSwitch(BLANK_MODE, null);
    }

    public void switchToDataMode() {
        performSwitch(DATA_MODE, null);
    }

    public void switchToDataMode(int dataLayoutId) {
        mDataLayoutId = dataLayoutId;
        switchToDataMode();
    }

    public void switchToErrorMode(String msg) {
        performSwitch(ERROR_MODE, msg);
    }

    public void switchToLoadingDelayed(int timeDelay) {
        mPendingLoad = true;
        mHandler.postDelayed(new Runnable() {

            public void run() {
                if (mPendingLoad)
                    switchToLoadingMode();
            }
        }, timeDelay);
    }

    public void switchToLoadingMode() {
        performSwitch(LOADING_MODE, null);
    }
}

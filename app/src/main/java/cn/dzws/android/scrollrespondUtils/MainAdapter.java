package cn.dzws.android.scrollrespondUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Lwang
 * @time 2018/1/2.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {


    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_item, null);
        return new MainHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 30;
    }

    public class MainHolder extends RecyclerView.ViewHolder{

        public MainHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 点击 RecyclerView 某条的监听
     */
    public interface OnItemClickListener {

        /**
         * 当RecyclerView某个被点击的时候回调
         *
         * @param view 点击item的视图
         * @param position 点击得到的position
         */
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置RecyclerView某个的监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}

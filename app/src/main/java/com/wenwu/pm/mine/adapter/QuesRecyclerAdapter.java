package com.wenwu.pm.mine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wenwu.pm.R;
import com.wenwu.pm.home.bean.CardViewItemBean;
import com.wenwu.pm.mine.bean.QuestionCardViewItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author:wenwudeng
 * @date:10:50 AM 3/19/2020
 */
public class QuesRecyclerAdapter extends RecyclerView.Adapter<QuesRecyclerAdapter.ViewHolder>{
    private List<QuestionCardViewItem> questionCardViewItemList;
    private QuestionCardViewItem questionCardViewItem;


    public QuesRecyclerAdapter(List<QuestionCardViewItem> cardViewItemBeanList) {
        this.questionCardViewItemList = cardViewItemBeanList;
    }

    // ViewHolder对控件实例进行缓存,避免每次都去每次都去通过id去获得控件实例句柄.*/
    static  class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView time;
        TextView answerCount;

        private ViewHolder( View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            title = itemView.findViewById(R.id.my_ques_title);
            time = itemView.findViewById(R.id.my_ques_time);
            answerCount = itemView.findViewById(R.id.my_ques_answer_count);
        }
    }

    /**
     * 创建viewHolder实例,并把加载出来的布局传递到构造函数中
     * @param parent 父布局
     * @param viewType 视图
     * @return  返回一个ViewHolder对象
     */
    @NonNull
    @Override
    public QuesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //获得R.layout.concern_item视图view实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_question_item, parent, false);
        //将获得的concern_item视图实例作为ViewHolder获取实例的参数
        final QuesRecyclerAdapter.ViewHolder holder = new QuesRecyclerAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                QuestionCardViewItem questionCardViewItem = questionCardViewItemList.get(position);
                Toast.makeText(v.getContext(), "you click view" + questionCardViewItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }


    /**
     * 对RecylerView子项进行赋值,会在每个子项滚动到屏幕内的时候执行
     * @param holder 实例对象
     * @param position 根据position获取各个实例
     */
    @Override
    public void onBindViewHolder(@NonNull QuesRecyclerAdapter.ViewHolder holder, int position) {
        QuestionCardViewItem questionCardViewItem = questionCardViewItemList.get(position);
        holder.title.setText(questionCardViewItem.getTitle());
        holder.time.setText(questionCardViewItem.getTime());
        holder.answerCount.setText(questionCardViewItem.getAnswerCount());
    }

    @Override
    public int getItemCount() {
        return questionCardViewItemList.size();
    }
}

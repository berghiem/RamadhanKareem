package ruby.com.hijrahchallenge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.model.ChallengeDetail;
import ruby.com.hijrahchallenge.model.response.Answer;

public class AnswerAdapter  extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private Context context;
    private List<Answer> answers;
    private AnswerAdapter.Like likeAction;

    public interface Like {

        public void likeAnswer( int answerId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView answerText;
        public TextView usernameText;
        public TextView countText;
        public ImageView likeButton;
        public LinearLayout shareView;

        public ViewHolder(View v) {
            super(v);
            answerText = (TextView) v.findViewById(R.id.answer);
            usernameText = (TextView) v.findViewById(R.id.username);
            countText = (TextView) v.findViewById(R.id.likeCount);
            likeButton = (ImageView) v.findViewById(R.id.likeButton);
            shareView = (LinearLayout) v.findViewById(R.id.shareView);
        }
    }

    public AnswerAdapter(Context context, AnswerAdapter.Like like){
        this.context = context;
        this.likeAction = like;

    }


    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.answer,parent,false);
        AnswerAdapter.ViewHolder viewHolder = new ViewHolder( v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AnswerAdapter.ViewHolder holder, int position) {
        final Answer answer = answers.get(position);
        holder.usernameText.setText(answer.getUsername());
        holder.answerText.setText(answer.getAnswer());
        holder.countText.setText(""+answer.getLikeCount());
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeAction.likeAnswer(answer.getId());
            }
        });
        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });


    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        notifyDataSetChanged();
    }
}

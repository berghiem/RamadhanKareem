package ruby.com.hijrahchallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ruby.com.hijrahchallenge.ButtonAction;
import ruby.com.hijrahchallenge.ChallengeAction;
import ruby.com.hijrahchallenge.R;
import ruby.com.hijrahchallenge.model.ChallengeDetail;

/**
 * Created by Ruby on 2/15/2018.
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    private Context context;
    private List<ChallengeDetail> challengeDetails;
    private Accept challengeAction;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView challengeText;
        public TextView countText;
        public Button acceptButton;
        public LinearLayout shareView;

        public ViewHolder(View v) {
            super(v);
            challengeText = (TextView) v.findViewById(R.id.challengeText);
            countText = (TextView) v.findViewById(R.id.countParticipant);
            acceptButton = (Button) v.findViewById(R.id.acceptbutton);
            shareView = (LinearLayout) v.findViewById(R.id.shareView);
        }
    }

    public ChallengeAdapter(Context context, Accept accept){
        this.context = context;
        this.challengeAction = accept;

    }

    @Override
    public ChallengeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.challengedetail,parent,false);
        ViewHolder viewHolder = new ViewHolder( v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ChallengeDetail cd = challengeDetails.get(position);
        holder.countText.setText(cd.getParticipantCount());
        holder.challengeText.setText(cd.getText());
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeAction.acceptChallenge(cd.getChallengeId());
            }
        });
        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
               // shareAChallenge(holder);
            }
        });

    }

    public interface Accept{
        public void acceptChallenge(int challengeDetailId);
    }

    @Override
    public int getItemCount() {
        return challengeDetails.size();
    }

    public void setChallengeDetails(List<ChallengeDetail> challengeDetails) {
        this.challengeDetails = challengeDetails;
        notifyDataSetChanged();
    }

    public void shareAChallenge(View view) {
        Bitmap bitmap = convertToImage(view);
        try {
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/path/to/file.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Environment.getExternalStorageDirectory() + "/path/to/file.png");
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.app_name)));
    }

    private Bitmap convertToImage(View view){

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;

    }
}

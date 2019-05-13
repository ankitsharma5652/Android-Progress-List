package android.practice.g0ku.ratingbgithub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {




    private List<DownloadUpdate> updates = new ArrayList<>();


    public MyAdapter() {
        for(int i = 0 ; i < 20 ; i++)
            updates.add(new DownloadUpdate());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((MyViewHolder)viewHolder).update(updates.get(i));


    }

    @Override
    public int getItemCount() {
        return updates.size();
    }



    static class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title,msg;
        private Button btn;
        private ProgressBar bar;
        private DownloadUpdate update;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.label);
            bar = itemView.findViewById(R.id.progress);
            btn = itemView.findViewById(R.id.btn);
            msg = itemView.findViewById(R.id.msg);

            btn.setOnClickListener(this);



        }

        OnProgressUpdate mProgressListener = null;
        public void update(final DownloadUpdate update){

            if(getAdapterPosition() == RecyclerView.NO_POSITION )  return;

            this.update = update;


            mProgressListener  = new OnProgressUpdate() {
                @Override
                public void onProgress(DownloadUpdate update1,String msg1) {

                    if(update1.getPosition() == RecyclerView.NO_POSITION || update1.getPosition()!= getAdapterPosition()) return;

                    bar.setProgress(update1.getProgress());
                    bar.setMax(update1.getMaxProgress());
                    msg.setText(msg1);
                }
            };

            update.setListener(mProgressListener);
            title.setText(String.valueOf(getAdapterPosition()));




            if(update.isDownloadStarted())
                checkDownload();
            else
                hideDownload();

        }

        private void hideDownload() {


            bar.setVisibility(View.GONE);
            msg.setVisibility(View.GONE);
            btn.setEnabled(true);
            bar.setIndeterminate(true);
            update.setDownloadStarted(false);



        }

        @Override
        public void onClick(View v) {

            if(update == null ) return;

            v.setEnabled(false);

            bar.setVisibility(View.VISIBLE);
            msg.setVisibility(View.VISIBLE);
            checkDownload();
            bar.setIndeterminate(false);
            update.setProgress(0);
            update.setMaxProgress(100);
            bar.setProgress(update.getProgress());
            bar.setMax(update.getMaxProgress());
            msg.setText("Starting...");
            update.setDownloadStarted(true);



            update.start(getAdapterPosition(),mProgressListener);


        }

        private void checkDownload() {

            bar.setVisibility(View.VISIBLE);
            msg.setVisibility(View.VISIBLE);

            btn.setEnabled(false);
            bar.setIndeterminate(false);
            bar.setProgress(update.getProgress());
            bar.setMax(update.getMaxProgress());

            msg.setText("Pos : "+ getAdapterPosition() +" "+ update.getProgress());

        }
    }

    public interface OnProgressUpdate{

        void onProgress(DownloadUpdate update,String msg);
    }
}

package android.practice.g0ku.ratingbgithub;

import android.os.AsyncTask;

import java.util.concurrent.ForkJoinPool;

public class DownloadUpdate {

    private boolean isDownloadStarted;
    private int progress = 0;
    private int maxProgress;
    private MyProgressAsyncTask asyncTask;
    private int position = -1;


    public boolean isDownloadStarted() {
        return isDownloadStarted;
    }

    public void setDownloadStarted(boolean downloadStarted) {
        isDownloadStarted = downloadStarted;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public boolean isCompleted(){return progress == maxProgress;}

    public void start(int position, MyAdapter.OnProgressUpdate update){


        if(asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED){

            this.position = position;
            asyncTask = new MyProgressAsyncTask(this,update);

            asyncTask.executeOnExecutor(new ForkJoinPool());
        }


    }

    public void setListener(MyAdapter.OnProgressUpdate mProgressListener) {
        if(asyncTask != null)
            asyncTask.setListener(mProgressListener);
    }

    public int getPosition() { return position;
    }
}


package android.practice.g0ku.ratingbgithub;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

public class MyProgressAsyncTask extends AsyncTask<Integer,Integer,String> {

    private DownloadUpdate downloadUpdate;
    private MyAdapter.OnProgressUpdate update;


    public MyProgressAsyncTask(DownloadUpdate downloadUpdate, MyAdapter.OnProgressUpdate update) {

        this.downloadUpdate= downloadUpdate;
        this.update = update;

    }

    @Override
    protected String doInBackground(Integer... integers) {



        int progress  = downloadUpdate.getProgress();
        int max = downloadUpdate.getMaxProgress();
        while(progress++<max){

            try {
                Thread.sleep(50);
                downloadUpdate.setProgress(progress);
                publishProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        if(values.length < 1) return;



        if(update !=null)
            update.onProgress(downloadUpdate,"Download : "+ downloadUpdate.getProgress()+"/"+downloadUpdate.getMaxProgress());

    }

    public void setListener(MyAdapter.OnProgressUpdate mProgressListener) {
        this.update =mProgressListener;
    }

    @Override
    protected void onCancelled() {
        this.update = null;
    }

    @Override
    protected void onPostExecute(String s) {
        update = null;
    }
}

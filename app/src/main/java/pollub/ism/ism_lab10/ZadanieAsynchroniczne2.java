package pollub.ism.ism_lab10;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;


public class ZadanieAsynchroniczne2 extends AsyncTask<Integer,Integer,Void> {

    WeakReference<Button> przyciskStartRef, przyciskCancelRef;
    WeakReference<ProgressBar> pasekPostempuRef;
    int liczbaPowtorzen, czasPauzy, postep;

    public ZadanieAsynchroniczne2(ProgressBar progressBar, Button przyciskCancel, Button przyciskStart) {
        przyciskStartRef=new WeakReference<>(przyciskStart);
        przyciskCancelRef=new WeakReference<>(przyciskCancel);
        pasekPostempuRef=new WeakReference<>(progressBar) ;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        liczbaPowtorzen=integers[0];
        czasPauzy=integers[1];

        for(int i=0;i<liczbaPowtorzen;i++){
            if(isCancelled()) break;

            try{
                Thread.sleep(czasPauzy);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            postep=100*(i+1)/liczbaPowtorzen;
            publishProgress(postep);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        pasekPostempuRef.get().setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void unused) {
        przyciskStartRef.get().setEnabled(true);
        przyciskCancelRef.get().setEnabled(false);
    }

    @Override
    protected void onCancelled(Void unused) {
        przyciskCancelRef.get().setEnabled(false);
        przyciskStartRef.get().setEnabled(true);
    }
}

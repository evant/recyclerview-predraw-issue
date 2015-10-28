package tatarka.me.picassorecyclerviewissue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;


public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = "MainActivity";

    private int itemCount = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new RecyclerView.Adapter<Holder>() {

            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Holder(getLayoutInflater().inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(Holder holder, int position) {
                holder.itemView.getViewTreeObserver().addOnPreDrawListener(holder);
            }

            @Override
            public int getItemCount() {
                return itemCount;
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                itemCount = 99;
                recyclerView.getAdapter().notifyItemRemoved(0);
            }
        }, 500);
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }

    public static class Holder extends RecyclerView.ViewHolder implements ViewTreeObserver.OnPreDrawListener {

        public Holder(View itemView) {
            super(itemView);
        }

        @Override
        public boolean onPreDraw() {
            itemView.getViewTreeObserver().removeOnPreDrawListener(this);
            Log.d(TAG, "predraw: " + getAdapterPosition() + "(" + System.nanoTime() + ")");
            return true;
        }
    }
}
